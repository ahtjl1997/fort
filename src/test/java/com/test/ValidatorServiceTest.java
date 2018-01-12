package com.test;

import java.util.Date;

import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.routines.DateValidator;

public class ValidatorServiceTest {

	public static void main(String[] args) {
		// Get the Date validator
	      DateValidator validator = DateValidator.getInstance();

	      // Validate/Convert the date
	      Date fooDate = validator.validate("14/05/2013", "dd/MM/yyyy");
	      if (fooDate == null) {
	          // error...not a valid date
	          return;
	      }
	      
	      System.out.println(GenericValidator.isInt("123"));
	}

}

