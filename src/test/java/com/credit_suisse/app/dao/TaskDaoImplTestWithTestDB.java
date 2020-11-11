package com.credit_suisse.app.dao;

import com.credit_suisse.app.bean.domain.Task;
import com.credit_suisse.app.test.ServerBaseClass;
import com.credit_suisse.app.test.SpringDataset;
import com.credit_suisse.app.test.SpringDatasetPath;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.lang.invoke.MethodHandles;
import static org.junit.Assert.*;

@SpringDatasetPath("task")
public class TaskDaoImplTestWithTestDB extends ServerBaseClass {

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
	@SpringDataset({ "task" })
	public void testFindById() {
		assertNotNull(taskDao.findById(1));
	}

	@Test
	@SpringDataset({ "task" })
	public void testFindByTitle() {
		assertNotNull(taskDao.findByTitle("task1"));
	}

	@Test
	@SpringDataset({ "task" })
	public void testFindByTitleList() {
		assertNotNull(taskDao.findByTitleList("task"));
	}

	@Test
	@SpringDataset({ "task" })
	public void testFindAll() {
		assertEquals(4, taskDao.findAll().size());
	}

	@Test
	@SpringDataset({ "task" })
	public void testUpdateTitle() {
		taskDao.updateTitle(1, "test");
		Task task = taskDao.findById(1);
		assertEquals("test", task.getTitle());
	}

	@Test
	@SpringDataset({ "task" })
	public void testSave() {
		Task task = new Task.Builder()
				.setId(8)
				.setTitle("task8")
				.build();
//		verify(taskDao).save(task);
		taskDao.save(task);
		task = taskDao.findById(8);
		assertEquals("task8", task.getTitle());
	}

	@Test
	@SpringDataset({ "task" })
	public void testDelete() {
		Task task = taskDao.findById(1);
		taskDao.delete(task);
		task = taskDao.findById(1);
		assertNull(task);
	}

	@Test
	@SpringDataset({ "task" })
	public void testDeleteById() {
		taskDao.deleteById(1);
		Task task = taskDao.findById(1);
		assertNull(task);
	}

	@Test
	@SpringDataset({ "task" })
	public void testDeleteByTitle() {
		taskDao.deleteByTitle("task5");
		Task task = taskDao.findByTitle("task5");
		assertNull(task);
	}

}
