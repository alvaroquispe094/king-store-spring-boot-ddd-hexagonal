package com.groupal.king.store.domain;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Builder
@Value
public class RefreshToken {

  Long id;
  User user;
  String token;
  Instant expiryDate;

}
