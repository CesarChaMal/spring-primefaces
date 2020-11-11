package com.credit_suisse.app.dao;

import com.credit_suisse.app.test.ServerBaseClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

public class TaskDaoImplTest extends ServerBaseClass {

	private static final Logger logger = LoggerFactory.getLogger(TaskDaoImplTest.class);

	@Autowired
	private ApplicationContext applicationContext;

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindById() {
		assertTrue(true);
	}

	@Test
	public void testFindByTitle() {
		assertTrue(true);
	}

	@Test
	public void testFindByTitleList() {
		assertTrue(true);
	}

	@Test
	public void testFindAll() {
		assertTrue(true);
	}

	@Test
	public void testUpdateTitle() {
		assertTrue(true);
	}

	@Test
	public void testSave() {
		assertTrue(true);
	}

	@Test
	public void testDelete() {
		assertTrue(true);
	}

	@Test
	public void testDeleteById() {
		assertTrue(true);
	}

	@Test
	public void testDeleteByTitle() {
		assertTrue(true);
	}

}
