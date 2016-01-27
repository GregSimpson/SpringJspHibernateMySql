package com.mvc.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

import org.joda.time.LocalDate;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mvc.dao.PlayerDao;
import com.mvc.model.Player;

public class PlayerServiceImplTest {

	@Mock
	PlayerDao dao;
	
	@InjectMocks
	PlayerServiceImpl playerService;
	
	@Spy
	List<Player> players = new ArrayList<Player>();
	
	@BeforeClass
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		players = getPlayerList();
	}

	@Test
	public void findById(){
		Player emp = players.get(0);
		when(dao.findById(anyInt())).thenReturn(emp);
		Assert.assertEquals(playerService.findById(emp.getId()),emp);
	}

	@Test
	public void savePlayer(){
		doNothing().when(dao).savePlayer(any(Player.class));
		playerService.savePlayer(any(Player.class));
		verify(dao, atLeastOnce()).savePlayer(any(Player.class));
	}
	
	@Test
	public void updatePlayer(){
		Player emp = players.get(0);
		when(dao.findById(anyInt())).thenReturn(emp);
		playerService.updatePlayer(emp);
		verify(dao, atLeastOnce()).findById(anyInt());
	}

	@Test
	public void deletePlayerByJersey(){
		doNothing().when(dao).deletePlayerByJersey(anyString());
		playerService.deletePlayerByJersey(anyString());
		verify(dao, atLeastOnce()).deletePlayerByJersey(anyString());
	}
	
	@Test
	public void findAllPlayers(){
		when(dao.findAllPlayers()).thenReturn(players);
		Assert.assertEquals(playerService.findAllPlayers(), players);
	}
	
	@Test
	public void findPlayerByJersey(){
		Player emp = players.get(0);
		when(dao.findPlayerByJersey(anyString())).thenReturn(emp);
		Assert.assertEquals(playerService.findPlayerByJersey(anyString()), emp);
	}

	@Test
	public void isPlayerJerseyUnique(){
		Player emp = players.get(0);
		when(dao.findPlayerByJersey(anyString())).thenReturn(emp);
		Assert.assertEquals(playerService.isPlayerJerseyUnique(emp.getId(), emp.getJersey()), true);
	}
	
	
	public List<Player> getPlayerList(){
		Player p1 = new Player();
		p1.setId(1);
		p1.setName("Axel");
		p1.setJoiningDate(new LocalDate());
		p1.setSalary(new BigDecimal(10000));
		p1.setJersey("XXX111");
		
		Player p2 = new Player();
		p2.setId(2);
		p2.setName("Jeremy");
		p2.setJoiningDate(new LocalDate());
		p2.setSalary(new BigDecimal(20000));
		p2.setJersey("XXX222");
		
		players.add(p1);
		players.add(p2);
		return players;
	}
	
}
