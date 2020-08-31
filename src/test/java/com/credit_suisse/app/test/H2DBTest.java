package com.credit_suisse.app.test;

import com.credit_suisse.app.bean.TaskBean;
import com.credit_suisse.app.bean.domain.Task;
import com.credit_suisse.app.dao.TaskDao;
import com.credit_suisse.app.test.SpringDataset;
import com.credit_suisse.app.test.SpringDatasetPath;
import com.credit_suisse.app.test.ServerBaseClass;
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

import java.lang.invoke.MethodHandles;
import java.util.Date;

import static org.junit.Assert.assertNotNull;

@SpringDatasetPath("task")
public class H2DBTest extends ServerBaseClass {

    private static final Log LOGGER = LogFactory.getLog(MethodHandles.lookup().lookupClass().getName());

    @Rule public UtcTimeZoneRule utcRule = new UtcTimeZoneRule();
    @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();
    @Rule public ExpectedException thrown = ExpectedException.none();

    //    @Mock
//    @Autowired
//    private TaskBean taskBean;
    private Task task;
    @Mock private TaskDao taskDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
//        Task task = new Task.Builder().setTitle("title").setDescription("description").setDueDate(new Date()).build();
        task = new Task();
        this.prepareMockForImport(task);
        LOGGER.info(this.taskDao);
    }

    void prepareMockForImport(Task task) {
        try {
            Mockito.when(task.getDescription()).thenReturn("description");
            Mockito.when(task.getTitle()).thenReturn("title");
            Mockito.when(task.getDueDate()).thenReturn(new Date());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @SpringDataset({ "task" })
    public void whenServiceResponseIsCodification1() throws Exception {
        this.prepareMockForImport(this.task);
//        assertNotNull(returnStatus);
    }
}
