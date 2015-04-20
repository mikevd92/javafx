package persistence;

import java.lang.reflect.ParameterizedType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import exceptions.AppException;

public abstract class JpaDAO<E , PK> implements DAO<E, PK> {
       
        protected Class<E> entityClass;
        
        @PersistenceContext
        protected EntityManager entityManager;
 
        @SuppressWarnings("unchecked")
        public JpaDAO() throws AppException {
        	try{
                ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();            
                this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[0];
        	}catch(Exception ex){
        		throw new AppException("DB Exception: "+ex.getMessage());
        	}
        }
        @SuppressWarnings("unchecked")
		@Override
        @Transactional
        public ObservableList<E> findAll() throws AppException {
        		try{
        			ObservableList<E> list= FXCollections.observableList(entityManager.createQuery("SELECT x FROM " + getEntityName() +" x").getResultList());
        			return list;
        		}catch(Exception ex){
        			throw new AppException("DB Exception: "+ex.getMessage());
        		}
        }

        @Override
        @Transactional
        public E findById(PK id) throws AppException {
        	    try{
                return entityManager.find(entityClass, id);
        	    }catch(Exception ex){
        	    	throw new AppException("DB Exception: "+ex.getMessage());
        	    }
        }

        @Override
        @Transactional
        public void flush() throws AppException {
        	try{
                entityManager.flush();
        	}catch(Exception ex){
        		throw new AppException("DB Exception: "+ex.getMessage());
        	}
        }

        @Override
        @Transactional(readOnly=false, propagation=Propagation.MANDATORY)
        public void remove(E entity) throws AppException {
        	try{
                entityManager.remove(entity);
        	}catch(Exception ex){
        		throw new AppException("DB Exception: "+ex.getMessage());
        	}
        }

        @Override
        @Transactional(readOnly=false, propagation=Propagation.MANDATORY)
        public void removeById(PK id) throws AppException {
                entityManager.remove(findById(id));
        }

        @Override
        @Transactional(readOnly=false, propagation=Propagation.MANDATORY)
        public E save(E entity) throws AppException {
        	try{
                entityManager.persist(entity);
                return entity;
        	}catch(Exception ex){
        		throw new AppException("DB Exception: "+ex.getMessage());
        	}
        }

        public String getEntityName() throws AppException {
        	try{
                return entityClass.getName();
        	}catch(Exception ex){
        		throw new AppException("DB Exception: "+ex.getMessage());
        	}
        }
              
}
