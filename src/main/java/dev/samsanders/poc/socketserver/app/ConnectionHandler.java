package dev.samsanders.poc.socketserver.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import org.springframework.scheduling.annotation.Async;

public class ConnectionHandler {

  @Async
  public void handle(Socket clientConnection, CountDownLatch countDownLatch) {
    try {
      PrintWriter printWriter = new PrintWriter(clientConnection.getOutputStream(), true);
      printWriter.println("hi");
      clientConnection.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (countDownLatch != null) {
      countDownLatch.countDown();
    }

  }

}
