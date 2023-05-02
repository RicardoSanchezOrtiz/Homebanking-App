package com.mindhub.homebanking.dtos;


public class LoanApplicationDTO {
    private Long id;
    private Double amount;
    private Integer payments;
    private String accountDestiny;

    public Long getId() {
        return id;
    }
    public Double getAmount() {
        return amount;
    }
    public Integer getPayments() {
        return payments;
    }
    public String getAccountDestiny() {
        return accountDestiny;
    }

}
