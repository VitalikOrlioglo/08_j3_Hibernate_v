package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import hibernate.HibernateUtil;
import model.User;

public class UserDAO {
	private SessionFactory sf = HibernateUtil.getSessionFactory();
	
	public void saveUser(User user) {
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		session.save(user); // saveOrUpdate
		session.getTransaction().commit(); // jetzt save
		
		boolean ok = session.getTransaction().getStatus() == TransactionStatus.COMMITTED;
		System.out.println("saved " + ok);
		
		session.close();
	}
	
	/**
	 * HQL - Hibernate Query
	 * @return list
	 */
	public List<User> findAll() {
		Session session = sf.getCurrentSession();
		session.beginTransaction(); // wird momentan von Query benutzt
		Query<User> q = session.createQuery("FROM User", User.class);
		List<User> users = q.getResultList();
		session.getTransaction().commit();
		session.close();
		
		return users;
	}
	
	public User findByUsernameAndPass(String usr, String pwd) {
		Session session = sf.getCurrentSession();
		session.beginTransaction(); // wird momentan von Query benutzt
		Query<User> q = session.createQuery("FROM User WHERE username = :u AND passwort = :p", User.class);
		q.setParameter("u", usr);
		q.setParameter("p", pwd);
		User user = q.uniqueResult();
		session.close();
		
		return user;
	}
	
	public boolean deleteUserById(int id) {
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		User user = session.load(User.class, id);// load erzeugt Exception, wenn nicht gefunden
		if (user != null) {
			session.delete(user);
			session.getTransaction().commit();
		}
		
		boolean ok = session.getTransaction().getStatus() == TransactionStatus.COMMITTED;
		System.out.println("deleted " + ok);
		
		session.close();
		return ok;
	}
	
	public boolean updatePassword(int id, String newPassword) {
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		User user = session.get(User.class, id);// get ist wie load(aber mit null)
		if (user != null) {
			user.setPasswort(newPassword);
			session.update(user);
			session.getTransaction().commit();
		}
		
		boolean ok = session.getTransaction().getStatus() == TransactionStatus.COMMITTED;
		System.out.println("updated " + ok);
		
		session.close();
		
		return ok;
	}
	
	public static void main(String[] args) {
		UserDAO dao = new UserDAO();
//		dao.saveUser(new User("Max", "1231"));
//		System.out.println(dao.findAll());
		System.out.println(dao.findByUsernameAndPass("Max", "1231"));
		dao.sf.close();
	}
}
