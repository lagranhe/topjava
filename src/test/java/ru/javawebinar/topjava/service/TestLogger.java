package ru.javawebinar.topjava.service;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;

public class TestLogger implements TestRule {

    public static final Logger log = LoggerFactory.getLogger(TestLogger.class);

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Instant start = Instant.now();
                base.evaluate();
                Instant finish = Instant.now();
                long timeElapsed = Duration.between(start, finish).toMillis();
                log.info("Test " + description.getMethodName() + " took " + timeElapsed + " milliseconds");
            }
        };
    }
}
