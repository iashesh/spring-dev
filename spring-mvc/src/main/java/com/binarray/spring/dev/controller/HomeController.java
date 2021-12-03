package com.binarray.spring.dev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Ashesh
 *
 */

@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String showHome() {
		return "home";
	}

}
