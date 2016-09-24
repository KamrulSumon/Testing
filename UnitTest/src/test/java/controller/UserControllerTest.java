package com.sumon.test.controller;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.sumon.test.TestConfig;
import com.sumon.api.controller.UserController;
import com.sumon.api.entity.User;
import com.sumon.api.exception.UserNotFound;
import com.sumon.api.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
@WebAppConfiguration
public class UserControllerTest {

	@Mock
	private IUserService service;
	
	@InjectMocks
	private UserController controller;
	
	private MockMvc mockMvc;
	
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
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void testFindAll() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/users"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		
		Mockito.verify(service).findAll();  //verify findAll call at least one
	}
	
	@Test
	public void testFindById() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/users/" + user.getId()))
		.andExpect(MockMvcResultMatchers.status().isOk());
	
	    Mockito.verify(service).findOne(user.getId());  //verify findOne call at least one
	}
	
	@Test
	public void testFindByIdNotFound() throws Exception{
		Mockito.when(service.findOne(user.getId())).thenThrow(UserNotFound.class);
		mockMvc.perform(MockMvcRequestBuilders.get("/users/garbageID"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	
	}
}
