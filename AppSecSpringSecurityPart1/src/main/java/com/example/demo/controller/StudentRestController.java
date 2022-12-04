package com.example.demo.controller;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.validators.StudentValidations;
import com.example.demo.model.Student;
import com.example.demo.repo.StudentRepo;


/*
 * Overview: 
 * 
 * This Controller Class will contain the majority of the logic for the application.
 * 
 * It includes methods to either create, retrieve or delete a Student Object
 * from the database.
 * 
 * This application is more of an API not a web application.  So its more
 * suitable to use Postman for testing here instead of the browser.
 */


/*
 * The @RestController is used here because this Controller functions more as
 * a REST API.  Instead of the responses returning an HTML or JSP page/view like in
 * a web application, here the responses will mostly return data in the form of JSON/text.
 * 
 * The @RequestMapping is used to map a specific request path to to this controller.
 * The parent path will be - http://localhost:9090/studentApi/***
 * 
 * Security Update:
 * 
 * The @Validated annotation was added to the Class level.
 * This is required for Spring to evaluate the constraint annotations on path variables
 * and query parameters.
 */
@RestController
@RequestMapping("/studentApi")
@Validated
@Service
public class StudentRestController {

	// The @Autowired is used here to create a StudentRepo Object at runtime.
	@Autowired
	private StudentRepo repo;
	
	// The @Autowired is used here to create a StudentValidations Object at runtime.
	@Autowired
	private StudentValidations studValid;
	
	@Autowired
	private Student studClass;
	

	
	/*
	 * This getStudents() method will be used to return all of the Student Objects 
	 * from the database.  The JPA repo will query the database and the data will 
	 * be returned in JSON format by default.
	 * 
	 * Method:  GET
	 * Path:  /studentApi/getAllStudents
	 */
	@GetMapping(value = "/getAllStudents")
	public List<Student> getStudents() {
		
		return(repo.findAll());

	} // end method
	
	
	
	/*
	 * This getOneStudentById() method will be used to return a specific Student Object
	 * from the database. An integer value needs to be provided in the path and the
	 * JPA repo will query the database for a Student Object with that value.
	 * 
	 * If the Student Object exists then the application will display its data.
	 * 
	 * If not an error message will be returned.
	 * 
	 * Method:  GET
	 * Path:  /studentApi/getAllStudentsById/{StudentId}
	 * 
	 * Security Update:
	 * 
	 * The @Min(3) constraint was applied to the method parameter "id".
	 * Spring will reject the request immediately if it does not conform to these constraints, but
	 * only if the Class is also annotated with the @Validated annotation.  
	 * 
	 * If either of these^ are missing, then the input validation will not work correctly.
	 */
	@GetMapping(value = "/getAllStudentsById/{StudentId}", produces = "text/plain")
	public ResponseEntity<String> getOneStudentById(@PathVariable("StudentId") @Min(3) int id) {
		
		/*
		 * This line was used to test the input validation annotation constraint @Min(3).
		 * The @Validated annotation also needs to be set at the Class level.
		 * 
		 * If the value in the path does not meet this validation constraint, the application will reject the
		 * request and return an error. However, if the value does meet the constraint, then the following
		 * print statement should execute.
		 * 
		 * (If the value in the path variable does not meet the constraint - Min(3), but we still see the
		 * below statement, it is probably because the @Validated annotation is not set.)
		 *
		 */
		System.out.println("Input validation test " + id);
		
		
		/*
		 * If/else statement used to retrieve the user from the database.
		 */
		if (repo.existsById(id)) {
			
			Student stud = repo.getReferenceById(id);
		
			return ResponseEntity.status(HttpStatus.OK)
					.body("Here is the Student Object: \n\n" + stud.toString());
		
		} else {
			
			return ResponseEntity.badRequest()
					.body("The Student does not exist in the database.");
			
		} // end if/else
		
	} // end method
	
	
	
	/*
	 * This getOneStudentByName() method will be used to return a specific Student Object
	 * from the database. The "name" for the Student needs to be provided in the path and the
	 * JPA repo will query the database for Student Objects that match the value.
	 * 
	 * If the Student Object exists then the application will display its data.
	 * 
	 * If not an error message will be returned.
	 * 
	 * Method:  GET
	 * Path:  /studentApi/getAllStudentsByName/{StudentName}
	 * 
	 * Security Update:
	 * 
	 * Custom validation was used here to ensure that the path variable was validated before being
	 * used in any business logic.
	 * 
	 * See the isValid() method in the StudentValidations.java Class.
	 */
	@GetMapping("/getAllStudentsByName/{StudentName}")
	public ResponseEntity<String> getOneStudentByName(@PathVariable("StudentName") String name) {
		
		/*
		 * Custom Validation Example:
		 * 
		 * Here instead of using annotation validation constraints (@Pattern, @Min, etc.),
		 * we can use a custom validation method.
		 * 
		 * If the path variable input does not meet the validation checks set in the isValid() method, then
		 * the application will return an error message and the business logic will not be processed.
		 */
		
		boolean valid = studValid.isValid(name);
		
		System.out.println("Boolean value is " + valid);
		
		
		try {
			
			if (valid) {
				
				// When the path variable does not pass validations then this line should not execute
				System.out.println("Input validation test " + name);
				
				Student student = repo.findByName(name);
				
				if (repo.existsById(student.getId())) {
					
					return ResponseEntity.status(HttpStatus.OK)
							.body("Here is the Student Object: \n\n" + student.toString());
					
				} else {
					
					return ResponseEntity.badRequest()
							.body("The Student does not exist in the database.");
					
				} // end inner if/else
				
			} else {
				
				return ResponseEntity.badRequest()
						.body("Student name did not pass input validation checks."); 
				
			} // end outer if/else
		
		} catch (Exception e) {
			
			System.out.println("Error message is here: " + e.getMessage());
			
			return ResponseEntity.internalServerError()
					.body("The Student does not exist in the database.");
			
		} // end try/catch
		
		
	} // end method
	
	
	
	/*
	 * This createStudent() method will be used to create a new Student Object and 
	 * the JPA repo will save it in the database.
	 * 
	 * The data needs to be sent with a JSON body format.  The "keys" of the JSON body 
	 * have the same names as the Student.java Class fields.
	 * 
	 * The @RequestBody annotation is used to enable automatic deserialization of the 
	 * HttpRequest body onto a Java Object.  In the response, we will see the created
	 * Student Object in JSON format by default.
	 * 
	 * Method:  POST
	 * Path:  /studentApi/createStudent
	 * 
	 * Test data is provided below.
	 * 
	 * Security Update:
	 * 
	 * Mass Assignment Vulnerability - submit a post request here with the complete Student 
	 * Object - name, degree, gpa.  Ensure that the @JsonIgnore annotation is set on the
	 * Student.java Class.  In the response, we should see the field that was 
	 * annotated with @JsonIgnore was not populated.  Verify this by calling the /getAllStudents.
	 * 
	 * The @Valid annotation was applied to the complex type of Student in the parameter.  This annotation is used
	 * to validate the request body payloads in POST/PUT requests as soon as the request is submitted.  
	 * This is required along with the Student.java Class implementing proper validation constraints on its 
	 * fields.  If either of these are missing then the validation will not kick in right away.
	 */
	@PostMapping(path = "/createStudent", consumes = {"application/json"})
	public Student createStudent(@RequestBody @Valid Student student) {
		
		/*
		 * These 2 lines are used to show why persistent layer (Model/Entity) validation alone
		 * is not enough.  That validation is triggered when the Student repo is called.  Any 
		 * business logic before will still get processed.  
		 * 
		 * To fix this, we need to use @Valid in the Student parameter to tell Spring to make 
		 * sure the input meets the validation annotation constraints specified in the Student.java 
		 * before the input data is used in anyway.
		 */
		System.out.println(student.getName());
		System.out.println(student.getDegree());
		
		
		/*
		 * Test Data to use in Postman:
{
    "name": "Bob",
    "degree": "Math",
    "gpa": 4.0
}
		 */
		
		return(repo.save(student));
		
	} // end method
	
	
	
	/*
	 * The deleteStudent() method will be used to delete a Student Object from the
	 * database.  The "id" value for that Object needs to be provided in the path.
	 * 
	 * If the Student Object exists then the JPA repo will retrieve the complete Object
	 * using the "id" value and delete it from the database.
	 * 
	 * If the Student does not exist then an error will be returned.
	 * 
	 * Method:  DELETE
	 * Path:  /studentApi/deleteStudent/{StudentId}
	 * 
	 * Security Update:
	 * 
	 * The @Min(3) constraint was applied to the "studId" parameter and the @Validated annotation
	 * was added to the Class.
	 */
	@DeleteMapping("/deleteStudent/{StudentId}")
	public ResponseEntity<String> deleteStudent(@PathVariable("StudentId") @Min(3) int studId) {
		
		if (repo.existsById(studId)) {
			
			Student stud = repo.getReferenceById(studId);
			repo.delete(stud);
		
			return ResponseEntity.status(HttpStatus.OK)
					.body("The following student has been deleted from the database: \n\n" + stud.toString());
		
		} else {
			
			return ResponseEntity.badRequest()
					.body("The Student does not exist in the database.");
			
		} // end if/else
		
	} // end method
	
	
		
	/*
	 * This method was to understand how to update an existing Student Object.
	 */
	@PutMapping("/editStudent/{id}")
	public Student editStudent(@PathVariable int id, @RequestBody Student stud) {
		
		if(repo.existsById(id)) {
			
			studClass = repo.getReferenceById(id);
			
			System.out.println(studClass.getName());
			
			studClass.setName(stud.getName());
			
			System.out.println(studClass.getName());
			
		}
		
		return(repo.save(studClass));
		
	}  // end method
	
	
	
} // end class
