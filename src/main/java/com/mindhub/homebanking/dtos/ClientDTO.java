package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import net.minidev.json.annotate.JsonIgnore;

import java.util.Set;
import static java.util.stream.Collectors.toSet;


public class ClientDTO{

        private long id;
        private String email;
        private String firstName;
        private String lastName;
        private Set<AccountDTO> accounts;
        private Set<ClientLoanDTO> loans;
        private Set<CardDTO> cards;
        public ClientDTO(Client client){
                this.id = client.getId();
                this.firstName = client.getFirstName();
                this.lastName = client.getLastName();
                this.email = client.getEmail();
                this.accounts = client.getAccounts().stream().map(AccountDTO::new).collect(toSet());
                this.loans = client.getClientloans().stream().map(ClientLoanDTO::new).collect(toSet());
                this.cards = client.getCards().stream().map(CardDTO::new).collect(toSet());
        }

        public void setId(long id) {
                this.id = id;
        }
        public long getId() {
                return id;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getFirstName() {
                return firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public String getEmail() {
                return email;
        }
        @JsonIgnore
        public Set<AccountDTO> getAccounts() {
                return accounts;
        }
        public void setAccounts(Set<AccountDTO> accounts) {
                this.accounts = accounts;
        }

        public void setClientLoans(Set<ClientLoanDTO> loans) {
                this.loans = loans;
        }
        public Set<ClientLoanDTO> getClientLoans() {
                return loans;
        }

        public void setCards(Set<CardDTO> cards) {
                this.cards = cards;
        }
        public Set<CardDTO> getCards() {
                return cards;
        }
}
