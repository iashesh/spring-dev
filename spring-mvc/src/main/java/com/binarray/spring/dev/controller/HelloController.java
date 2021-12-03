package com.binarray.spring.dev.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Ashesh
 *
 */

@Controller
public class HelloController {
	
	@RequestMapping("/showHelloForm")
	public String showHelloForm() {
		return "hello-form";
	}
	
	@RequestMapping("/showHello")
	public String showHello() {
		return "hello";
	}
	
	@RequestMapping("/showHelloShout")
	public String showHelloShout(HttpServletRequest request, Model model) {
		
		String name = request.getParameter("name");
		
		name = name != null ? name.toUpperCase() : "NULL";
		
		model.addAttribute("name", name);
		
		return "hello";
	}
	
	@RequestMapping("/showHelloShoutParam")
	public String showHelloShoutParam(@RequestParam("name") String name, Model model) {
		
		name = "V3: " + (name != null ? name.toUpperCase() : "NULL");
		
		model.addAttribute("name", name);
		
		return "hello";
	}

}
