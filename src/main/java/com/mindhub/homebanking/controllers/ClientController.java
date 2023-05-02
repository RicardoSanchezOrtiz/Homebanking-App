package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Utils;
import com.mindhub.homebanking.services.AccountServices;
import com.mindhub.homebanking.services.ClientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController{
    @Autowired
    ClientServices clientServices;
    @Autowired
    private AccountServices accountServices;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private Utils utils = new Utils();

    @GetMapping("/clients")
    public List<ClientDTO> getClients(){
        return clientServices.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
    }
    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return new ClientDTO(clientServices.findById(id));
    }

    @PostMapping(path = "/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password ) {
        if (firstName.isEmpty()) {
            return new ResponseEntity<>("Missing first name", HttpStatus.FORBIDDEN);
        }
        if (lastName.isEmpty()) {
            return new ResponseEntity<>("Missing Last Name", HttpStatus.FORBIDDEN);
        }
        if (email.isEmpty()) {
            return new ResponseEntity<>("Missing e-mail", HttpStatus.FORBIDDEN);
        }
        if (password.isEmpty()) {
            return new ResponseEntity<>("Missing password", HttpStatus.FORBIDDEN);
        }
        if (clientServices.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }
        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        Account account = new Account(0, "VIN-" + utils.accNumber(), LocalDateTime.now(), AccountType.SAVINGS, true);
        client.addAccount(account);
        clientServices.save(client);
        accountServices.save(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
        }
    @CrossOrigin
    @RequestMapping("/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication) {
        String email = authentication.getName();
        return new ClientDTO(clientServices.findByEmail(email));
    }
}
