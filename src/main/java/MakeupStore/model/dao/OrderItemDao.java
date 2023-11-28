package MakeupStore.model.dao;

import MakeupStore.model.entities.order.OrderItem;
import MakeupStore.model.exceptions.DaoException;

import java.util.List;

/**
 * @author ejevika
 * @version 1.0
 */
public interface OrderItemDao {
    /**
     * Find items from order
     *
     * @param key key of order
     * @return List of OrderItems from order
     * @throws DaoException throws when there is some errors during dao method execution
     */
    List<OrderItem> getOrderItems(Long key) throws DaoException;
}
