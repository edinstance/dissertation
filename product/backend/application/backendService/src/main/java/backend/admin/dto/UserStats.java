package backend.admin.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object for user statistics.
 */
@Getter
@Setter
public class UserStats {

  private long total;

  private long newUserTotal;

  private long deletedUserTotal;

  /**
   * Default constructor.
   */
  public UserStats() {

  }

  /**
   * Constructor with all the information.
   *
   * @param total            the total amount of users.
   * @param newUserTotal     the total of new users.
   * @param deletedUserTotal the total of deleted users.
   */
  public UserStats(long total, long newUserTotal, long deletedUserTotal) {
    this.total = total;
    this.newUserTotal = newUserTotal;
    this.deletedUserTotal = deletedUserTotal;
  }

}
