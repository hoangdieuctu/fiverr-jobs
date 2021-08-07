package com.raunheim.los.service;

import com.raunheim.los.model.Client;
import com.raunheim.los.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client getOrCreate(String name) {
        var client = clientRepository.findByName(name);
        if (client != null) {
            return client;
        }

        log.info("Create a new client: {}", name);
        var newClient = new Client();
        newClient.setName(name);
        clientRepository.save(newClient);

        return newClient;
    }

}
