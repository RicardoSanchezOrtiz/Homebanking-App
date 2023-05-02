package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountServices;
import com.mindhub.homebanking.services.ClientServices;
import com.mindhub.homebanking.services.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping ("/api")
public class TransactionController {

    @Autowired
    private ClientServices clientServices;
    @Autowired
    private AccountServices accountServices;
    @Autowired
    private TransactionServices transactionServices;

    @Transactional
    @PostMapping(path = "clients/current/accounts/transactions")
    public ResponseEntity<Object> createTransaction(Authentication authentication,
            @RequestParam Double transactionAmount, @RequestParam String description,
            @RequestParam String transferingAccNum, @RequestParam String recieverAccNum) {
        Account transferingAcc = accountServices.findByNumber(transferingAccNum);
        Account receiverAcc = accountServices.findByNumber(recieverAccNum);
        Client client = clientServices.findByEmail(authentication.getName());
        if (transactionAmount <= 0 ) {
            return new ResponseEntity<>("You haven't entered an amount for the transaction", HttpStatus.BAD_REQUEST);
        }
        if (description.isEmpty()) {
            return new ResponseEntity<>("You haven't provided a description for the transaction", HttpStatus.BAD_REQUEST);
        }
        if (transferingAccNum.isEmpty()){
            return new ResponseEntity<>("You haven't provided a valid account number to withdraw the funds", HttpStatus.BAD_REQUEST);
        }
        if (recieverAccNum.isEmpty()){
            return new ResponseEntity<>("You haven't provided a valid account number to send the funds to", HttpStatus.BAD_REQUEST);
        }
        if (accountServices.findByNumber(transferingAccNum) == null){
            return new ResponseEntity<>("The account you provided to withdraw the funds doesn't exist", HttpStatus.BAD_REQUEST);
        }
        if (accountServices.findByNumber(recieverAccNum) == null){
            return new ResponseEntity<>("The account that is to recieve the funds doesn't exist", HttpStatus.BAD_REQUEST);
        }
        if (accountServices.findByNumber(transferingAccNum).getAccountHolder() != client){
            return new ResponseEntity<>("The transfering account does not belong to the client", HttpStatus.FORBIDDEN);
        }
        if (accountServices.findByNumber(recieverAccNum) == null){
            return new ResponseEntity<>("The account you provided to send the funds to does not exist", HttpStatus.BAD_REQUEST);
        }
        if (accountServices.findByNumber(transferingAccNum).getBalance() < transactionAmount){
            return new ResponseEntity<>("Insufficient funds", HttpStatus.BAD_REQUEST);
        }
        Transaction transactionSend = new Transaction(LocalDateTime.now(), TransactionType.DEBIT, -transactionAmount, description);
        transferingAcc.addTransaction(transactionSend);
        Transaction transactionReceive = new Transaction(LocalDateTime.now(), TransactionType.CREDIT, transactionAmount, description);
        receiverAcc.addTransaction(transactionReceive);
        transferingAcc.setBalance(transferingAcc.getBalance()-transactionAmount);
        receiverAcc.setBalance(receiverAcc.getBalance()+transactionAmount);
        transactionServices.save(transactionReceive);
        transactionServices.save(transactionSend);
        accountServices.save(transferingAcc);
        accountServices.save(receiverAcc);
        return  new ResponseEntity<>("Transaction successfully completed", HttpStatus.ACCEPTED);
    }
}
