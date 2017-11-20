package gtev.servlet;

import gtev.cli.RecordReceiverSerialConnection;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by cy on 11/15/2017.
 */
//@WebListener
public class BackgroundScheduler implements ServletContextListener {

    private ExecutorService executor;

    public void contextInitialized(ServletContextEvent event) {
        executor = Executors.newSingleThreadExecutor();
        executor.submit(new RecordReceiverSerialConnection()); // Task should implement Runnable.
    }

    public void contextDestroyed(ServletContextEvent event) {
        executor.shutdown();
    }

}