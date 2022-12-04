package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.demo.model.Student;

/*
 * This JpaRepository will allow us to perform CRUD operations on the
 * Student Class/Table.  The primary key of the Student Class is of type Integer.
 * Every Model Class needs to have a "repository" if we want to perform CRUD
 * operations with it.
 */
@Service
public interface StudentRepo extends JpaRepository<Student, Integer> {

	/*
	 * Spring Data JPA will automatically create the method below
	 * findByName(), which will take in an "String name" parameter and
	 * retrieve a Student Object that matches this "name" value.
	 * 
	 * This works because in the Student.java Class, we have a field called
	 * "name".  We just need to use the same naming convention below and Spring JPA
	 * can create these methods automatically:
	 * 		- findBy{FieldName}(FieldType fieldname)
	 * 
	 * Spring will use the "name" to create a SELECT query to 
	 * retrieve the Student Object.
	 * 
	 * More Notes:
	 * ***Is SQLi possible here?? <- Look at the AppSecSpringSecurityPart2 project
	 * 
	 * Some resources to look at:
	 * https://stackoverflow.com/questions/41661193/is-spring-data-jpa-safe-against-sql-injection
	 * https://www.baeldung.com/sql-injection
	 */
	Student findByName(String name);
	
	
	/*
	 * The below JPA methods are for testing purposes.
	 * 
	 * Reference:  https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
	 */
	
	/* The below method will find the Student objects by Name and order them
	 * by Degree in Ascending order.  Look at the AppSecSpringSecurityPart2 project
	 */
	List<Student> findByNameOrderByDegreeAsc(String name);
	
	
} // end interface
