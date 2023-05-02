package com.mindhub.homebanking.services.implementations;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClientServicesImplementation implements ClientServices {

    @Autowired
    ClientRepository clientRepo;

    @Override
    public List<Client> findAll() {
        return clientRepo.findAll();
    }

    @Override
    public Client findById(Long id) {
        return clientRepo.findById(id).orElse(null);
    }

    @Override
    public Client findByEmail(String email) {
        return clientRepo.findByEmail(email);
    }

    @Override
    public void save(Client client) {
        clientRepo.save(client);
    }
}
