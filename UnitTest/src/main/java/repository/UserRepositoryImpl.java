package com.sumon.api.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sumon.api.entity.User;

@Repository
@Transactional
public class UserRepositoryImpl implements IUserRepository{


	@PersistenceContext
	private EntityManager em;

	@Override
	public List<User> findAll() {
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u ORDER BY u.email ASC", User.class);
		return query.getResultList();
	}

	@Override
	public User findOne(String id) {
		return em.find(User.class, id);
	}

	@Override
	public User create(User user) {
		em.persist(user);
		return user;
	}

	@Override
	public User update(User user) {
		return em.merge(user);
	}

	@Override
	public void delete(User user) {
		User existing = em.find(User.class, user.getId());
		em.remove(existing);
	}

	@Override
	public User findByEmail(String email) {
		TypedQuery<User> query = em.createNamedQuery("User.findByEmail", User.class);
		query.setParameter("pEmail", email);
		List<User> users = query.getResultList();
		if (users != null && users.size() == 1) {
			return users.get(0);
		} else {
			return null;
		}
	}
}
