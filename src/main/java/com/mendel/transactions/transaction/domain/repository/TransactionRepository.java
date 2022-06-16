package com.mendel.transactions.transaction.domain.repository;

import com.mendel.transactions.common.exception.EntityNotFoundException;
import com.mendel.transactions.transaction.domain.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class represents the MS repository, using a set as a collection to persist transactions in memory and the
 * singleton pattern to get only one collection instance with threadsafe
 *
 * @author jpvargas
 */
@Component
public class TransactionRepository {

  private final Logger logger = LoggerFactory.getLogger(TransactionRepository.class);

  private static TransactionRepository instance;

  private static Set<Transaction> transactionsList;

  private TransactionRepository() {
    transactionsList = new HashSet<>();
  }

  /**
   * Mutex Access to collection
   *
   * @return singleton instance of Collections -> Set<Transactions>
   */
  public static synchronized TransactionRepository getInstance() {
    if (instance == null) {
      return new TransactionRepository();
    }
    return instance;
  }

  public static Set<Transaction> getTransactionsList() {
    return transactionsList;
  }

  /**
   * @param transaction a transaction to persist in memory
   * @return Transaction object
   */
  public Transaction save(final Transaction transaction) {
    getTransactionsList().add(transaction);
    logger.info("transaction {}. saved in our records", transaction);
    return transaction;
  }

  /**
   * @param type type of transaction to find
   * @return List of id's
   */
  public List<Long> findByType(final String type) {
    return getTransactionsList().stream()
      .filter(transaction -> transaction.getType().equals(type))
      .map(Transaction::getTransactionId)
      .collect(Collectors.toUnmodifiableList());
  }

  /**
   * @param transactionId id of transactions to find
   * @return a Transaction object or Entity Not Found exception
   */
  public Transaction findByTransactionId(final long transactionId) {
    return getTransactionsList().stream()
      .filter(transaction -> transaction.getTransactionId().equals(transactionId))
      .findFirst()
      .orElseThrow(() -> new EntityNotFoundException(Transaction.class,
        String.format("Transaction with id: %d, not found", transactionId)));
  }

  public List<Transaction> findChildrenByTransactionId(final Long transactionId) {
    return getTransactionsList().stream()
      .filter(transaction -> transaction.getParentId().isPresent())
      .filter(transaction -> transaction.getParentId().get().equals(transactionId))
      .collect(Collectors.toUnmodifiableList());
  }

  /**
   * Clear collection
   */
  public void cleanRepository(){
    getTransactionsList().clear();
  }
}
