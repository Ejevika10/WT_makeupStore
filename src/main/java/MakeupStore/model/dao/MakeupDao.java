package MakeupStore.model.dao;

import MakeupStore.model.entities.makeup.Makeup;
import MakeupStore.model.enums.SortField;
import MakeupStore.model.enums.SortOrder;
import MakeupStore.model.exceptions.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * @author ejevika
 * @version 1.0
 */
public interface MakeupDao {
    /**
     * Find phone by id
     *
     * @param key id of makeup item
     * @return makeup item with id
     * @throws DaoException throws when there is some errors during dao method execution
     */
    Optional<Makeup> get(Long key) throws DaoException;

    /**
     * Find makeup item from database
     *
     * @param offset    - offset of found makeup items
     * @param limit     - limit of found makeup items
     * @param sortField - field to sort
     * @param sortOrder - sort order (asc or desc)
     * @param query     - query for find
     * @return List of makeup items
     * @throws DaoException throws when there is some errors during dao method execution
     */

    List<Makeup> findAll(int offset, int limit, SortField sortField, SortOrder sortOrder, String query) throws DaoException;

    /**
     * Number of founded makeup items
     *
     * @param query - query for find
     * @return number of makeup items
     * @throws DaoException throws when there is some errors during dao method execution
     */
    Long numberByQuery(String query) throws DaoException;
}
