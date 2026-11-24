package com.eatinghalal;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import java.io.File;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class LocalDemoLauncher {
  public static void main(String[] args) throws Exception {
    String directory =
        System.getenv()
            .getOrDefault(
                "DEMO_DATABASE_DIRECTORY",
                System.getProperty("java.io.tmpdir") + "/eatinghalal-demo-postgres");
    EmbeddedPostgres database =
        EmbeddedPostgres.builder()
            .setPort(0)
            .setDataDirectory(new File(directory))
            .setCleanDataDirectory(false)
            .start();
    SpringApplication application = new SpringApplication(EatingHalalDemoApplication.class);
    application.setAdditionalProfiles("demo");
    application.setDefaultProperties(
        Map.of(
            "spring.datasource.url",
            database.getJdbcUrl("postgres", "postgres"),
            "spring.datasource.username",
            "postgres",
            "spring.datasource.password",
            "",
            "server.address",
            "127.0.0.1"));
    String[] launch =
        java.util.stream.Stream.concat(
                java.util.Arrays.stream(args),
                java.util.stream.Stream.of(
                    "--spring.datasource.url=" + database.getJdbcUrl("postgres", "postgres"),
                    "--spring.datasource.username=postgres",
                    "--spring.datasource.password=",
                    "--server.address=127.0.0.1"))
            .toArray(String[]::new);
    ConfigurableApplicationContext context = application.run(launch);
    Runtime.getRuntime()
        .addShutdownHook(
            new Thread(
                () -> {
                  context.close();
                  try {
                    database.close();
                  } catch (Exception ignored) {
                  }
                }));
    Thread.currentThread().join();
  }
}
