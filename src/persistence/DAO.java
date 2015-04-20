package persistence;

import javafx.collections.ObservableList;
import exceptions.AppException;


/**
 * Generic dao interface for uniquely identifiable entities.  <br/>
 * The entity is of type T  <br/>
 * The key which uniquely identifies the entity is of type PK  <br/>
 * <br/><br/>
 * The daos in this project are only supposed to return types of its respective entity, 
 * collections of its entities or calculated primitive values (like counts)  
 * 
 * @author bertell
 */
public interface DAO<T , PK >  {
        
        /**
         * Finds all stored entities of the Dao's specified type.
         * Not a good idea to use this for plentiful entities.
         * @return all entities
         * @throws AppException 
         */
        ObservableList<T> findAll() throws AppException;
        
        /**
         * Finds an entity by its id.
         * @param id
         * @return entity
         * @throws AppException 
         */
        T findById(PK id) throws AppException;
               
        /**
         * Saves an entity into persistent storage
         * @param entity
         * @return saved entity
         * @throws AppException 
         */
        T save(T entity) throws AppException;
        
        /**
         * Removes an entity
         * @param entity
         * @throws AppException 
         */
        void remove(T entity) throws AppException;

        /**
         * Removes an entity by its id
         * @param id
         * @throws AppException 
         */
        void removeById(PK id) throws AppException;
        
        /**
         * Flushes all pending changes into persistent storage
         * @throws AppException 
         */
        void flush() throws AppException;
}