package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
public class Client{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;

    @OneToMany(mappedBy = "accountHolder", fetch = FetchType.EAGER)
    Set<Account> accounts = new HashSet<>();
    @OneToMany(mappedBy = "borrower", fetch = FetchType.EAGER)
    Set<ClientLoan> clientloans = new HashSet<>();
    @OneToMany(mappedBy ="cardHolder" , fetch = FetchType.EAGER)
    Set<Card> cards = new HashSet<>();

    public Client() {}

    public Client(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
    public long getId(){
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String toString() {
        return firstName + " " + lastName;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public Set<Account> getAccounts(){
        return accounts;
    }

    public void setClientloans(Set<ClientLoan> loans) {
        this.clientloans = loans;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Set<ClientLoan> getClientloans() {
        return clientloans;
    }
    public List<Loan> getLoan(){
        return clientloans.stream().map(clientLoan -> clientLoan.getLoan()).collect(toList());
    }
    public Set<Card> getCards() {
        return cards;
    }
    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public void addAccount(Account account){
        account.setAccountHolder(this);
        accounts.add(account);
    }
    public void addClientLoan(ClientLoan loan){
        loan.setClient(this);
        clientloans.add(loan);
    }
    public void addCard(Card card){
        card.setCardHolder(this);
        cards.add(card);
    }
}
