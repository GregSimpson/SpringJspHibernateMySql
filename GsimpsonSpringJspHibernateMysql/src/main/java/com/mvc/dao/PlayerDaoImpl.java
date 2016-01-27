package com.mvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mvc.model.Player;

@Repository("playerDao")
public class PlayerDaoImpl extends AbstractDao<Integer, Player> implements PlayerDao {

	public Player findById(int id) {
		return getByKey(id);
	}

	public void savePlayer(Player player) {
		persist(player);
	}

	public void deletePlayerByJersey(String jersey) {
		Query query = getSession().createSQLQuery("delete from Player where jersey = :jersey");
		query.setString("jersey", jersey);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Player> findAllPlayers() {
		Criteria criteria = createEntityCriteria();
		return (List<Player>) criteria.list();
	}

	public Player findPlayerByJersey(String jersey) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("jersey", jersey));
		return (Player) criteria.uniqueResult();
	}
}
