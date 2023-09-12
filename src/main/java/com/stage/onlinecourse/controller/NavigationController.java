package com.stage.onlinecourse.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class NavigationController {

	// On définit ici les controls pour acceder à l'arborescence des cours (par système, sous-système etc.)
	
	@GetMapping("/")
	// accessible à tout le monde
	public String home() {
		// TODO: renvoyer à l'acceuil
		return null;
	}
	
	@GetMapping("/sections")
	// accessible à tout le monde
	public String racine() {
		// TODO: renvoyer la racine de l'arborescence ( par exemple un choix entre Anglophone et Francophone)
		return null;
	}
	
	@GetMapping("/section/")
	// accessible à tout le monde
	// En front l'utilisateur stocque au fur et à mesur sous forme de liste l'ensemble des documents qu'il traverse
	public String gotoChild(@RequestParam(name = "path", required = true) String elementPath) {
		// TODO: verifier que cet enfant existe effectivement
		// TODO: renvoyer la liste des éléments dans l'enfant ( par exemple un choix entre Anglophone et Francophone)
		return null;
	}
	
}
