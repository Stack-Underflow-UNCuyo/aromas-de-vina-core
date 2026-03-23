package com.aromasdevina.core;

import com.aromasdevina.core.config.AsyncSyncConfiguration;
import com.aromasdevina.core.config.DatabaseTestcontainer;
import com.aromasdevina.core.config.JacksonConfiguration;
import com.aromasdevina.core.config.TestSecurityConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(
    classes = {
        CoreApp.class,
        JacksonConfiguration.class,
        AsyncSyncConfiguration.class,
        TestSecurityConfiguration.class,
        com.aromasdevina.core.config.JacksonHibernateConfiguration.class,
    }
)
@ImportTestcontainers(DatabaseTestcontainer.class)
public @interface IntegrationTest {}
