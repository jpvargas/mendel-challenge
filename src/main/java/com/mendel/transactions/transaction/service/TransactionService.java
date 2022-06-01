package com.mendel.transactions.transaction.service;

import com.mendel.transactions.transaction.domain.Transaction;
import com.mendel.transactions.transaction.domain.Transaction.Builder;
import com.mendel.transactions.transaction.domain.dto.TransactionDTO;
import com.mendel.transactions.transaction.domain.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TransactionService {

  private final Logger logger = LoggerFactory.getLogger(TransactionService.class);

  private final TransactionRepository repository;

  public TransactionService(final TransactionRepository repository) {
    this.repository = repository;
  }

  /**
   * Create new transaction in ours records
   *
   * @param transactionDTO DTO transaction
   * @return a Transaction entity
   */
  public Transaction create(final Long transactionId, final TransactionDTO transactionDTO) {
    Objects.requireNonNull(transactionId);
    logger.info("Gonna to save new transaction with id {} and type {}", transactionId, transactionDTO.getType());
    return repository.save(new Builder().transactionId(transactionId)
      .amount(transactionDTO.getAmount())
      .type(transactionDTO.getType())
      .parentId(transactionDTO.getParentId() != null ? Optional.of(transactionDTO.getParentId()) : Optional.empty())
      .build());
  }

  /**
   * Find transactions by type
   *
   * @param type Transaction type
   * @return List of transactions id
   */
  public List<Long> findByType(final String type) {
    Objects.requireNonNull(type);
    logger.info("Gonna to find all transactions by type {}", type);
    return repository.findByType(type);
  }

  /**
   * Get the sum of the amounts of the related transactions
   *
   * @param transactionId id of the transaction to summarize
   * @return sum of the amounts
   */
  public Double getTransactionTotalAmount(final long transactionId) {
    logger.info("Gonna to sum amount all transactions with transaction id {}", transactionId);
    Transaction transaction = repository.findByTransactionId(transactionId);
    return recursiveSumTransactionAmounts(transaction);
  }

  /**
   * Recursive sum of the amounts of the related transactions
   *
   * @param transaction a transaction
   * @return Transactions amounts sum
   */
  public Double recursiveSumTransactionAmounts(final Transaction transaction) {
    Optional<Transaction> transactionChild = repository.findChildByTransactionId(transaction.getTransactionId());
    return transactionChild.map(value -> Double.sum(transaction.getAmount(), recursiveSumTransactionAmounts(value)))
      .orElseGet(transaction::getAmount);
  }
}
