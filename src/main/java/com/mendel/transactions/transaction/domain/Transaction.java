package com.mendel.transactions.transaction.domain;

import java.util.Optional;

/**
 * An entity to represent our transactions
 *
 * @author jpvargas
 */
public class Transaction {

  private Long id;

  private Double amount;

  private String type;

  private Optional<Long> transaction_id;

  private Transaction(final Long id, final Double amount, final String type, final Optional<Long> transaction_id) {
    this.id = id;
    this.amount = amount;
    this.type = type;
    this.transaction_id = transaction_id;
  }

  public Long getId() {
    return id;
  }

  public Double getAmount() {
    return amount;
  }

  public String getType() {
    return type;
  }

  public Optional<Long> getTransaction_id() {
    return transaction_id;
  }

  /**
   * Builder to Transaction Class
   */
  public static class Builder {

    private Long id;
    private Double amount;
    private String type;
    private Optional<Long> transaction_id;

    public Builder id(final Long id) {
      this.id = id;
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

    public Builder transactionId(final Optional<Long> transaction_id) {
      this.transaction_id = transaction_id;
      return this;
    }

    public Transaction build() {
      return new Transaction(id, amount, type, transaction_id);
    }
  }

  @Override
  public String toString() {
    return "Transaction{" + "id=" + id + ", amount=" + amount + ", type='" + type + '\'' + ", transaction_id=" + transaction_id + '}';
  }
}
