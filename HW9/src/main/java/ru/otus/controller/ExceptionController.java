package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExceptionController {

	@ExceptionHandler(RuntimeException.class)
	ModelAndView handleRuntimeException(RuntimeException e) {
		final ModelAndView modelAndView = new ModelAndView("error");
		modelAndView.addObject("message", e.getMessage());
		return modelAndView;
	}
}
