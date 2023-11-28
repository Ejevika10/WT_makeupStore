package MakeupStore.model.entities.order;

import MakeupStore.model.dao.MakeupDao;
import MakeupStore.model.dao.impl.JdbcMakeupDao;
import MakeupStore.model.exceptions.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemsExtractor {
    public List<OrderItem> extractData(ResultSet resultSet) throws SQLException, DaoException {
        List<OrderItem> orderItems = new ArrayList<>();
        MakeupDao makeupDao = JdbcMakeupDao.getInstance();
        while (resultSet.next()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setMakeupItem(makeupDao.get(resultSet.getLong("makeupId")).orElse(null));
            orderItem.setQuantity(resultSet.getInt("quantity"));
            orderItems.add(orderItem);
        }
        return orderItems;
    }
}
