package com.mvc.service;

import java.util.List;

import com.mvc.model.Player;

public interface PlayerService {

	Player findById(int id);
	
	void savePlayer(Player player);
	
	void updatePlayer(Player player);
	
	void deletePlayerByJersey(String jersey);

	List<Player> findAllPlayers(); 
	
	Player findPlayerByJersey(String jersey);

	boolean isPlayerJerseyUnique(Integer id, String jersey);
	
}
