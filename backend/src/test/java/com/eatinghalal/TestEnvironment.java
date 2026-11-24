package com.eatinghalal;

import com.sun.net.httpserver.HttpServer;
import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

public final class TestEnvironment {
  public static final EmbeddedPostgres DATABASE;
  public static final HttpServer PROVIDER;
  public static final AtomicReference<String> PROVIDER_RESPONSE =
      new AtomicReference<>("{\"products\":[]}");
  public static final AtomicReference<String> LAST_USER_AGENT = new AtomicReference<>("");

  static {
    try {
      DATABASE = EmbeddedPostgres.builder().setPort(0).start();
      PROVIDER = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
      PROVIDER.createContext(
          "/cgi/search.pl",
          exchange -> {
            LAST_USER_AGENT.set(exchange.getRequestHeaders().getFirst("User-Agent"));
            byte[] body = PROVIDER_RESPONSE.get().getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, body.length);
            exchange.getResponseBody().write(body);
            exchange.close();
          });
      PROVIDER.start();
      Runtime.getRuntime()
          .addShutdownHook(
              new Thread(
                  () -> {
                    PROVIDER.stop(0);
                    try {
                      DATABASE.close();
                    } catch (Exception ignored) {
                    }
                  }));
    } catch (Exception e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  private TestEnvironment() {}
}
