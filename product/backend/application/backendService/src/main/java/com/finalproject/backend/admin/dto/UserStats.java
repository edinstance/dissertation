package com.finalproject.backend.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserStats {

  private long total;

  private long newUserTotal;

  private long deletedUserTotal;

  public UserStats() {

  }

  public UserStats(long total, long newUserTotal, long deletedUserTotal) {
    this.total = total;
    this.newUserTotal = newUserTotal;
    this.deletedUserTotal = deletedUserTotal;
  }

}
