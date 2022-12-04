package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.daoImpl.SchoolDAOImpl;
import com.example.demo.model.School;
import com.example.demo.repo.SchoolRepo;

@RestController
@RequestMapping("/schoolApi")
public class SchoolRestController {
	
	@Autowired
	private SchoolRepo repo;
	
	@Autowired
	private SchoolDAOImpl dao;
	

	
	/*
	 * Method used to retrieve all of the School Objects in the database.
	 */
	@GetMapping(value = "/getAllSchools")
	public List<School> getSchools() {
		
		return(repo.findAll());
		
	} // end getSchools
	
	
	
	/*
	 * Method used to create a School Object and save it onto the database.
	 */
	@PostMapping(path = "/createSchool", consumes = {"application/json"})
	public School createSchool(@RequestBody School school) {
	
		return(repo.save(school));
		
	} // end createSchool
	
	
	
	/*
	 * Method used to test out the secure implementation of the NamedParameterJdbcTemplate Class.
	 */
	@GetMapping("/getAllSchoolsByNameDAO1/{SchoolName}")
	public List<School> getOneSchoolByNameDAO1(@PathVariable("SchoolName") String name) {
		
		return dao.findAllByName1(name);
		
	}  // end getOneSchoolByNameDAO1
	
	
	
	/*
	 * Method used to test out the INsecure implementation of the NamedParameterJdbcTemplate Class.
	 */
	@GetMapping("/getAllSchoolsByNameDAO2/{SchoolName}")
	public List<School> getOneSchoolByNameDAO2(@PathVariable("SchoolName") String name) {
		
		return dao.findAllByName2(name);
		
	}  // end getOneSchoolByNameDAO2
	
	
	
	/*
	 * Method used to test out the functionality of a Class called JdbcTemplate.
	 */
	@GetMapping("/getAllSchoolsByNameDAO3/{SchoolName}")
	public List<School> getOneSchoolByNameDAO3(@PathVariable("SchoolName") String name) {
		
		return dao.findAllByName3(name);
		
	}  // end getOneSchoolByNameDAO3
	
	
	
	
	/*
	 * Method used to test out the findByName(String name) method in SchoolRepo.java Class
	 * and to see if these Spring Data JPA method queries are vulnerable to SQL injection.
	 */
	@GetMapping("/jpa/{name}")
	public List<School> getSchoolByNameJPA(@PathVariable("name") String name) {
		
		return repo.findByName(name);
		
	} // end getSchoolByNameJPA
	
	
	
	/*
	 * Method used to test out the findByNameOrderByRoomsAsc(String name) method in SchoolRepo.java 
	 * Class and to see if these Spring Data JPA method queries are vulnerable to SQL injection.
	 */
	@GetMapping("/jpa2/{name}")
	public List<School> getSchoolByNameJPA2(@PathVariable("name") String name) {
		
		return repo.findByNameOrderByRoomsAsc(name);
		
	} // end getSchoolByNameJPA2
	
	
	
	/*
	 * Method used to test out the findByStudentsLessThan(int num) method in SchoolRepo.java 
	 * Class and to see if these Spring Data JPA method queries are vulnerable to SQL injection.
	 */
	@GetMapping("/jpa3/{number}")
	public List<School> getSchoolByNameJPA3(@PathVariable("number") int num) {
		
		return repo.findByStudentsLessThan(num);
		
	} // end getSchoolByNameJPA3
	
	
	
}  // end class
