package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanCreationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanServices loanServices;
    @Autowired
    private AccountServices accountServices;
    @Autowired
    private ClientServices clientServices;
    @Autowired
    private ClientLoanServices clientLoanServices;
    @Autowired
    private TransactionServices transactionServices;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans(){
        return loanServices.findAll().stream().map(loan -> new LoanDTO(loan)).collect(toList());
    }
    @PostMapping(path = "/loans/newloan")
    public  ResponseEntity<Object> createLoan(Authentication authentication, @RequestBody LoanCreationDTO loan){
        Client client = clientServices.findByEmail(authentication.getName());
        if (loan.getPayments().size() <= 0){
            return new ResponseEntity<>("You haven't entered a valid number of payments", HttpStatus.FORBIDDEN);
        }
        if (loan.getMaxAmount() < 0){
            return new ResponseEntity<>("You entered a invalid loan amount", HttpStatus.FORBIDDEN);
        }
        if (loan.getName().isEmpty()){
            return new ResponseEntity<>("You need to specify a type for the loan", HttpStatus.FORBIDDEN);
        }
        if (loan.getInterest() == 0){
            return new ResponseEntity<>("The interest rate is lower than 100%", HttpStatus.FORBIDDEN);
        }
        Loan newLoan = new Loan(loan.getName(), loan.getMaxAmount(), loan.getPayments(), loan.getInterest());
        loanServices.save(newLoan);
        return new ResponseEntity<>("You have successfully created a new Loan", HttpStatus.CREATED);
    }
    @PostMapping(path = "/loans")
    public  ResponseEntity<Object> applyLoan(Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO){
        Client client = clientServices.findByEmail(authentication.getName());
        Account account = accountServices.findByNumber(loanApplicationDTO.getAccountDestiny());
        if (loanApplicationDTO.getId() == 0 || loanApplicationDTO.getId() == null){
            return new ResponseEntity<>("You haven't entered a valid id", HttpStatus.FORBIDDEN);
        }
        if(loanApplicationDTO.getAmount() <= 0 || loanApplicationDTO.getAmount() == null){
            return new ResponseEntity<>("You haven't entered a valid amount", HttpStatus.FORBIDDEN);
        }
        if(client.getLoan().contains(loanServices.getReferenceById(loanApplicationDTO.getId()))){
            return new ResponseEntity<>("You have already taken this loan", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAccountDestiny() == null){
            return new ResponseEntity<>("You haven't entered a valid account number", HttpStatus.FORBIDDEN);
        }
        if (loanServices.getReferenceById(loanApplicationDTO.getId()).getMaxAmount() <= loanApplicationDTO.getAmount()){
            return new ResponseEntity<>("You've entered an amount that exceeds that of the total amount of the loan", HttpStatus.FORBIDDEN);
        }
        if (loanServices.getReferenceById(loanApplicationDTO.getId()).getMaxAmount() <= loanApplicationDTO.getAmount()){
            return new ResponseEntity<>("You've entered an amount that exceeds that of the total amount of the loan", HttpStatus.FORBIDDEN);
        }
        if (accountServices.findByNumber(loanApplicationDTO.getAccountDestiny()) == null){
            return new ResponseEntity<>("The account number you provided does not belong to an existing account", HttpStatus.FORBIDDEN);
        }
        if (accountServices.findByNumber(loanApplicationDTO.getAccountDestiny()).getActive() == false){
            return new ResponseEntity<>("The account number you provided is not active", HttpStatus.FORBIDDEN);
        }
        if (!client.getAccounts().contains(accountServices.findByNumber(loanApplicationDTO.getAccountDestiny()))){
            return new ResponseEntity<>("This account does not belong to you", HttpStatus.FORBIDDEN);
        }
        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount()*1.2, loanApplicationDTO.getPayments(), client, loanServices.getReferenceById(loanApplicationDTO.getId()));
        Transaction transaction = new Transaction(LocalDateTime.now(), TransactionType.CREDIT, loanApplicationDTO.getAmount(), loanServices.getReferenceById(loanApplicationDTO.getId()).getName() + " Loan approved");
        account.setBalance(account.getBalance()+loanApplicationDTO.getAmount());
        account.addTransaction(transaction);
        clientLoanServices.save(clientLoan);
        transactionServices.save(transaction);
        accountServices.save(account);
        return new ResponseEntity<>(loanServices.getReferenceById(loanApplicationDTO.getId()).getName() + " loan has been approved", HttpStatus.ACCEPTED);
    }
}
