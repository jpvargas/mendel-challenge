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

  private final TransactionRepository repository = TransactionRepository.getInstance();

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
    final List<Transaction> transactionChild = repository.findChildrenByTransactionId(transaction.getTransactionId());
    if (transactionChild.isEmpty()) {
      return transaction.getAmount();
    } else {
      return transaction.getAmount() + transactionChild.stream()
        .map(this::recursiveSumTransactionAmounts)
        .reduce(0.0, Double::sum);
    }
  }

  /**
   * Mapper for convert Transaction to TransactionDTO Object
   *
   * @param transaction a Transaction entity
   * @return TransactionDTO
   */
  public TransactionDTO mapToTransactionDTO(final Transaction transaction) {
    final Long parentId = transaction.getParentId().isPresent() ? transaction.getParentId().get() : null;
    return new TransactionDTO(transaction.getAmount(), transaction.getType(), parentId);
  }

  /**
   * Util for testing
   */
  public void resetMemoryStore() {
    repository.cleanRepository();
  }
}
