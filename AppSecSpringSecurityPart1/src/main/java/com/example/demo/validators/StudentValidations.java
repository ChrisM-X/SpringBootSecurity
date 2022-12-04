package com.example.demo.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;


/*
 * The purpose of this Class is to show how we can use custom validation
 * to validate the input from incoming HTTP requests.
 * 
 * Instead of using validation annotations like @Pattern, @Min, etc., we
 * can use a custom validator like below.
 * 
 * See the getOneStudentByName() method in the StudentRestController.java Class.
 * 
 */
@Service
public class StudentValidations {

	/*
	 * The isValid() method will take in a String parameter and verify that the
	 * parameter matches a regex and that the length of the value is less than 
	 * 20 characters.
	 * 
	 * The regex will only accept alphanumeric characters and spaces.
	 * 
	 * If the incoming argument passes these constraints, then the "true" value
	 * will be returned.  If is does not pass then a "false" value will be
	 * returned.
	 */
	public boolean isValid(String value) {
		
		Pattern pattern = Pattern.compile("[\\w\\d\\s]{1,10}");
		
		Matcher matcher = pattern.matcher(value);
		
		if (matcher.matches()) {
			
			return true;
			
		} else {
			
			return false;
			
		} // end if/else
		
		
	} // end method
	
} // end class
