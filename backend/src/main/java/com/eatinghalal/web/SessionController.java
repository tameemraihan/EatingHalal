package com.eatinghalal.web;

import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/session")
public class SessionController {
  private final boolean demo;

  public SessionController(@Value("${eatinghalal.demo:false}") boolean demo) {
    this.demo = demo;
  }

  @GetMapping
  public Map<String, Object> session(Authentication authentication, CsrfToken csrfToken) {
    return Map.of(
        "username",
        authentication == null ? "" : authentication.getName(),
        "admin",
        authentication != null
            && authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN")),
        "demo",
        demo,
        "csrfToken",
        csrfToken.getToken(),
        "csrfHeader",
        csrfToken.getHeaderName());
  }
}
