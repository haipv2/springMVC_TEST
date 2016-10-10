package com.mkyong.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
public class HelloController {

	@Autowired
	TestFormValidator validator;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// binder.setValidator(validator);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printWelcome(ModelMap model, HttpServletRequest request) {

		Map<String, ?> inputFlashMap = RequestContextUtils
				.getInputFlashMap(request);

		if (inputFlashMap != null) {
			model.addAttribute("testForm", inputFlashMap.get("testForm"));
		}
		model.addAttribute("testForm", new TestForm());
		model.addAttribute("message", "Spring 3 MVC Hello World");
		List<String> list = getList();

		model.addAttribute("lists", list);
		return "hello";

	}

	@RequestMapping(value = { "/phoneList/{type}", "/phoneList" }, method = RequestMethod.GET)
	public String printPhoneList(ModelMap model, HttpServletRequest request,
			@PathVariable Map<String, String> pathVariablesMap) {
		PagedListHolder<Phone> productList = null;

		String type = pathVariablesMap.get("type");

		if (null == type) {
			// First Request, Return first set of list
			List<Phone> phonesList = getPhoneslist();

			productList = new PagedListHolder<Phone>();
			productList.setSource(phonesList);
			productList.setPageSize(2);

			request.getSession().setAttribute("phonesList", productList);

			printPageDetails(productList);

		} else if ("next".equals(type)) {
			// Return next set of list
			productList = (PagedListHolder<Phone>) request.getSession()
					.getAttribute("phonesList");

			productList.nextPage();

			printPageDetails(productList);

		} else if ("prev".equals(type)) {
			// Return previous set of list
			productList = (PagedListHolder<Phone>) request.getSession()
					.getAttribute("phonesList");

			productList.previousPage();

			printPageDetails(productList);

		} else {
			// Return specific index set of list
			System.out.println("type:" + type);

			productList = (PagedListHolder<Phone>) request.getSession()
					.getAttribute("phonesList");

			int pageNum = Integer.parseInt(type);

			productList.setPage(pageNum);

			printPageDetails(productList);
		}

		return "phoneList";

	}

	private void printPageDetails(PagedListHolder productList) {

		System.out.println("curent page - productList.getPage() :"
				+ productList.getPage());

		System.out.println("Total Num of pages - productList.getPageCount :"
				+ productList.getPageCount());

		System.out.println("is First page - productList.isFirstPage :"
				+ productList.isFirstPage());

		System.out.println("is Last page - productList.isLastPage :"
				+ productList.isLastPage());
	}

	public List<Phone> getPhoneslist() {

		List<Phone> phonesList = new ArrayList<Phone>();

		phonesList.add(buildPhone("1", "Samsung Galaxy Y"));
		phonesList.add(buildPhone("2", "Nokia Lumia"));
		phonesList.add(buildPhone("3", "Moto G"));
		phonesList.add(buildPhone("4", "Lenovo A 7000 white"));
		phonesList.add(buildPhone("5", "Sony XPeria"));
		phonesList.add(buildPhone("6", "IPHONE"));
		phonesList.add(buildPhone("7", "MOTOROLA"));
		phonesList.add(buildPhone("8", "bPhone"));
		return phonesList;
	}

	private Phone buildPhone(String id, String name) {

		Phone phone = new Phone(id, name);
		return phone;
	}

	@RequestMapping(value = "/{pageId}", method = RequestMethod.GET)
	public String printWelcomePaging(ModelMap model,
			HttpServletRequest request, @PathVariable int pageId) {

		Map<String, ?> inputFlashMap = RequestContextUtils
				.getInputFlashMap(request);

		if (inputFlashMap != null) {
			model.addAttribute("testForm", inputFlashMap.get("testForm"));
		}
		model.addAttribute("testForm", new TestForm());
		model.addAttribute("message", "Spring 3 MVC Hello World");

		int total = 2;
		if (pageId == 1) {
		} else {
			pageId = (pageId - 1) * total + 1;
		}

		List<String> list = getList();
		model.addAttribute("lists", list);

		return "hello";

	}

	private List<String> getList() {

		List<String> list = new ArrayList<String>();
		list.add("List A");
		list.add("List B");
		list.add("List C");
		list.add("List D");
		list.add("List 1");
		list.add("List 2");
		list.add("List 3");

		return list;

	}

	@RequestMapping(value = "/testAction", method = RequestMethod.POST)
	public String doSomeThing(ModelMap model,
			@ModelAttribute @Validated TestForm testForm, BindingResult result,
			RedirectAttributes redirectAttrs) {
		if (result != null && result.hasErrors()) {
			return "hello";
		}

		Random random = new Random(100);
		testForm.setConfirmNumber(random.nextInt(10));
		redirectAttrs.addFlashAttribute(testForm);
		testForm.setName(String.valueOf(Math.random()));
		return "redirect:/suc";
	}

	@RequestMapping(value = "/suc", method = RequestMethod.GET)
	public String doSomeThing() {
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