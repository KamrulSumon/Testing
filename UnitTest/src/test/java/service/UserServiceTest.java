package com.sumon.test.service;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Assert;
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
import com.sumon.api.exception.UserAlreadyExistException;
import com.sumon.api.exception.UserNotFound;
import com.sumon.api.repository.IUserRepository;
import com.sumon.api.service.IUserService;
import com.sumon.api.service.UserServiceImpl;
import com.sumon.test.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
public class UserServiceTest {

	@Mock
	private  IUserRepository repo;
	
	@InjectMocks
	private IUserService service = new UserServiceImpl();
	
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
		service.findAll();
		Mockito.verify(repo).findAll();
	}
	
	@Test
	public void testFindById() throws UserNotFound{
		Mockito.when(repo.findOne(user.getId())).thenReturn(user);
		
		User actual = service.findOne(user.getId());
		Assert.assertEquals(user, actual);
	}
	
	@Test(expected=UserNotFound.class)
	public void testFindByIdException() throws UserNotFound{
		Mockito.when(repo.findOne(user.getId())).thenReturn(null);
		
		service.findOne(user.getId());
	}
	
	@Test(expected=UserAlreadyExistException.class)
	public void testCreateUserException() throws UserAlreadyExistException{
		Mockito.when(repo.findByEmail(user.getEmail())).thenReturn(user);
		
		service.create(user);
	}
	
	@Test
	public void testCreateUser() throws UserAlreadyExistException{
		Mockito.when(repo.findByEmail(user.getEmail())).thenReturn(null);
		
		service.create(user);
		Mockito.verify(repo).create(user);
	}
}
