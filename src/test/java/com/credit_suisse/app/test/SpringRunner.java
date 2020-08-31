package com.credit_suisse.app.test;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.credit_suisse.app.bean.util.FileUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Ignore;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.io.File;
import java.net.MalformedURLException;

public class SpringRunner extends SpringJUnit4ClassRunner {

    private static final Log LOGGER = LogFactory.getLog(SpringRunner.class);

    public SpringRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    protected Statement withBefores(FrameworkMethod method, Object testInstance, Statement statement) {
        ServerBaseClass serverBase = (ServerBaseClass) testInstance;
        if (hasRtdmsDatasetAnnotation(method)) {
            setDataSet(serverBase, method.getAnnotation(SpringDataset.class).value());
        }
        return super.withBefores(method, testInstance, statement);
    }

    private boolean hasRtdmsDatasetAnnotation(FrameworkMethod method) {
        return method.getAnnotation(SpringDataset.class) != null && method.getAnnotation(Ignore.class) == null;
    }

/*
    protected void runChild(FrameworkMethod frameworkMethod, RunNotifier notifier) {
        super.runChild(frameworkMethod, notifier);
    }
*/

    private void setDataSet(ServerBaseClass serverBase, String[] values) {
        Preconditions.checkNotNull(values);
        Preconditions.checkState(values.length > 0);
        try {
            LOGGER.debug("Start preparation of dataset");
            CompositeDataSet compositeDataSet = getCompositeDataSet(getRtdmsDatasetPath(serverBase), values);
            serverBase.setDataSet(compositeDataSet);
        } catch (Exception e) {
            LOGGER.error("DBUNIT SETUP ERROR:\n" + Throwables.getStackTraceAsString(e));
        }
    }

    private CompositeDataSet getCompositeDataSet(String packageName, String[] values) throws MalformedURLException, DataSetException {
        IDataSet[] listDataSet = new IDataSet[values.length];

        for (int i = 0; i < values.length; i++) {

            if (!Strings.isNullOrEmpty(values[i])) {
                FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
                builder.setColumnSensing(true);
                listDataSet[i] = builder.build(
                        new File("./dataset/" + packageName + "/" + values[i] + FileUtil.XML_FILE_EXTENSION)
                );
            }
        }
        return new CompositeDataSet(listDataSet);
    }

    private String getRtdmsDatasetPath(ServerBaseClass serverBase) {
        return serverBase.getClass().getAnnotation(SpringDatasetPath.class) != null ? serverBase.getClass().getAnnotation(SpringDatasetPath.class).value() : "";
    }
}
