package com.mvc.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import static org.mockito.Mockito.atLeastOnce;

import org.springframework.context.MessageSource;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import com.mvc.model.Player;
import com.mvc.service.PlayerService;

public class AppControllerTest {

	@Mock
	PlayerService service;
	
	@Mock
	MessageSource message;
	
	@InjectMocks
	AppController appController;
	
	@Spy
	List<Player> players = new ArrayList<Player>();

	@Spy
	ModelMap model;
	
	@Mock
	BindingResult result;
	
	@BeforeClass
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		players = getPlayerList();
	}
	
	@Test
	public void listPlayers(){
		when(service.findAllPlayers()).thenReturn(players);
		Assert.assertEquals(appController.listPlayers(model), "allplayers");
		Assert.assertEquals(model.get("players"), players);
		verify(service, atLeastOnce()).findAllPlayers();
	}
	
	@Test
	public void newPlayer(){
		Assert.assertEquals(appController.newPlayer(model), "registration");
		Assert.assertNotNull(model.get("player"));
		Assert.assertFalse((Boolean)model.get("edit"));
		Assert.assertEquals(((Player)model.get("player")).getId(), 0);
	}


	@Test
	public void savePlayerWithValidationError(){
		when(result.hasErrors()).thenReturn(true);
		doNothing().when(service).savePlayer(any(Player.class));
		Assert.assertEquals(appController.savePlayer(players.get(0), result, model), "registration");
	}

	@Test
	public void savePlayerWithValidationErrorNonUniqueJersey(){
		when(result.hasErrors()).thenReturn(false);
		when(service.isPlayerJerseyUnique(anyInt(), anyString())).thenReturn(false);
		Assert.assertEquals(appController.savePlayer(players.get(0), result, model), "registration");
	}

	
	@Test
	public void savePlayerWithSuccess(){
		when(result.hasErrors()).thenReturn(false);
		when(service.isPlayerJerseyUnique(anyInt(), anyString())).thenReturn(true);
		doNothing().when(service).savePlayer(any(Player.class));
		Assert.assertEquals(appController.savePlayer(players.get(0), result, model), "success");
		Assert.assertEquals(model.get("success"), "Player Axel registered successfully");
	}

	@Test
	public void editPlayer(){
		Player emp = players.get(0);
		when(service.findPlayerByJersey(anyString())).thenReturn(emp);
		Assert.assertEquals(appController.editPlayer(anyString(), model), "registration");
		Assert.assertNotNull(model.get("player"));
		Assert.assertTrue((Boolean)model.get("edit"));
		Assert.assertEquals(((Player)model.get("player")).getId(), 1);
	}

	@Test
	public void updatePlayerWithValidationError(){
		when(result.hasErrors()).thenReturn(true);
		doNothing().when(service).updatePlayer(any(Player.class));
		Assert.assertEquals(appController.updatePlayer(players.get(0), result, model,""), "registration");
	}

	@Test
	public void updatePlayerWithValidationErrorNonUniqueSSN(){
		when(result.hasErrors()).thenReturn(false);
		when(service.isPlayerJerseyUnique(anyInt(), anyString())).thenReturn(false);
		Assert.assertEquals(appController.updatePlayer(players.get(0), result, model,""), "registration");
	}

	@Test
	public void updatePlayerWithSuccess(){
		when(result.hasErrors()).thenReturn(false);
		when(service.isPlayerJerseyUnique(anyInt(), anyString())).thenReturn(true);
		doNothing().when(service).updatePlayer(any(Player.class));
		Assert.assertEquals(appController.updatePlayer(players.get(0), result, model, ""), "success");
		Assert.assertEquals(model.get("success"), "Player Axel updated successfully");
	}
	
	
	@Test
	public void deletePlayer(){
		doNothing().when(service).deletePlayerByJersey(anyString());
		Assert.assertEquals(appController.deletePlayer("123"), "redirect:/list");
	}

	public List<Player> getPlayerList(){
		Player e1 = new Player();
		e1.setId(1);
		e1.setName("Axel");
		e1.setJoiningDate(new LocalDate());
		e1.setSalary(new BigDecimal(10000));
		e1.setJersey("XXX111");
		
		Player e2 = new Player();
		e2.setId(2);
		e2.setName("Jeremy");
		e2.setJoiningDate(new LocalDate());
		e2.setSalary(new BigDecimal(20000));
		e2.setJersey("XXX222");
		
		players.add(e1);
		players.add(e2);
		return players;
	}
	
}
