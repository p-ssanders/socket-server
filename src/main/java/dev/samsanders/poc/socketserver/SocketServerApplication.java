package dev.samsanders.poc.socketserver;

import dev.samsanders.poc.socketserver.app.ConnectionHandler;
import dev.samsanders.poc.socketserver.app.ConnectionListener;
import dev.samsanders.poc.socketserver.app.Server;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SocketServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(SocketServerApplication.class, args);
  }

  @Bean
  public ConnectionHandler connectionHandler() {
    return new ConnectionHandler();
  }

  @Bean
  public ConnectionListener connectionListener(@Value("${app.port}") int port, ConnectionHandler connectionHandler) {
    ConnectionListener connectionListener = new ConnectionListener(port, connectionHandler);
    return connectionListener;
  }

  @Bean
  public Server server(ConnectionListener connectionListener) throws IOException {
    return new Server(connectionListener);
  }

}
