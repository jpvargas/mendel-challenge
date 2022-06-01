package com.mendel.transactions.transaction.service;


import com.mendel.transactions.transaction.domain.Transaction;
import com.mendel.transactions.transaction.domain.dto.TransactionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest()
@ActiveProfiles("test")
class TransactionServiceTest {

  @Autowired
  private TransactionService transactionService;

  @BeforeEach
  void setUp() {
    transactionService.resetMemoryStore();
  }

  @Test
  void testCreate() {
    final Long transactionID = 10L;
    TransactionDTO transactionDTO = new TransactionDTO(5000D, "cars", null);

    Transaction transaction = transactionService.create(transactionID, transactionDTO);

    assertThat(transaction).isNotNull();
    assertThat(transaction.getTransactionId()).isEqualTo(10L);
    assertThat(transaction.getAmount()).isEqualTo(5000D);
    assertThat(transaction.getType()).isEqualTo("cars");
  }

  @Test
  void testFindByType() {

    transactionService.create(10L, new TransactionDTO(5000D, "cars", null));
    transactionService.create(11L, new TransactionDTO(10000D, "shopping", 10L));
    transactionService.create(12L, new TransactionDTO(500D, "shopping", 11L));

    final List<Long> transactionsShoppingType = transactionService.findByType("shopping");
    assertThat(transactionsShoppingType).isNotEmpty();
    assertThat(transactionsShoppingType.size()).isEqualTo(2);
    assertThat(transactionsShoppingType.contains(11L)).isTrue();
    assertThat(transactionsShoppingType.contains(12L)).isTrue();

    final List<Long> transactionsIds = transactionService.findByType("cars");
    assertThat(transactionsIds).isNotEmpty();
    assertThat(transactionsIds.size()).isEqualTo(1);
    assertThat(transactionsIds.contains(10L)).isTrue();
  }

  @Test
  void testTransactionsSum() {
    transactionService.create(10L, new TransactionDTO(5000D, "cars", null));
    transactionService.create(11L, new TransactionDTO(10000D, "shopping", 10L));
    transactionService.create(12L, new TransactionDTO(5000D, "shopping", 11L));

    Double totalAmount = transactionService.getTransactionTotalAmount(10L);
    assertThat(totalAmount).isEqualTo(20000D);

    Double totalAmount_11 = transactionService.getTransactionTotalAmount(11L);
    assertThat(totalAmount_11).isEqualTo(15000D);

  }
}