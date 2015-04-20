package persistence;

import javax.persistence.Query;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import exceptions.AppException;
import model.Manager;

@Repository("mngDAO")
public class JpaManagerDAO extends JpaDAO<Manager,Integer> implements ManagerDAO {

    private Manager manager;
	public JpaManagerDAO() throws AppException {
		super();
	}

	@Override
	@Transactional
	@Cacheable(value="managers",key="name")
	public model.Manager findManagerByNameAndPass(String name, String pass) throws AppException {
		try{
		Query q=entityManager.createQuery("SELECT X FROM "+entityClass.getName()+" X WHERE X.name=:name AND X.password=:pass");
		q.setParameter("name", name);
		q.setParameter("pass", pass);
		manager=(model.Manager) q.getSingleResult();
		}catch(Exception ex){
			throw new AppException("DB Exception"+ex.getMessage());
		}
		return manager;
	}
}
