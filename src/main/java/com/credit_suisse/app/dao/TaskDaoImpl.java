package com.credit_suisse.app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.credit_suisse.app.bean.domain.Task;

@Repository
public class TaskDaoImpl implements TaskDao {

	private static final Logger logger = LoggerFactory.getLogger(TaskDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private JdbcTemplate jdbcTemplate;
	
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	public void setdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Task findById(int id) {
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
	public void updateTitle(int id, String title) {
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

	@Override
	public void save(Task task) {
		String title = task.getTitle();
		String description = task.getDescription();
		Date due_date = task.getDueDate();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", title);
		params.put("description", description);
		params.put("due_date", due_date);
		
		String sql = "INSERT INTO TASK (title, description, due_date) VALUES (:title, :description, :due_date)";
		
		int status = namedParameterJdbcTemplate.update(sql, params);	
		
		if(status != 0){
			logger.info("Task was inserted with title: "+ params.get("title"));
		} else {
			logger.debug("Task not inserted");
		}	
	}
	
	@Override
	public void delete(Task task) {
		int id = task.getId();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		
		String sql = "DELETE FROM TASK WHERE id=:id";
		
		int status = namedParameterJdbcTemplate.update(sql, params);	
		
		if(status != 0){
			logger.info("Task was deleted with id: "+ params.get("id"));
		} else {
			logger.debug("Task not deleted");
		}	
	}
	
	@Override
	public void deleteById(int id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		
		String sql = "DELETE FROM TASK WHERE id=:id";
		
		int status = namedParameterJdbcTemplate.update(sql, params);	
		
		if(status != 0){
			logger.info("Task was deleted with id: "+ params.get("id"));
		} else {
			logger.debug("Task not deleted");
		}	
	}
	
	@Override
	public void deleteByTitle(String title) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", title);
		
		String sql = "DELETE FROM TASK WHERE title=:title";
		
		int status = namedParameterJdbcTemplate.update(sql, params);	
		
		if(status != 0){
			logger.info("Task was deleted with title: "+ params.get("title"));
		} else {
			logger.debug("Task not deleted");
		}	
	}
	
	private static final class TaskMapper implements RowMapper<Task> {

		public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
			
//			Task task = new Task();
//			task.setId(rs.getInt("id"));
//			task.setTitle(rs.getString("title"));
//			task.setDescription(rs.getString("description"));
//			task.setDueDate(rs.getDate("due_date"));
			
			Task task = new Task.Builder()
					.setId(rs.getInt("id"))
					.setTitle(rs.getString("title"))
					.setDescription(rs.getString("description"))
					.setDueDate(rs.getDate("due_date")).build();
			return task;
		}
	}

}