package com.erlis.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class StartUpListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(Config.class, PostgresDataSource.class);

        ServletContext context = sce.getServletContext();
        context.setAttribute("context", ctx);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}

