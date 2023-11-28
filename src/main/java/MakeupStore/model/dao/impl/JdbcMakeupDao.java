package MakeupStore.model.dao.impl;

import MakeupStore.model.dao.MakeupDao;
import MakeupStore.model.entities.makeup.Makeup;
import MakeupStore.model.entities.makeup.MakeupExtractor;
import MakeupStore.model.enums.SortField;
import MakeupStore.model.enums.SortOrder;
import MakeupStore.model.exceptions.DaoException;
import MakeupStore.model.utils.ConnectionPool;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Using jdbc to work with makeup items
 *
 * @author ejevika
 * @version 1.0
 */
public class JdbcMakeupDao implements MakeupDao {
    /**
     * Instance of logger
     */
    private static final Logger log = Logger.getLogger(MakeupDao.class);
    /**
     * Instance of makeupExtractor
     */
    private MakeupExtractor makeupExtractor = new MakeupExtractor();
    /**
     * Instance of MakeupDao
     */
    private static volatile MakeupDao instance;
    /**
     * Instance of ConnectionPool
     */
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    /**
     * SQL query to find makeup items by id
     */
    private static final String GET_QUERY = "SELECT * FROM makeup WHERE id = ?";
    /**
     * SQL query to find all makeup items with available stock > 0, limit and offset
     */
    private static final String SIMPLE_FIND_ALL_QUERY =
            //"SELECT * FROM makeup";
            "SELECT item.* " +
                    "FROM (SELECT makeup.* FROM makeup " +
                    "LEFT JOIN stocks ON makeup.id = stocks.makeupId WHERE stocks.stock - stocks.reserved > 0 and makeup.price > 0) item";
    /**
     * SQL query to find all makeup items with available stock
     */
    private static final String FIND_WITHOUT_OFFSET_AND_LIMIT = "SELECT item.* " +
            "FROM (SELECT makeup.* FROM makeup " +
            "LEFT JOIN stocks ON makeup.id = stocks.makeupId WHERE stocks.stock - stocks.reserved > 0 ";

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * SQL query to find number of makeup items
     */
    private static final String NUMBER_OF_MAKEUP_QUERY = "SELECT count(*) FROM MAKEUP LEFT JOIN STOCKS ON MAKEUP.ID = STOCKS.MAKEUPID WHERE STOCKS.STOCK - STOCKS.RESERVED > 0 AND makeup.price > 0";

    /**
     * Realisation of Singleton pattern
     *
     * @return instance of MakeupDao
     */
    public static MakeupDao getInstance() {
        if (instance == null) {
            synchronized (MakeupDao.class) {
                if (instance == null) {
                    instance = new JdbcMakeupDao();
                }
            }
        }
        return instance;
    }

    /**
     * Get makeup item by id from database
     *
     * @param key id of makeup item
     * @return makeup item
     * @throws DaoException throws when there is some errors during dao method execution
     */
    @Override
    public Optional<Makeup> get(Long key) throws DaoException {
        Optional<Makeup> makeupItem;
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            lock.readLock().lock();
            conn = connectionPool.getConnection();
            statement = conn.prepareStatement(GET_QUERY);
            statement.setLong(1, key);
            ResultSet resultSet = statement.executeQuery();
            makeupItem = makeupExtractor.extractData(resultSet).stream().findAny();
            log.log(Level.INFO, "Found makeup items by id in the database");
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Error in get function", ex);
            throw new DaoException("Error in process of getting makeup item");
        } finally {
            lock.readLock().unlock();
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
        return makeupItem;
    }

    /**
     * Find all phones from database
     *
     * @param offset    - offset of found makeup items
     * @param limit     - limit of found makeup items
     * @param sortField - field to sort
     * @param sortOrder - sort order (asc or desc)
     * @param query     - query for find
     * @return list of makeup items
     * @throws DaoException throws when there is some errors during dao method execution
     */
    @Override
    public List<Makeup> findAll(int offset, int limit, SortField sortField, SortOrder sortOrder, String query) throws DaoException {
        List<Makeup> makeupItems;
        String sql = makeFindAllSQL(sortField, sortOrder, query);
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            lock.readLock().lock();
            conn = connectionPool.getConnection();
            statement = conn.prepareStatement(sql);
            //statement.setInt(1, offset);
            //statement.setInt(2, limit);
            System.out.println(statement.toString());
            ResultSet resultSet = statement.executeQuery();
            makeupItems = makeupExtractor.extractData(resultSet);
            log.log(Level.INFO, "Found all makeup items in the database");
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Error in findAll", ex);
            throw new DaoException("Error in process of getting all makeup items");
        } finally {
            lock.readLock().unlock();
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
        System.out.println(makeupItems.toString());
        return makeupItems;
    }

    /**
     * Find number of makeup items by query from database
     *
     * @param query - query for find
     * @return number of makeup items
     * @throws DaoException throws when there is some errors during dao method execution
     */
    @Override
    public Long numberByQuery(String query) throws DaoException {
        String sql;
        if (query == null || query.equals("")) {
            sql = NUMBER_OF_MAKEUP_QUERY;
        } else {
            sql = NUMBER_OF_MAKEUP_QUERY + " AND " +
                    "(LOWER(MAKEUP.BRAND) LIKE LOWER('" + query + "%') " +
                    "OR LOWER(MAKEUP.BRAND) LIKE LOWER('% " + query + "%'))";
        }
        Connection conn = null;
        Statement statement = null;
        try {
            lock.readLock().lock();
            conn = connectionPool.getConnection();
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                return rs.getLong(1);
            }
            log.log(Level.INFO, "Found count of makeup items");
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Error in numberByQuery", ex);
            throw new DaoException("Error in process of getting number of makeup items");
        } finally {
            lock.readLock().unlock();
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
        return 0L;
    }

    /**
     * Make sql query with sorting and finding
     *
     * @param sortField - field to sort
     * @param sortOrder - order to sort
     * @param query     - query to find
     * @return sql query
     */
    private String makeFindAllSQL(SortField sortField, SortOrder sortOrder, String query) {
        if (sortField != null || query != null && !query.equals("")) {
            StringBuilder sql = new StringBuilder(FIND_WITHOUT_OFFSET_AND_LIMIT);

            if (query != null && !query.equals("")) {
                sql.append("AND (" + "LOWER(MAKEUP.BRAND) LIKE LOWER('").append(query).append("%') ").
                        append("OR LOWER(MAKEUP.BRAND) LIKE LOWER('% ").append(query).append("%') ").append(") ");
            }
            sql.append("AND MAKEUP.PRICE > 0 ");
            if (sortField != null) {
                sql.append("ORDER BY ").append(sortField.name()).append(" ");
                if (sortOrder != null) {
                    sql.append(sortOrder.name()).append(" ");
                } else {
                    sql.append("ASC ");
                }
            }
            sql.append(") item");
            //System.out.println(sql.toString());
            return sql.toString();
        } else {
            return SIMPLE_FIND_ALL_QUERY;
        }
    }
}
