package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.PosnetDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountServices;
import com.mindhub.homebanking.services.CardServices;
import com.mindhub.homebanking.services.ClientServices;
import com.mindhub.homebanking.services.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountServices accountServices;
    @Autowired
    private ClientServices clientServices;
    @Autowired
    private TransactionServices transactionServices;
    @Autowired
    private CardServices cardServices;
    private Utils utils = new Utils();

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountServices.findAll().stream().map(account -> new AccountDTO(account)).collect(toList());
    }
    @GetMapping("/accounts/{id}")
    public AccountDTO accountDTO(@PathVariable Long id){
        return new AccountDTO(accountServices.findById(id));
    }
    @PostMapping(path = "/clients/current/accounts")
    public ResponseEntity<Object> createAccount(@RequestParam String email, @RequestParam AccountType type) {
        Client client = clientServices.findByEmail(email);
        if (client.getAccounts().stream().filter(account -> account.getActive() == true).count() >= 3) {
            return new ResponseEntity<>("You have the maximum amount of accounts per client", HttpStatus.FORBIDDEN);}
        Account newAccount = new Account(0, "VIN-" + utils.accNumber(), LocalDateTime.now(), type, true);
        client.addAccount(newAccount);
        accountServices.save(newAccount);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PatchMapping(path = "/clients/current/accounts")
    public ResponseEntity<Object> deleteAccount(@RequestParam String number, @RequestParam String accNumber){
        Account account = accountServices.findByNumber(number);
        Account receivingAcc = accountServices.findByNumber(accNumber);
        if (account.getBalance() > 0) {
            Transaction transaction = new Transaction(LocalDateTime.now(), TransactionType.DEBIT, -account.getBalance(), "Account deleted, balance transfered to Account#" + receivingAcc.getNumber());
            Transaction deposit = new Transaction(LocalDateTime.now(), TransactionType.CREDIT, account.getBalance(), "Transaction made due to account deletion. From Account #" + account.getNumber());
            account.addTransaction(transaction);
            receivingAcc.addTransaction(deposit);
            receivingAcc.setBalance(receivingAcc.getBalance() + account.getBalance());
            account.setBalance(0.0);
            accountServices.save(receivingAcc);
            transactionServices.save(transaction);
            transactionServices.save(deposit);
        }
        account.setActive(false);
        accountServices.save(account);
        return new ResponseEntity<>("Account successfully deleted", HttpStatus.ACCEPTED);
    }
    @CrossOrigin(origins = {"*"})
    @Transactional
    @PostMapping("/pay")//nuevo servlet
    public ResponseEntity <Object> payCards(@RequestParam String number, @RequestParam String cvv, @RequestParam Double amount, @RequestParam String description){
        if (number.isEmpty()){
            return new ResponseEntity<>("The card number is missing",HttpStatus.FORBIDDEN);}
        if (amount == null && amount>0){
            return new ResponseEntity<>("The amount to be paid is missing",HttpStatus.FORBIDDEN);}
        if (cvv.isEmpty())
            return new ResponseEntity<>("The card cvv is missing",HttpStatus.FORBIDDEN);
        if (description.isEmpty())
            return new ResponseEntity<>("The description is missing",HttpStatus.FORBIDDEN);
        if (cardServices.findByNumber(number) == null)
            return new ResponseEntity<>("Card not found",HttpStatus.FORBIDDEN);
        Card card = cardServices.findByNumber(number);
        Account account = card.getCardHolder().getAccounts().stream().iterator().next();
        if (!card.getActive())
            return new ResponseEntity<>("The card is not valid",HttpStatus.FORBIDDEN);
        if (card.getType() != CardType.DEBIT)
            return new ResponseEntity<>("The card is not debit",HttpStatus.FORBIDDEN);
        if (card.getThruDate().isBefore(LocalDate.now()))
            return new ResponseEntity<>("The card has expired",HttpStatus.FORBIDDEN);
        if (account == null)
            return new ResponseEntity<>("There is no associated account",HttpStatus.FORBIDDEN);
        if (!account.getActive())
            return new ResponseEntity<>("The associated account is inactive",HttpStatus.FORBIDDEN);
        if (account.getBalance()<amount)
            return new ResponseEntity<>("Your account balance does not cover the payment",HttpStatus.FORBIDDEN);
        Transaction transaction = new Transaction(LocalDateTime.now(), TransactionType.DEBIT, -amount, description);
        account.setBalance(account.getBalance() - amount);
        account.addTransaction(transaction);
        transactionServices.save(transaction);
        accountServices.save(account);
        return new ResponseEntity<>("Payment made",HttpStatus.ACCEPTED);
    }
}


