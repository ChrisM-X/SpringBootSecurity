package com.example.demo.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.dao.SchoolDAO;
import com.example.demo.model.School;

@Service
public class SchoolDAOImpl implements SchoolDAO {
	
	
	@Autowired
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	
	
	/*
	 * Correct method to prevent against SQL injection when using prepared statements with
	 * the NamedParameterJdbcTemplate Class.
	 * 
	 * Correct place holders -> :paramName
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<School> findAllByName1(String name) {
		
		String sql = "select * from school where name = :name";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("name", name);
		
		return namedParameterJdbcTemplate.query(sql, parameters, new UserMapper() );
				
	}  // end findAllByName1
	
	
	
	/*
	 * Incorrect method to prevent against SQL injection when using prepared statements with
	 * the NamedParameterJdbcTemplate Class.
	 * 
	 * NamedParameterJdbcTemplate class is used to execute the query, however, the query is not 
	 * using the correct place holders for it's arguments.  Correct place holder -> :paramName
	 * 
	 * Instead a String format method is being used -> %s
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<School> findAllByName2(String name) {
		
		String sql = String.format("select * from school where name = '%s'", name);
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("name", name);
		
		return namedParameterJdbcTemplate.query(sql, parameters, new UserMapper() );
				
	}  // end findAllByName2
	
	
	
	/*
	 * Method used to test out a different Class called JdbcTemplate.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<School> findAllByName3(String name) {
		
		String sql = String.format("select * from school where name = '%s'", name);
		
		return jdbcTemplate.query(sql, new UserMapper() );
	
	}  // end findAllByName3
	
	
	
	/*
	 * Class/method used to retrieve the results of the SQL queries.
	 */
	class UserMapper implements RowMapper {

		@Override
		public School mapRow(ResultSet rs, int rowNum) throws SQLException {
			School input = new School();
			input.setId(rs.getInt("id"));
			input.setName(rs.getString("name"));
			input.setRooms(rs.getInt("rooms"));
			input.setStudents(rs.getInt("students"));
			return input;
			
		}  // end mapRow
		
	} // end UserMapper class
	
	//TEST return (Student) namedParameterJdbcTemplate.queryForObject(sql, parameters, new UserMapper());
	
	
}  // end SchoolDAOImpl class
