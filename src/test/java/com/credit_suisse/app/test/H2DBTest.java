package com.credit_suisse.app.test;

import com.credit_suisse.app.bean.domain.Task;
import com.credit_suisse.app.dao.TaskDao;
import com.credit_suisse.app.test.rules.UtcTimeZoneRule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.lang.invoke.MethodHandles;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@SpringDatasetPath("task")
public class H2DBTest extends ServerBaseClass {

    private static final Log logger = LogFactory.getLog(MethodHandles.lookup().lookupClass().getName());

    @Rule public UtcTimeZoneRule utcRule = new UtcTimeZoneRule();
    @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();
    @Rule public ExpectedException thrown = ExpectedException.none();

//    @InjectMocks
//    @Autowired
    @Mock
    private Task task;

//    @Mock
    @Autowired
    private TaskDao taskDao;

//    @Autowired
//    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
//        Task task = new Task.Builder().setTitle("title").setDescription("description").setDueDate(new Date()).build();
//        task = new Task();
        this.prepareMockForImport(task);
        logger.info(this.taskDao);
    }

    void prepareMockForImport(Task task) {
        try {
            Mockito.when(task.getDescription()).thenReturn("description of task1");
            Mockito.when(task.getTitle()).thenReturn("task1");
            Mockito.when(task.getDueDate()).thenReturn(new Date());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @SpringDataset({ "task" })
    public void whenServiceResponseIsCodification1() throws Exception {
        this.prepareMockForImport(this.task);
        Map<String, Object> params = new HashMap<String, Object>();
        String sql = "SELECT * FROM TASK";
//        List<Task> tasksList = namedParameterJdbcTemplate.query(sql, params, new H2DBTest.TaskMapper());
        List<Task> tasksList = this.getJdbcTemplate().query(sql, new H2DBTest.TaskMapper());
        assertNotNull(tasksList);
        assertEquals(4, tasksList.size());
    }

    private static final class TaskMapper implements RowMapper<Task> {
        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            Task task = new Task.Builder()
                    .setId(rs.getInt("id"))
                    .setTitle(rs.getString("title"))
                    .setDescription(rs.getString("description"))
                    .setDueDate(rs.getDate("due_date")).build();
            return task;
        }
    }

}
