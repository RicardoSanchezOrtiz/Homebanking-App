package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;

import java.time.LocalDate;

public class CardDTO {
    private long id;
    private CardType type;
    private CardColor color;
    private String number;
    private String ccv;
    private LocalDate thruDate;
    private String cardholder;
    private Boolean active;

    public CardDTO(Card card){
        this.type = card.getType();
        this.color = card.getColor();
        this.number = card.getNumber();
        this.ccv = card.getCcv();
        this.thruDate = card.getThruDate();
        this.cardholder = card.getCardHolder().getFirstName() + " " + card.getCardHolder().getLastName();
        this.active = card.getActive();

    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public String getNumber() {
        return number;
    }
    public Boolean getActive() {
        return active;
    }

    public String getCcv() {
        return ccv;
    }
    public LocalDate getThruDate() {
        return thruDate;
    }
    public String getCardholder() {
        return cardholder;
    }}

