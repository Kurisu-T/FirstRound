package com.anyview.yjy.utils.Listener;

import com.anyview.yjy.service.OrderService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 监听器, 用于更新观影结束后的订单状态
 */
@WebListener
public class TicketListener implements ServletContextListener {
    private ScheduledExecutorService thread;
    OrderService orderService = new OrderService();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        thread = Executors.newSingleThreadScheduledExecutor();
        Runnable task = () -> {
            try{
                orderService.finishTicket();    // 已支付订单, 更新状态为已完成
                orderService.CancelTicket();    // 未支付订单, 更新状态为已取消
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        // 从程序启动时开始, 没隔一分钟执行一次 task
        thread.scheduleAtFixedRate(task, 0, 1, TimeUnit.MINUTES);
    }

//    关闭线程
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
