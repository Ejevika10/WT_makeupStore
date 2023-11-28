package MakeupStore.model.dao.impl;

import MakeupStore.model.dao.StockDao;
import MakeupStore.model.utils.ConnectionPool;
import MakeupStore.model.entities.stock.Stock;
import MakeupStore.model.entities.stock.StocksExtractor;
import MakeupStore.model.exceptions.DaoException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Using jdbc to work with stock
 *
 * @author ejevika
 * @version 1.0
 */
public class JdbcStockDao implements StockDao {
    /**
     * Instance of logger
     */
    private static final Logger log = Logger.getLogger(StockDao.class);
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    /**
     * Stock extractor
     */
    private StocksExtractor stocksExtractor = new StocksExtractor();
    /**
     * Instance of connection pool
     */
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    /**
     * Instance of StockDao
     */
    private static volatile StockDao instance;
    /**
     * SQL query to find stock by id
     */
    private static final String GET_STOCK_BY_ID = "SELECT * FROM stocks WHERE makeupId = ?";
    /**
     * SQL query to update reserved stock
     */
    private static final String UPDATE_STOCK = "UPDATE stocks SET reserved = ? WHERE makeupId = ?";

    /**
     * Realisation of Singleton pattern
     *
     * @return instance of StockDao
     */
    public static StockDao getInstance() {
        if (instance == null) {
            synchronized (StockDao.class) {
                if (instance == null) {
                    instance = new JdbcStockDao();
                }
            }
        }
        return instance;
    }

    /**
     * Find available stock in database
     *
     * @param makeupId id of makeup item
     * @return available stock
     * @throws DaoException throws when there is some errors during dao method execution
     */
    @Override
    public Integer availableStock(Long makeupId) throws DaoException {
        Stock stock = getStock(makeupId);
        if (stock != null) {
            return stock.getStock() - stock.getReserved();
        } else {
            return 0;
        }
    }

    /**
     * Update reserve number of makeup items in database
     *
     * @param makeupId  - makeup item to update
     * @param quantity - quantity to add in reserve field
     * @throws DaoException throws when there is some errors during dao method execution
     */
    @Override
    public void reserve(Long makeupId, int quantity) throws DaoException {
        Stock stock = getStock(makeupId);
        if (stock != null) {
            int newReserved = stock.getReserved() + quantity;
            Connection conn = null;
            PreparedStatement statement = null;
            try {
                lock.writeLock().lock();
                conn = connectionPool.getConnection();
                statement = conn.prepareStatement(UPDATE_STOCK);
                statement.setLong(2, makeupId);
                statement.setLong(1, newReserved);
                statement.execute();
                log.log(Level.INFO, "Update reserve stock in the database");
            } catch (SQLException ex) {
                log.log(Level.ERROR, "Error in reserve", ex);
                throw new DaoException("Error in process of reserving stock");
            } finally {
                lock.writeLock().unlock();
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException ex) {
                        log.log(Level.ERROR, "Error in closing statement", ex);
                    }
                }
                if (conn != null) {
                    connectionPool.releaseConnection(conn);
                }
            }
        }
    }

    /**
     * Get stock of makeup item in database
     *
     * @param makeupId id of makeup item
     * @return stock of makeup item
     * @throws DaoException throws when there is some errors during dao method execution
     */
    private Stock getStock(Long makeupId) throws DaoException {
        Stock stock = null;
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = connectionPool.getConnection();
            statement = conn.prepareStatement(GET_STOCK_BY_ID);
            statement.setLong(1, makeupId);
            ResultSet resultSet = statement.executeQuery();
            stock = stocksExtractor.extractData(resultSet).get(0);
            log.log(Level.INFO, "Found stock by makeupId in the database");
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Error in getStock", ex);
            throw new DaoException("Error in process of getting stock");
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    log.log(Level.ERROR, "Error in closing statement", ex);
                }
            }
            if (conn != null) {
                connectionPool.releaseConnection(conn);
            }
        }
        return stock;
    }
}
