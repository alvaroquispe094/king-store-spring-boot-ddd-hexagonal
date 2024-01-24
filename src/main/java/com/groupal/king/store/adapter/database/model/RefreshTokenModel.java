package com.groupal.king.store.adapter.database.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "refreshtoken", schema = "users")
public class RefreshTokenModel {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private UserModel user;

  @Column(nullable = false, unique = true)
  private String token;

  @Column(nullable = false)
  private Instant expiryDate;

  public RefreshTokenModel() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public UserModel getUser() {
    return user;
  }

  public void setUser(UserModel user) {
    this.user = user;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Instant getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(Instant expiryDate) {
    this.expiryDate = expiryDate;
  }

}
