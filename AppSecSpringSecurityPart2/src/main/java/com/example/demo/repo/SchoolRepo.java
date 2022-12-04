package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.School;

@Repository
public interface SchoolRepo extends JpaRepository<School, Integer> {

	/*
	 * Creating these Spring Data JPA queries to test how they work 
	 * and determine if they are vulnerable to SQL injection.
	 * 
	 * References:
	 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
	 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
	 * https://thorben-janssen.com/what-is-spring-data-jpa-and-why-should-you-use-it/
	 * 
	 * https://www.reddit.com/r/javahelp/comments/9nrxwp/is_spring_hibernate_jpa_native_query_sql/
	 * https://stackoverflow.com/questions/41661193/is-spring-data-jpa-safe-against-sql-injection
	 * 
	 * Are these Spring Data JPA methods below safe from SQL injection?
	 * 		It is hard to find references/documentation that mention they are safe from
	 * 		SQL injection.  However, from the few references mentioned above, as long
	 * 		as these queries are not created dynamically and the parameters are not being
	 * 		concatenated to the queries, these type of methods below are safe from SQL
	 * 		injection.
	 * 
	 * 		After performing a few SQLi tests, the methods below were not vulnerable to SQLi.
	 * 
	 * 		**** However, still recommended to test these out if planning to use them in a real
	 * 		application. ****
	 */
	
	// This will return the School Objects by the name provided
	List<School> findByName(String name);
	
	// This will return the School Objects by the name provided and ordered by rooms in ascending order
	List<School> findByNameOrderByRoomsAsc(String name);
	
	// This will return the School Objects that contain a student value thats less than the integer provided
	List<School> findByStudentsLessThan(int num); 
	
	
}  // end interface
