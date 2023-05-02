package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private double amount;
    private int payments;
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "borrower_id")
    private Client borrower;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    public ClientLoan(){}

    public ClientLoan(double loanAmount, int numberPayments, Client clients, Loan loans){
        amount = loanAmount;
        payments = numberPayments;
        name = loans.getName();
        this.loan = loans;
        this.borrower = clients;

    }

    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    public double getAmount() {
        return amount;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }
    public int getPayments() {
        return payments;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public Client getClient() {
        return borrower;
    }
    public void setClient(Client client) {
        this.borrower = client;
    }

    public Loan getLoan() {
        return loan;
    }
    public void setLoan(Loan loan) {
        this.loan = loan;
    }


}
