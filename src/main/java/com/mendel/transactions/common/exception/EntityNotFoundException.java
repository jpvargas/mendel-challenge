package com.mendel.transactions.common.exception;

import com.mendel.transactions.transaction.domain.Transaction;

public class EntityNotFoundException extends TransactionException{

  /**
   * The entity type
   */
  private Class type;

  public EntityNotFoundException() {
  }

  public EntityNotFoundException(Class type, String message) {
    super(message);
    this.type = type;
  }

  @Override
  public String getMessage() {
    return String.format("[%s] %s", type.getName(), super.getMessage());
  }
}
