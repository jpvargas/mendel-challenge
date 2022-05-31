package com.mendel.transactions.common.exception;

/**
 * Base marker exception for our handled exceptions
 *
 * @author jpvargas
 */
public class TransactionException extends RuntimeException{

  public TransactionException() { super();}

  public TransactionException(final String message) {
    super(message);
  }
}

