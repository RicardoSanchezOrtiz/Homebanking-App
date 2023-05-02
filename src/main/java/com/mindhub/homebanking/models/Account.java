package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private AccountType accountType;
    private Boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accountHolder_id")
    private Client accountHolder;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    Set<Transaction> transactions = new HashSet<>();
    public Account(){}

    public Account(double accBalance, String number, LocalDateTime localDateTime, AccountType accountType, Boolean active) {
        balance = accBalance;
        this.number= number;
        this.creationDate = localDateTime;
        this.accountType = accountType;
        this.active = active;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getNumber(){
        return number;}
    public void setNumber(String number){
        this.number = number;}

    public LocalDateTime getCreationDate(){
        return creationDate;}
    public void setCreationDate(LocalDateTime creationDate){
        this.creationDate = creationDate;}

    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }

    public double getBalance() {
        return balance;}
    public void setBalance(double balance) {
        this.balance = balance;}

    public Client getAccountHolder(){
        return accountHolder;}
    public void setAccountHolder(Client accountHolder) {
        this.accountHolder = accountHolder;
    }

    public Set<Transaction> getTransactions(){
        return transactions;
    }
    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public AccountType getAccountType() {
        return accountType;
    }
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
    public void addTransaction (Transaction transaction){
        transaction.setAccount(this);
        transactions.add(transaction);
    }
}
