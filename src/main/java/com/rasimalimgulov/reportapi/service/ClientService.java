package com.rasimalimgulov.reportapi.service;

import com.rasimalimgulov.reportapi.entity.Client;
import com.rasimalimgulov.reportapi.entity.User;
import com.rasimalimgulov.reportapi.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepository repository;
    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }
    public List<Client> getAllClientsByUser(User user) {
      return repository.findAllByUser(user);
    }
    public Client addClient(Client client) {
        return repository.save(client);
    }
}
