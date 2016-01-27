package com.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvc.dao.PlayerDao;
import com.mvc.model.Player;

@Service("playerService")
@Transactional
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	private PlayerDao dao;
	
	public Player findById(int id) {
		return dao.findById(id);
	}

	public void savePlayer(Player player) {
		dao.savePlayer(player);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updatePlayer(Player player) {
		Player entity = dao.findById(player.getId());
		if(entity!=null){
			entity.setName(player.getName());
			entity.setJoiningDate(player.getJoiningDate());
			entity.setSalary(player.getSalary());
			entity.setJersey(player.getJersey());
		}
	}

	public void deletePlayerByJersey(String jersey) {
		dao.deletePlayerByJersey(jersey);
	}
	
	public List<Player> findAllPlayers() {
		return dao.findAllPlayers();
	}

	public Player findPlayerByJersey(String jersey) {
		return dao.findPlayerByJersey(jersey);
	}

	public boolean isPlayerJerseyUnique(Integer id, String jersey) {
		Player player = findPlayerByJersey(jersey);
		return ( player == null || ((id != null) && (player.getId() == id)));
	}
	
}
