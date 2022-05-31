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
   * @param transactionDTO DTO transaction object
   * @return Transaction entity
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

  public List<Long> findByType(final String type) {
    Objects.requireNonNull(type);
    logger.info("Gonna to find all transactions by type {}", type);
    return repository.findByType(type);
  }

  public Double addAmountsForTransaction(final long transactionId) {
    logger.info("Gonna to sum amount all transactions with transaction id {}", transactionId);
    Transaction transaction = repository.findByTransactionId(transactionId);
    return transactionSum(transaction);
  }

  public Double transactionSum(final Transaction transaction) {
    Optional<Transaction> transactionChild = repository.findChildByTransactionId(transaction.getTransactionId());
    return transactionChild.map(value -> Double.sum(transaction.getAmount(), transactionSum(value)))
      .orElseGet(transaction::getAmount);
  }
}
