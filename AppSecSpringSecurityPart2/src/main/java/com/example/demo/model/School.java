package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

@Entity
public class School {
	
	/*
	 * The @Id annotation is used to define the primary key of the table.
	 * The @GeneratedValue means that Spring will auto increment this value whenever
	 * a new Student Object is created. 
	 */
	@Id
	@GeneratedValue
	@Min(0)
	private int id;
	
	private String name;
	
	private int rooms;
	
	private int students;

	
	
	/*
	 * Get/Set methods for the 4 fields.
	 */
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getRooms() {
		return rooms;
	}

	public int getStudents() {
		return students;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRooms(int rooms) {
		this.rooms = rooms;
	}

	public void setStudents(int students) {
		this.students = students;
	}
	
	@Override
	public String toString() {
		return "School [id=" + id + ", name=" + name + ", rooms=" + rooms + ", student=" + students + "]";
	}

}  // end class
