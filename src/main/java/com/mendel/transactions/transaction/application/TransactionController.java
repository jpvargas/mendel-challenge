package com.mendel.transactions.transaction.application;

import com.mendel.transactions.transaction.domain.Transaction;
import com.mendel.transactions.transaction.domain.dto.TransactionDTO;
import com.mendel.transactions.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A controller exposing endpoints related to transactions
 *
 * @author jpvargas
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/transactions")
public class TransactionController {

  private final TransactionService transactionService;

  public TransactionController(final TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @Operation(summary = "Create a new transaction.")
  @PutMapping("/{transaction_id}")
  public ResponseEntity<TransactionDTO> create(
    @PathVariable(value = "transaction_id") final Long transactionId,
    @Valid @RequestBody(required = false) final TransactionDTO transactionDTO
  ) {
    final Transaction transaction = transactionService.create(transactionId, transactionDTO);
    return ResponseEntity.ok().body(transactionService.mapToTransactionDTO(transaction));
  }

  @Operation(summary = "Gets list of transactions id by type")
  @GetMapping("/types/{type}")
  public ResponseEntity<List<Long>> getTransactionIdsByType(
    @PathVariable(value = "type") final String type
  ) {
    return ResponseEntity.ok().body(transactionService.findByType(type));
  }

  @Operation(summary = "Get the sum of the amounts of the related transactions")
  @GetMapping("/sum/{transaction_id}")
  public ResponseEntity<Map<String, Double>> getTotalAmount(@PathVariable(value = "transaction_id") final Long transactionId) {
    final Double sum = transactionService.getTransactionTotalAmount(transactionId);
    Map<String, Double> response = new HashMap<>();
    response.put("sum", sum);
    return ResponseEntity.ok().body(response);
  }
}
