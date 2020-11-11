package com.credit_suisse.app.dao;

import com.credit_suisse.app.bean.domain.Task;
import com.credit_suisse.app.test.ServerBaseClass;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.lang.invoke.MethodHandles;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class TaskDaoImplTest extends ServerBaseClass {

	private static final Log logger = LogFactory.getLog(MethodHandles.lookup().lookupClass().getName());

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
//	@Mock
//	@InjectMocks
	private TaskDao taskDao;

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
//        Task task = new Task.Builder().setTitle("title").setDescription("description").setDueDate(new Date()).build();
//        task = new Task();
		logger.info(this.taskDao);
	}

	@Test
	public void testFindById() {
		assertNotNull(taskDao.findById(1));
	}

	@Test
	public void testFindByTitle() {
		assertNotNull(taskDao.findByTitle("task1"));
	}

	@Test
	public void testFindByTitleList() {
		assertNotNull(taskDao.findByTitleList("task"));
	}

	@Test
	public void testFindAll() {
		assertEquals(4, taskDao.findAll().size());
	}

	@Test
	public void testUpdateTitle() {
		taskDao.updateTitle(4, "test");
		Task task = taskDao.findById(4);
		assertEquals("test", task.getTitle());
	}

	@Test
	public void testSave() {
		Task task = new Task.Builder()
				.setId(5)
				.setTitle("task5")
				.build();
//		verify(taskDao).save(task);
		taskDao.save(task);
		task = taskDao.findById(5);
		assertEquals("task5", task.getTitle());
	}

	@Test
	public void testDelete() {
		Task task = taskDao.findById(4);
		taskDao.delete(task);
		task = taskDao.findById(4);
		assertNull(task);
	}

	@Test
	public void testDeleteById() {
		taskDao.deleteById(3);
		Task task = taskDao.findById(3);
		assertNull(task);
	}

	@Test
	public void testDeleteByTitle() {
		taskDao.deleteByTitle("task2");
		Task task = taskDao.findByTitle("task2");
		assertNull(task);
	}

}
