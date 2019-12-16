package dev.samsanders.poc.socketserver.app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import org.springframework.scheduling.annotation.Async;

public class ConnectionListener {

  private final ConnectionHandler connectionHandler;
  private final int port;
  private CountDownLatch countDownLatch;

  public ConnectionListener(int port, ConnectionHandler connectionHandler) {
    this.port = port;
    this.connectionHandler = connectionHandler;
  }

  public void setCountDownLatch(CountDownLatch countDownLatch) {
    this.countDownLatch = countDownLatch;
  }

  @Async
  public void listen() throws IOException {
    ServerSocket serverSocket = new ServerSocket(port);
    while (true) {
      try {
        Socket clientConnection = serverSocket.accept();
        connectionHandler.handle(clientConnection, countDownLatch);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

}
