package com.mvc.dao;

import java.math.BigDecimal;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.mvc.model.Player;


public class PlayerDaoImplTest extends EntityDaoImplTest{

	@Autowired
	PlayerDao playerDao;

	@Override
	protected IDataSet getDataSet() throws Exception{
		IDataSet dataSet = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Player.xml"));
		return dataSet;
	}
	
	/* In case you need multiple datasets (mapping different tables) and you do prefer to keep them in separate XML's
	@Override
	protected IDataSet getDataSet() throws Exception {
	  IDataSet[] datasets = new IDataSet[] {
			  new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Player.xml")),
			  new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Benefits.xml")),
			  new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Departements.xml"))
	  };
	  return new CompositeDataSet(datasets);
	}
	*/

	@Test
	public void findById(){
		Assert.assertNotNull(playerDao.findById(1));
		Assert.assertNull(playerDao.findById(3));
	}

	
	@Test
	public void savePlayer(){
		playerDao.savePlayer(getSamplePlayer());
		Assert.assertEquals(playerDao.findAllPlayers().size(), 3);
	}
	
	@Test
	public void deletePlayerByJersey(){
		playerDao.deletePlayerByJersey("11111");
		Assert.assertEquals(playerDao.findAllPlayers().size(), 1);
	}
	
	@Test
	public void deletePlayerByInvalidJersey(){
		playerDao.deletePlayerByJersey("23423");
		Assert.assertEquals(playerDao.findAllPlayers().size(), 2);
	}

	@Test
	public void findAllPlayers(){
		Assert.assertEquals(playerDao.findAllPlayers().size(), 2);
	}
	
	@Test
	public void findPlayerByJersey(){
		Assert.assertNotNull(playerDao.findPlayerByJersey("11111"));
		Assert.assertNull(playerDao.findPlayerByJersey("14545"));
	}

	public Player getSamplePlayer(){
		Player player = new Player();
		player.setName("Karen");
		player.setJersey("12345");
		player.setSalary(new BigDecimal(10980));
		player.setJoiningDate(new LocalDate());
		return player;
	}

}
