package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private LocalDateTime transactionTime;
    private TransactionType type;
    private double amount;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    public Transaction(){};
    public Transaction(LocalDateTime transactionDateTime, TransactionType transactionType, double transactionAmount, String transDescription){
        transactionTime = transactionDateTime;
        type = transactionType;
        amount = transactionAmount;
        description = transDescription;
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }
    public void setTransactionTime(LocalDateTime transactionTime) {this.transactionTime = transactionTime;}

    public void setType(TransactionType type) {
        this.type = type;
    }
    public TransactionType getType() {
        return type;
    }

    public void setDescription(String description) {this.description = description;}
    public String getDescription() {return description;}


    public void setAccount(Account account) {this.account = account;}
    public Account getAccount() {return account;}

}
