package MakeupStore.model.entities.stock;

import MakeupStore.model.dao.MakeupDao;
import MakeupStore.model.dao.impl.JdbcMakeupDao;
import MakeupStore.model.exceptions.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StocksExtractor {
    private MakeupDao makeupDao = JdbcMakeupDao.getInstance();

    public List<Stock> extractData(ResultSet resultSet) throws SQLException, DaoException {
        List<Stock> stocks = new ArrayList<>();
        while (resultSet.next()) {
            Stock stock = new Stock();
            stock.setMakeup(makeupDao.get(resultSet.getLong("MAKEUPID")).orElse(null));
            stock.setStock(resultSet.getInt("STOCK"));
            stock.setReserved(resultSet.getInt("RESERVED"));
            stocks.add(stock);
        }
        return stocks;
    }
}
