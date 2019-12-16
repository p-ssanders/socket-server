package dev.samsanders.poc.socketserver.app;

import java.io.IOException;

public class Server {

  public Server(ConnectionListener connectionListener) throws IOException {
    connectionListener.listen();
  }
}
