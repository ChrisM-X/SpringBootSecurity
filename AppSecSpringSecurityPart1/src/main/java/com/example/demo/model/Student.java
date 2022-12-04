package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * Overview:
 * 
 * The Student Class will contain 4 fields - id, name, degree, gpa
 * 
 * This Class will be converted to a table in the h2-database automatically
 * by Spring when using the @Entity annotation.
 * 
 * This Class will be the main source of "data" that the application will be
 * manipulating and working with.
 * 
 */


/*
 * The @Entity annotation is used here to define the Student Class as a table in the
 * h2-database.  The table name will be "Student".
 * 
 * Check it out by going to the following URL after starting the application:
 * 		http://localhost:9090/h2-console
 */
@Component
@Entity
public class Student {

	
	/*
	 * Security Update:
	 * 
	 * All the fields in the Class have input validation annotation constraints.
	 * 
	 * These annotations here are useful when a POST/PUT request is
	 * initiated and the parameters are coming from the body example -> '@RequestBody Student'.
	 * The @Valid annotation needs to be added to the method parameter Student^ in order for this
	 * validation to work as soon as the request is submitted.
	 * 
	 * See: createStudent() method in the StudentRestController.java Class
	 */

	/*
	 * The @Id annotation is used to define the primary key of the table.
	 * The @GeneratedValue means that Spring will auto increment this value whenever
	 * a new Student Object is created.
	 */
	@Id
	@GeneratedValue
	@Min(0)
	private int id;
	
	@Pattern(regexp = "[\\w\\d\\s]+")
	@NotEmpty
	private String name;
	
	@Pattern(regexp = "[\\w\\d\\s]+")
	@NotEmpty
	private String degree;
	
	/*
	 * Security Update:
	 * 
	 * The @JsonIgnore annotation is used here as a proof of concept for the Mass Assignment
	 * Vulnerability.  When this annotation is used on the field, if we try to create/update
	 * a Student Object, the "gpa" field will not be bound to the Object. This annotation
	 * (@JsonIgnore) works when the @RequestBody is used to bind the request -> object.
	 * 
	 * Another method to prevent MA vulnerability in this case is to use another "Model" or "DTO" 
	 * Class which omits/excludes the field that we don't want bound to the Object with the HTTP request.
	 * So in this scenario we would create another Class and name it like StudentDTO.java, which
	 * would only include the id, name, and degree fields.
	 * 
	 * See: Use the createStudent() method function in the StudentRestController.java Class to test this out.
	 */
	@JsonIgnore
	@Min(0)
	private double gpa;
	
	
	
	/*
	 * Below are the standard Get/Set + toString() methods.
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public double getGpa() {
		return gpa;
	}

	public void setGpa(double gpa) {
		this.gpa = gpa;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", degree=" + degree + ", gpa=" + gpa + "]";
	}
	
	
} // end class
