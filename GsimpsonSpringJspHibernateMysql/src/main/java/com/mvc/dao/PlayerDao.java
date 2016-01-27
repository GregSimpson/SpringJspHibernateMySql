package com.mvc.dao;

import java.util.List;

import com.mvc.model.Player;

public interface PlayerDao {

	Player findById(int id);

	void savePlayer(Player player);
	
	void deletePlayerByJersey(String jersey);
	
	List<Player> findAllPlayers();

	Player findPlayerByJersey(String jersey);

}
