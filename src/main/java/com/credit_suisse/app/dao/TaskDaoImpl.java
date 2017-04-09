package com.credit_suisse.app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.credit_suisse.app.domain.Task;

@Service
public class TaskDaoImpl implements TaskDao {

	private static final Logger logger = LoggerFactory.getLogger(TaskDaoImpl.class);

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	JdbcTemplate jdbcTemplate;
	
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	public void setdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Task findById(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        
		String sql = "SELECT * FROM TASK WHERE id=:id";
		
		Task result = namedParameterJdbcTemplate.queryForObject(
                    sql,
                    params,
                    new TaskMapper());
                    
        return result;
	}

	
	@Override
	public Task findByTitle(String title) {
		
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("title", title);
        
		String sql = "SELECT * FROM TASK WHERE title=:title";
		
		Task result = namedParameterJdbcTemplate.queryForObject(sql, params, new TaskMapper());
        return result;
	}
	
	@Override
	public List<Task> findByTitleList(String title) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", title);
		
		String sql = "SELECT * FROM TASK title=:title";
		
//		Task result = namedParameterJdbcTemplate.queryForObject(sql, params, new TaskMapper());
		List<Task> result = namedParameterJdbcTemplate.query(sql, params, new TaskMapper());
		
		return result.size() == 0 ? null : result;
	}
	
	@Override
	public List<Task> findAll() {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String sql = "SELECT * FROM TASK";
		
        List<Task> result = namedParameterJdbcTemplate.query(sql, params, new TaskMapper());
        
        return result.size() == 0 ? null : result;
	}

	@Override
	public void setTitle(long id, String title) {
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("title", title);

        String sql = "UPDATE TASK set title=:title where id=:id";

        int status = namedParameterJdbcTemplate.update(sql, params);	
        
        if(status != 0){
            logger.info("Title data updated for Task " + params.get("id") + " with value: "+ params.get("title"));
        } else {
        	logger.debug("No task found with id " + params.get("id"));
        }	
	}

	private static final class TaskMapper implements RowMapper<Task> {

		public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
			Task task = new Task();
			task.setId(rs.getLong("id"));
			task.setTitle(rs.getString("title"));
			task.setDescription(rs.getString("description"));
			task.setDueDate(rs.getDate("due_date"));
			return task;
		}
	}

}