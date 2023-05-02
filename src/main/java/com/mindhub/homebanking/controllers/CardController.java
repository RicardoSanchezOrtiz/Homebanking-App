package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardServices;
import com.mindhub.homebanking.services.ClientServices;
import com.mindhub.homebanking.services.implementations.CardServicesImplementations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.models.Utils.ccv;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardServices cardServices;

    @Autowired
    private ClientServices clientServices;

    public Utils utils = new Utils();

    @PatchMapping(path = "/clients/current/cards")
    public ResponseEntity<Object> hideCard (@RequestParam String cardNumber){
        Card card = cardServices.findByNumber(cardNumber);
        card.setActive(false);
        cardServices.save(card);
        return new ResponseEntity<>("Card successfully deleted", HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/clients/current/cards")
    public ResponseEntity<Object> createCard (Authentication authentication, @RequestParam CardType type, @RequestParam CardColor color){
        Client client = clientServices.findByEmail(authentication.getName());
        if (client.getCards().stream().map(card -> card.getType().equals("DEBIT") && card.getActive()== true).collect(Collectors.toSet()).size() == 3 ){
            return new ResponseEntity<>("You have the maximum amount of debit cards per client", HttpStatus.FORBIDDEN);}

        if (client.getCards().stream().map(card -> card.getType().equals("CREDIT") && card.getActive()== true).collect(Collectors.toSet()).size() == 3  ){
            return new ResponseEntity<>("You have the maximum amount of credit cards per client", HttpStatus.FORBIDDEN);}
        if (client.getCards().stream().anyMatch(card -> type == card.getType() && color == card.getColor() && card.getActive() == true)){
            return new ResponseEntity<>("This card has already been created", HttpStatus.FORBIDDEN);
        }
        Card card = new Card(type, color, LocalDate.now(), LocalDate.now().plusYears(5), utils.cardNumber(), ccv(), true);
        client.addCard(card);
        cardServices.save(card);
        return new ResponseEntity<>(card, HttpStatus.CREATED);
    }

}


