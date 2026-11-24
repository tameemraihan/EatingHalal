package com.eatinghalal.config;

import com.eatinghalal.model.*;
import com.eatinghalal.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ConditionalOnProperty(name = "eatinghalal.demo", havingValue = "true")
public class DemoDataConfiguration {
  @Bean
  CommandLineRunner seedDemo(
      UserAccountRepository users,
      ProductRepository products,
      AssessmentRepository assessments,
      PasswordEncoder passwords) {
    return args -> {
      for (String name : new String[] {"demo", "other", "admin"})
        if (users.findByUsername(name).isEmpty())
          users.save(
              new UserAccount(
                  name,
                  passwords.encode("demo-password"),
                  name.equals("admin") ? "ADMIN" : "USER"));
      if (!products.findTop30ByNameContainingIgnoreCaseOrderByNameAsc("Demo Oat Drink").isEmpty())
        return;
      Product drink =
          products.save(
              new Product(
                  "Demo Oat Drink",
                  "Synthetic classroom fixture",
                  "1 litre",
                  "Synthetic evidence",
                  "Demo only",
                  "Synthetic demo",
                  "",
                  null));
      assessments.save(
          new HalalAssessment(
              drink,
              1,
              "Halal",
              "Synthetic classroom result for demonstrating the application. Not a real dietary"
                  + " assessment.",
              "Synthetic evidence snapshot",
              "synthetic-demo"));
      Product sweets =
          products.save(
              new Product(
                  "Demo Gelatine Sweets",
                  "Synthetic classroom fixture",
                  "100 g",
                  "Synthetic evidence",
                  "Demo only",
                  "Synthetic demo",
                  "",
                  null));
      assessments.save(
          new HalalAssessment(
              sweets,
              1,
              "Haram",
              "Synthetic classroom result; not a real dietary assessment.",
              "Synthetic evidence snapshot",
              "synthetic-demo"));
      products.save(
          new Product(
              "Demo Unassessed Sauce",
              "Synthetic classroom fixture",
              "200 ml",
              "",
              "",
              "Synthetic demo",
              "",
              null));
    };
  }
}
