package com.eatinghalal.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_account")
public class UserAccount {
  @Id public UUID id;

  @Column(nullable = false, unique = true)
  public String username;

  @Column(name = "password_hash", nullable = false)
  public String passwordHash;

  @Column(nullable = false)
  public String role;

  protected UserAccount() {}

  public UserAccount(String username, String passwordHash, String role) {
    this.id = UUID.randomUUID();
    this.username = username;
    this.passwordHash = passwordHash;
    this.role = role;
  }
}
