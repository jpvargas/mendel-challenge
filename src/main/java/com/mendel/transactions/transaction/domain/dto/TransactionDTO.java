package com.mendel.transactions.transaction.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Transaction DTO
 *
 * @author jpvargas
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDTO {

  private static final String AMOUNT_FIELD = "amount";
  private static final String TYPE_FIELD = "type";
  private static final String PARENT_ID_FIELD = "parent_id";

  @JsonProperty(AMOUNT_FIELD)
  @NotNull
  private Double amount;

  @JsonProperty(TYPE_FIELD)
  @NotEmpty
  private String type;

  @JsonProperty(PARENT_ID_FIELD)
  private Long parentId;

  public TransactionDTO(final Double amount, final String type, final Long parentId) {
    this.amount = amount;
    this.type = type;
    this.parentId = parentId;
  }

  public Double getAmount() {
    return amount;
  }

  public String getType() {
    return type;
  }

  public Long getParentId() {
    return parentId;
  }
}
