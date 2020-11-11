package com.credit_suisse.app.test;

import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import static org.dbunit.operation.DatabaseOperation.CLEAN_INSERT;
import static org.dbunit.operation.DatabaseOperation.DELETE_ALL;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@RunWith(CustomSpringRunner.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "h2")
//@ContextConfiguration(classes = SpringRootConfig.class)
@ContextConfiguration(locations = {"file:src/main/**/applicationContext-test.xml"})
public abstract class ServerBaseClass {

    @Autowired
    private DataSource ds;

    @Autowired
    private IDatabaseTester databaseTester;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private TransactionStatus transaction;

    private boolean useTransactions = true;

    private boolean cleanDatabaseBeforeEachTest = true;

    protected ServerBaseClass() {
    }

    @Resource(name="dataSource")
    public void setDs(DataSource dataSource) {
        ds = dataSource;
    }

    public void setUseTransactions(boolean useTransactions) {
        this.useTransactions = useTransactions;
    }

    @Deprecated
    public void doNotCleanDatabaseBeforeEachTest() {
        this.cleanDatabaseBeforeEachTest = false;
    }

    public void setDataSet(IDataSet dataSet) {
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(CLEAN_INSERT);
        databaseTester.setTearDownOperation(DELETE_ALL);
        if(!cleanDatabaseBeforeEachTest) {
            try {
                databaseTester.onSetup();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Before
    public void before() {
        if(useTransactions) {
            transaction = createTransaction();
            transaction.setRollbackOnly();
        }
        try {
            cleanDBAndLoadDataBeforeTestIfNeeded();
        } catch (Exception e) {
            e.printStackTrace();
        }
        beforeTest();
    }

    public void beforeTest() {

    }

    @After
    public void after() {
        afterTest();
        if(useTransactions) {
            transactionManager.rollback(transaction);
        }
        try {
            if(cleanDatabaseBeforeEachTest) {
                databaseTester.onTearDown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cleanDBAndLoadDataBeforeTestIfNeeded() {
        if(cleanDatabaseBeforeEachTest) {
            // Clean stuff that can be in db before test
            JdbcTemplate jdbcTemplate = getJdbcTemplate();
            if (databaseTester.getDataSet() != null) {
                try {
                    // Load test data
                    databaseTester.onSetup();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            jdbcTemplate.update("DELETE from TASK");
        }
    }

    public void afterTest() {

    }

    private TransactionStatus createTransaction() {
        return transactionManager.getTransaction(new TransactionTemplate());
    }

    protected DataSource getDs() {
        return ds;
    }

    protected JdbcTemplate getJdbcTemplate() {
        return  new JdbcTemplate(getDs());
    }

    protected void createAliasForOracleObject(String aliasName, String alias) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        jdbcTemplate.execute("CREATE ALIAS " + aliasName + " FOR \"" + alias + "\"");
    }

    protected void dropAliasForOracleObject(String aliasName) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        jdbcTemplate.execute("drop ALIAS " + aliasName);
    }
}
