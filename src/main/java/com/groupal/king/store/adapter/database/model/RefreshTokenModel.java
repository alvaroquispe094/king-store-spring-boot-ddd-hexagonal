package com.groupal.king.store.adapter.database.model;

import com.groupal.king.store.domain.RefreshToken;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
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

  /*public RefreshTokenModel() {
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
  }*/

  public RefreshToken toDomain(){
    return RefreshToken.builder()
            .id(id)
            .user(user.toDomain())
            .token(token)
            .expiryDate(expiryDate)
            .build();
  }

  public static RefreshTokenModel fromDomain(RefreshToken refreshToken){
    return RefreshTokenModel.builder()
            .id(refreshToken.getId())
            .user(UserModel.fromDomain(refreshToken.getUser()))
            .token(refreshToken.getToken())
            .expiryDate(refreshToken.getExpiryDate())
            .build();
  }

}
