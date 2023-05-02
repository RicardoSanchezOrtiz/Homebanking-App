package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name ="native", strategy = "native")
    private long id;
    private CardType type;
    private CardColor color;
    private String number;
    private String ccv;
    private LocalDate fromDate;
    private LocalDate thruDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cardHolder_id")
    private Client cardHolder;
    private Boolean active;

    public Card(){}

    public Card(CardType cardType, CardColor cardColor, LocalDate startDate, LocalDate endDate, String cardNumber, String cvvCode, Boolean active){
        type = cardType;
        color = cardColor;
        fromDate = startDate;
        thruDate = endDate;
        number = cardNumber;
        ccv = cvvCode;
        this.active = active;

    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public void setType(CardType type) {
        this.type = type;
    }
    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }
    public void setColor(CardColor color) {
        this.color = color;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public String getNumber() {
        return number;
    }

    public String getCcv() {
        return ccv;
    }
    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
    public LocalDate getThruDate() {
        return thruDate;
    }
    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }
    @JsonIgnore
    public Client getCardHolder() {
        return cardHolder;
    }
    public void setCardHolder(Client cardHolder) {
        this.cardHolder = cardHolder;
    }
}
