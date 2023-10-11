package com.plisboa.banking.entity;

import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Param")
@Data
public class Param {

  @Id
  private String id;
  private String name;
  private BigDecimal value;
  private String stringValue;
}
