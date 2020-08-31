package com.credit_suisse.app.test.rules;
import org.joda.time.DateTimeZone;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import java.util.TimeZone;
import static java.util.TimeZone.getTimeZone;
import static java.util.TimeZone.setDefault;
import static org.joda.time.DateTimeZone.*;

public class UtcTimeZoneRule extends TestWatcher {

    private DateTimeZone origDefault = DateTimeZone.getDefault();
    private TimeZone origJavaDefault = TimeZone.getDefault();

    @Override
    protected void starting(Description description) {
        setDefault(TimeZone.getTimeZone("UTC"));
        setDefault(getTimeZone(TimeZone.getTimeZone("UTC").getID()));
    }

    @Override
    protected void finished(Description description) {
        setDefault(origDefault.toTimeZone());
        setDefault(getTimeZone(origJavaDefault.toZoneId()));
    }
}