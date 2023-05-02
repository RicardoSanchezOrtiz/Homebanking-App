package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {
	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class);
	}
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//
//	@Bean
//	public CommandLineRunner initData(ClientRepository repository, AccountRepository accountRepo, TransactionRepository transactionRepo, LoanRepository loanRepo, ClientLoanRepository clientLoanRepo, CardRepository cardRepository){
//		return args -> {
//
//			Client admin = new Client("Ricardo", "Sánchez", "ricardo@mindhub.com", passwordEncoder.encode("123"));
//			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("123"));
//			Client client2 = new Client("Benito", "Cameras", "benito@mindhub.com", passwordEncoder.encode("123"));
//			Account account1 = new Account( 5000, "VIN-87613581", LocalDateTime.now().minusMonths(5), AccountType.SAVINGS, true);
//			Account account2 = new Account(7500, "VIN-84113485", LocalDateTime.now().minusDays(15), AccountType.CHECKING, true);
//			Account account3 = new Account(10000, "VIN-32517845", LocalDateTime.now().minusMonths(2), AccountType.SAVINGS, true);
//			client1.addAccount(account1);
//			client1.addAccount(account2);
//			client2.addAccount(account3);
//			Transaction transaction1_1 = new Transaction(LocalDateTime.now().minusDays(15), TransactionType.CREDIT, 350000, "loan");
//			Transaction transaction1_2 = new Transaction(LocalDateTime.now().minusDays(10), TransactionType.DEBIT, -156156.21, "mortgage");
//			Transaction transaction1_3 = new Transaction(LocalDateTime.now(), TransactionType.DEBIT, -20040.50, "car payment");
//			Transaction transaction2_1 = new Transaction(LocalDateTime.now().minusMonths(3), TransactionType.CREDIT, 70000, "salary");
//			Transaction transaction2_2 = new Transaction(LocalDateTime.now().minusMonths(1), TransactionType.DEBIT, -2000, "dinner");
//			Transaction transaction2_3 = new Transaction(LocalDateTime.now(), TransactionType.CREDIT, 350000, "lottery");
//			Transaction transaction3_1 = new Transaction(LocalDateTime.now().minusMonths(4), TransactionType.DEBIT, -700, "gift for wife");
//			Transaction transaction3_2 = new Transaction(LocalDateTime.now().minusMonths(3), TransactionType.DEBIT, -1900, "gift for mother");
//			Transaction transaction3_3 = new Transaction(LocalDateTime.now().minusDays(5), TransactionType.DEBIT, -2000, "gift for myself");
//
//			Loan loan1 = new Loan("Mortgage", 500000, List.of(12, 24, 36, 48, 60), 0.042);
//			Loan loan2 = new Loan("Personal", 100000, List.of(6, 12, 24), 0.1);
//			Loan loan3 = new Loan("Car", 300000, List.of(6, 12, 24, 36), 0.15);
//
//			ClientLoan clientLoan1 = new ClientLoan(300000, 60, client1, loan1);
//			ClientLoan clientLoan2 = new ClientLoan(50000, 12, client1, loan2);
//			ClientLoan clientLoan3 = new ClientLoan(100000, 24, client2, loan1);
//			ClientLoan clientLoan4 = new ClientLoan(200000, 36, client2, loan3);
//
//			client1.addClientLoan(clientLoan1);
//			client1.addClientLoan(clientLoan2);
//			client2.addClientLoan(clientLoan3);
//			client2.addClientLoan(clientLoan4);
//
//			loan1.addClientLoan(clientLoan1);
//			loan1.addClientLoan(clientLoan3);
//			loan2.addClientLoan(clientLoan2);
//			loan3.addClientLoan(clientLoan4);
//
//			Card card1 = new Card(CardType.DEBIT, CardColor.GOLD, LocalDate.now(), LocalDate.now().plusYears(5), "5390-8314-4013-8600", "383", true );
//			Card card2 = new Card(CardType.CREDIT, CardColor.TITANIUM, LocalDate.now(), LocalDate.now().plusYears(5), "4580-1278-9312-8074", "861", true );
//			Card card3 = new Card(CardType.CREDIT, CardColor.SILVER, LocalDate.now().minusMonths(5), LocalDate.now().minusDays(5),  "5914-5374-6982-6193", "984", true );
//			client1.addClientLoan(clientLoan1);
//			client1.addClientLoan(clientLoan2);
//			client2.addClientLoan(clientLoan3);
//			client2.addClientLoan(clientLoan4);
//			client1.addCard(card1);
//			client1.addCard(card2);
//			client2.addCard(card3);
//			account1.addTransaction(transaction1_1);
//			account1.addTransaction(transaction1_2);
//			account1.addTransaction(transaction1_3);
//			account2.addTransaction(transaction2_1);
//			account2.addTransaction(transaction2_2);
//			account2.addTransaction(transaction2_3);
//			account3.addTransaction(transaction3_1);
//			account3.addTransaction(transaction3_2);
//			account3.addTransaction(transaction3_3);
//			client1.addClientLoan(clientLoan1);
//			client1.addClientLoan(clientLoan2);
//			client2.addClientLoan(clientLoan3);
//			client2.addClientLoan(clientLoan4);
//			repository.save(client1);
//			repository.save(client2);
//			repository.save(admin);
//			accountRepo.save(account1);
//			accountRepo.save(account2);
//			accountRepo.save(account3);
//			transactionRepo.save(transaction1_1);
//			transactionRepo.save(transaction1_2);
//			transactionRepo.save(transaction1_3);
//			transactionRepo.save(transaction2_1);
//			transactionRepo.save(transaction2_2);
//			transactionRepo.save(transaction2_3);
//			transactionRepo.save(transaction3_1);
//			transactionRepo.save(transaction3_2);
//			transactionRepo.save(transaction3_3);
//			loanRepo.save(loan1);
//			loanRepo.save(loan2);
//			loanRepo.save(loan3);
//			clientLoanRepo.save(clientLoan1);
//			clientLoanRepo.save(clientLoan2);
//			clientLoanRepo.save(clientLoan3);
//			clientLoanRepo.save(clientLoan4);
//			cardRepository.save(card1);
//			cardRepository.save(card2);
//			cardRepository.save(card3);
//		};}

	}