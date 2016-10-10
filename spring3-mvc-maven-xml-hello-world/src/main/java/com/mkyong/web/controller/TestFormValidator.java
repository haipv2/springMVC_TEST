package com.mkyong.web.controller;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TestFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return TestForm.class.equals(clazz);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		TestForm testForm = (TestForm) arg0;
		if (testForm.getFromDate() != null
				&& testForm.getFromDate().after(testForm.toDate)) {
			errors.rejectValue("fromDate", "From date must not be after to date.");
		}

	}

}
