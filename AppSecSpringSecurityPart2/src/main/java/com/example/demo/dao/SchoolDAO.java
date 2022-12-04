package com.example.demo.dao;

import java.util.List;

import com.example.demo.model.School;

public interface SchoolDAO {
	
	public List<School> findAllByName1(String name);

	public List<School> findAllByName2(String name);
	
	public List<School> findAllByName3(String name);

}
