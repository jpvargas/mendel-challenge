package com.mendel.transactions.transaction.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A controller exposing endpoints related to transactions entities
 *
 * @author jpvargas
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/transactions")
public class TransactionController {

  private final Logger logger = LoggerFactory.getLogger(TransactionController.class);

}
