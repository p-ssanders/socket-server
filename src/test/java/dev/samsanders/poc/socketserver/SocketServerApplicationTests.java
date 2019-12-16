package dev.samsanders.poc.socketserver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.samsanders.poc.socketserver.app.ConnectionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootTest
class SocketServerApplicationTests {

  @Value("${app.port}")
  int port;

  @Autowired
  ConnectionListener connectionListener;

  @Autowired
  ThreadPoolTaskExecutor threadPool;

  @Test
  void contextLoads() {
  }

  @Test
  void integration_handlesMultipleConnections() throws InterruptedException, ExecutionException, TimeoutException {
    CountDownLatch countDownLatch = new CountDownLatch(3);
    connectionListener.setCountDownLatch(countDownLatch);

    Future<String> socketClient1 = threadPool.submit(new SocketClient());
    Future<String> socketClient2 = threadPool.submit(new SocketClient());
    Future<String> socketClient3 = threadPool.submit(new SocketClient());

    countDownLatch.await(1000, TimeUnit.MILLISECONDS);

    assertEquals("hi", socketClient1.get(10, TimeUnit.MILLISECONDS));
    assertEquals("hi", socketClient2.get(10, TimeUnit.MILLISECONDS));
    assertEquals("hi", socketClient3.get(10, TimeUnit.MILLISECONDS));
  }

  class SocketClient implements Callable<String> {

    SocketClient() {
    }

    @Override
    public String call() throws Exception {
      Socket socket = new Socket("localhost", port);
      InputStream socketInputStream = socket.getInputStream();
      BufferedReader in = new BufferedReader(new InputStreamReader(socketInputStream));

      StringBuilder result = new StringBuilder();
      String line;
      while ((line = in.readLine()) != null) {
        result.append(line);
      }

      return result.toString();
    }
  }
}
