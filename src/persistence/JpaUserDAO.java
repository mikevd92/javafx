package persistence;

import java.sql.Date;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import exceptions.AppException;
import model.User;
@Repository("userDAO")
public class JpaUserDAO extends JpaDAO<User,Integer> implements UserDAO {

	private User user;
	public JpaUserDAO() throws AppException {
		super();
	}

	@Override
	@Transactional
	@Cacheable(value="users",key="name")
	public User findUserByNameAndPass(String name, String pass) throws AppException {
		Query q;
		try{
			q=entityManager.createQuery("SELECT X FROM "+entityClass.getName()+" X WHERE X.name=:name AND X.password=:pass");
			q.setParameter("name", name);
			q.setParameter("pass", pass);
		return (model.User) q.getSingleResult();
		}catch(Exception ex){
			throw new AppException("DB Exception: "+ex.getMessage());
		}
	}
	@Override
	@Transactional
	@Cacheable(value="users",key="name")
	public User findUserByName(String name) throws AppException {
		Query q;
		try{
			q=entityManager.createQuery("SELECT X FROM "+entityClass.getName()+" X WHERE X.name=:name");
			q.setParameter("name", name);
			return (model.User) q.getSingleResult();
		}catch(Exception ex){
			throw new AppException("DB Exception: "+ex.getMessage());
		}
	}

	@Override
	@Transactional
	@CacheEvict(value="users",key="name")
	public void createUser(String name, String pass,String address,String dob) throws AppException {
		user=new User();
		user.setName(name);
		user.setPassword(pass);
		Date birth;
		try{
			birth=Date.valueOf(dob);
			user.setDateOfBirth(birth);
		}catch(Exception ex){
			throw new AppException("DB Exception: "+ex.getMessage());
		}
		user.setAddress(address);
		this.save(user);
	}

}
