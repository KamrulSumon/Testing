package com.sumon.test.repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sumon.api.entity.User;
import com.sumon.api.repository.IUserRepository;
import com.sumon.api.repository.UserRepositoryImpl;
import com.sumon.test.TestConfig;

import org.junit.Assert; 

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
public class UserRepositoryTest {

	@Mock
	private EntityManager em;
	
	@InjectMocks
	private IUserRepository repo = new UserRepositoryImpl();
	
	@Mock
	private TypedQuery<User> query;
	private User user;
	
	@Before
	public void setup(){
		//em = Mockito.mock(EntityManager.class);  //if not using mocking, write specifically
		MockitoAnnotations.initMocks(this);
		user = new User();
		user.setEmail("hasanm@goldmail.etsu.edu");
		user.setFirstName("Kamrul");
		user.setLastName("Hasan");
		user.setId(UUID.randomUUID().toString());
	} 
	
	@Test
	public void testFindAll(){
		List<User> expected = Arrays.asList(user);
		Mockito.when(em.createQuery("SELECT u FROM User u ORDER BY u.email ASC", User.class)).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(expected);
		List<User> users = repo.findAll();
		Assert.assertEquals(expected, users);
		
		//Mockito.verify(query).getResultList();  //Mockito.verify(query, Mockito.never()).getResultList();
	}
	
	@Test
	public void testFindById(){
		Mockito.when(em.find(User.class, user.getId())).thenReturn(user);
		User actual = repo.findOne(user.getId());
		Assert.assertEquals(user, actual);
	}
	
	@Test
	public void testFindByEmail(){
		List<User> expected = Arrays.asList(user);
		Mockito.when(em.createNamedQuery("User.findByEmail", User.class)).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(expected);
		
		User actual = repo.findByEmail(user.getEmail());
		Assert.assertEquals(user, actual);
	}
	
	@Test
	public void testFindByEmailNull(){
		List<User> expected = Arrays.asList(user);
		Mockito.when(em.createNamedQuery("User.findByEmail", User.class)).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(null);
		
		User actual = repo.findByEmail(user.getEmail());
		Assert.assertEquals(null, actual);
	}
	
	@Test
	public void testCreateUser(){
		repo.create(user);
		Mockito.verify(em).persist(user);
	}
	
	@Test
	public void test(){
		
	}
}
