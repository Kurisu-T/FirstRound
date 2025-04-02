package com.anyview.yjy.utils.Listener;

import com.anyview.yjy.service.OrderService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class TicketListener implements ServletContextListener {
    private ScheduledExecutorService thread;
    OrderService orderService = new OrderService();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        thread = Executors.newSingleThreadScheduledExecutor();
        Runnable task = () -> {
            try{
                orderService.finishTicket();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        thread.scheduleAtFixedRate(task, 0, 1, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (thread != null) {
            thread.shutdownNow();
            try {
                if (!thread.awaitTermination(1, TimeUnit.SECONDS)) {
                    System.err.println("thread closed");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
