package com.mkyong.web.controller;

import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HelloController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printWelcome(ModelMap model, HttpServletRequest request) {

		Map<String, ?> inputFlashMap = RequestContextUtils
				.getInputFlashMap(request);
		
		if (inputFlashMap != null) {
	        model.addAttribute("formResults", inputFlashMap.get("testForm"));
	    }

		model.addAttribute("testForm", new TestForm(
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
		model.addAttribute("message", "Spring 3 MVC Hello World");
		return "hello";

	}

	@RequestMapping(value = "/testAction", method = RequestMethod.POST)
	public String  doSomeThing(ModelMap model,
			@ModelAttribute TestForm testForm, RedirectAttributes redirectAttrs) {
		Random random = new Random(100);
		testForm.setConfirmNumber(random.nextInt(10));

		redirectAttrs.addFlashAttribute(testForm);
		testForm.setName(String.valueOf(Math.random()));
		return "redirect:/suc";
	}
	
	@RequestMapping(value = "/suc", method = RequestMethod.GET)
	public String  doSomeThing() {
		return "suc";
	}

	@RequestMapping(value = "/hello/{name:.+}", method = RequestMethod.GET)
	public ModelAndView hello(@PathVariable("name") String name) {

		ModelAndView model = new ModelAndView();
		model.setViewName("hello");
		model.addObject("msg", name);

		return model;

	}

}