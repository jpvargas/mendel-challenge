package com.mendel.transactions.transaction.domain;

import java.util.Optional;

/**
 * An entity to represent our transactions
 *
 * @author jpvargas
 */
public class Transaction {

  private Long transactionId;

  private Double amount;

  private String type;

  private Optional<Long> parentId;

  private Transaction(final Long id, final Double amount, final String type, final Optional<Long> transaction_id) {
    this.transactionId = id;
    this.amount = amount;
    this.type = type;
    this.parentId = transaction_id;
  }

  public Long getTransactionId() {
    return transactionId;
  }

  public Double getAmount() {
    return amount;
  }

  public String getType() {
    return type;
  }

  public Optional<Long> getParentId() {
    return parentId;
  }

  /**
   * Builder to Transaction Class
   */
  public static class Builder {

    private Long transactionId;
    private Double amount;
    private String type;
    private Optional<Long> parentId;

    public Builder transactionId(final Long transactionId) {
      this.transactionId = transactionId;
      return this;
    }

    public Builder amount(final Double amount) {
      this.amount = amount;
      return this;
    }

    public Builder type(final String type) {
      this.type = type;
      return this;
    }

    public Builder parentId(final Optional<Long> parentId) {
      this.parentId = parentId;
      return this;
    }

    public Transaction build() {
      return new Transaction(transactionId, amount, type, parentId);
    }
  }

  @Override
  public String toString() {
    return "{" + "id=" + transactionId + ", amount=" + amount + ", type='" + type + '\'' + ", transaction_id=" + parentId + '}';
  }
}
