package com.hasanozgan.demo.controllers;

import com.hasanozgan.demo.services.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
@RequestMapping("/")
public class HelloController {

    @Autowired
    CounterService counterService;

	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) throws Exception {

        model.addAttribute("counter", counterService.get());

		model.addAttribute("message", "Hello, Spring MVC and Akka World");

        counterService.increase();

        return "hello";
	}
	
}