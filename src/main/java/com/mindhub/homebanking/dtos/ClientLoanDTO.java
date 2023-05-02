package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

public class ClientLoanDTO {
    private long id;
    private String name;
    private double amount;
    private int payments;


    public ClientLoanDTO(ClientLoan clientLoan){
        this.id = clientLoan.getId();
        this.name = clientLoan.getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
    }

    public long getId() {
        return id;
    }
    public int getPayments() {
        return payments;
    }
    public double getAmount() {
        return amount;
    }
    public String getName() {
        return name;
    }
}
