package ru.javawebinar.topjava;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalTime;

public class BenchmarkRule implements TestRule {

    private static final Logger log = LoggerFactory.getLogger(BenchmarkRule.class);

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                LocalTime start = LocalTime.now();
                base.evaluate();
                LocalTime end = LocalTime.now();
                log.info("Test time: {} ns", Duration.between(end, start).getNano());
            }
        };
    }


}
