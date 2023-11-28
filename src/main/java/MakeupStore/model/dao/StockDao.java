package MakeupStore.model.dao;

import MakeupStore.model.exceptions.DaoException;

/**
 * @author ejevika
 * @version 1.0
 */
public interface StockDao {
    /**
     * Find available stock of makeup item
     *
     * @param makeupId id of makeup item
     * @return available stock
     * @throws DaoException throws when there is some errors during dao method execution
     */
    Integer availableStock(Long makeupId) throws DaoException;

    /**
     * Update reserve of makeup items in database
     *
     * @param makeupId  - makeup item to update
     * @param quantity - quantity to add in reserve field
     * @throws DaoException throws when there is some errors during dao method execution
     */
    void reserve(Long makeupId, int quantity) throws DaoException;
}
