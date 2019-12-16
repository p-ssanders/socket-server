# socket-server

Demonstrates a TCP socket server that delegates connection handling to separate threads.

* `SocketServerApplication` is the point of entry, and contains configuration. Notice the `@EnableAsync` annotation.
* `Server` is used to "start" the `ConnectionListener` by invoking the `@Async` `listen()` method.
* `ConnectionListener` is used to listen for new connections.
* `ConnectionHandler` is used to handle connections. Its `handle` method is invoked by the `ConnectionListener.listen()` method upon accepting a new connection.
The `CountDownLatch` is used to ensure deterministic tests.

### Build
`mvn clean install`

### Test
Run the `SocketServerApplicationTests`