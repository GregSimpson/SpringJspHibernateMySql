package com.mvc.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mvc.model.Player;
import com.mvc.service.PlayerService;

@Controller
@RequestMapping("/")
public class AppController {

	@Autowired
	PlayerService service;
	
	@Autowired
	MessageSource messageSource;

	/*
	 * This method will list all existing players.
	 */
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String listPlayers(ModelMap model) {

		List<Player> players = service.findAllPlayers();
		model.addAttribute("players", players);
		return "allplayers";
	}

	/*
	 * This method will provide the medium to add a new player.
	 */
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public String newPlayer(ModelMap model) {
		Player player = new Player();
		model.addAttribute("player", player);
		model.addAttribute("edit", false);
		return "registration";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * saving player in database. It also validates the user input
	 */
	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
	public String savePlayer(@Valid Player player, BindingResult result,
			ModelMap model) {

		if (result.hasErrors()) {
			return "registration";
		}

		/*
		 * Uniqueness of field [jersey] should be implemented by custom @Unique annotation 
		 * and applying it on the field [jersey] of Model class [Player].
		 */
		if(!service.isPlayerJerseyUnique(player.getId(), player.getJersey())){
			FieldError jerseyError =new FieldError("player","jersey",messageSource.getMessage("non.unique.jersey", new String[]{player.getJersey()}, Locale.getDefault()));
		    result.addError(jerseyError);
			return "registration";
		}
		
		service.savePlayer(player);

		model.addAttribute("success", "Player " + player.getName() + " registered successfully");
		return "success";
	}


	/*
	 * This method will update an existing player.
	 */
	@RequestMapping(value = { "/edit-{jersey}-player" }, method = RequestMethod.GET)
	public String editPlayer(@PathVariable String jersey, ModelMap model) {
		Player player = service.findPlayerByJersey(jersey);
		model.addAttribute("player", player);
		model.addAttribute("edit", true);
		return "registration";
	}
	
	/*
	 * This method will be called on form submission, handling POST request for
	 * updating player in database. It also validates the user input
	 */
	@RequestMapping(value = { "/edit-{jersey}-player" }, method = RequestMethod.POST)
	public String updatePlayer(@Valid Player player, BindingResult result,
			ModelMap model, @PathVariable String jersey) {

		if (result.hasErrors()) {
			return "registration";
		}

		if(!service.isPlayerJerseyUnique(player.getId(), player.getJersey())){
			FieldError jerseyError =new FieldError("player","jersey",messageSource.getMessage("non.unique.jersey", new String[]{player.getJersey()}, Locale.getDefault()));
		    result.addError(jerseyError);
			return "registration";
		}

		service.updatePlayer(player);

		model.addAttribute("success", "Player " + player.getName()	+ " updated successfully");
		return "success";
	}

	
	/*
	 * This method will delete a player by it's SSN value.
	 */
	@RequestMapping(value = { "/delete-{jersey}-player" }, method = RequestMethod.GET)
	public String deletePlayer(@PathVariable String jersey) {
		service.deletePlayerByJersey(jersey);
		return "redirect:/list";
	}

}
