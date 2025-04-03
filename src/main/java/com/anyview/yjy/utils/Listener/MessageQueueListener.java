package com.anyview.yjy.utils.Listener;

import com.anyview.yjy.entity.Movie;
import com.anyview.yjy.entity.Orders;
import com.anyview.yjy.service.MovieService;
import com.anyview.yjy.service.OrderService;
import com.anyview.yjy.entity.Message;
import com.anyview.yjy.utils.MessageQueue.MessageQueue;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.anyview.yjy.utils.code.ORDER_CANCEL;
import static com.anyview.yjy.utils.code.ORDER_UNPAID;

/**
 * 消息队列监听器
 */
@WebListener
public class MessageQueueListener implements ServletContextListener {
    private ScheduledExecutorService thread;
    OrderService orderService = new OrderService();
    MovieService movieService = new MovieService();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        thread = Executors.newSingleThreadScheduledExecutor();

        Runnable task = () -> {
            try{
                Message message = MessageQueue.pop();
                if(message != null){
                    processMessage(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        thread.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
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

    /**
     * 消息处理
     * @param message
     */
    private void processMessage(Message message) {
        Long orderId = message.getOrderId();
        if (orderId == null) return;

        Orders orders = orderService.getById(orderId);
        if (orders == null) {
            return;
        }

        if (orders.getStatus() == ORDER_UNPAID) {
            Movie movie = movieService.adminGetById(orders.getMovie());
            if (movie == null) {
                return;
            }

            orders.setStatus(ORDER_CANCEL); // 设置订单状态为已取消
            movie.setAmount(movie.getAmount() + 1); // 票数 + 1

            orderService.update(orders);
            movieService.updateNoLock(movie);
        }
    }
}
