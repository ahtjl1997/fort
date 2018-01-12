package com.test;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.util.ValidatorUtils;

public class TestValidator {
	public static boolean validateRequired(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		System.out.println(GenericValidator.isBlankOrNull(value));;
		return GenericValidator.isBlankOrNull(value);
	}
}
