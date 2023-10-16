package com.plisboa.banking.service;

import com.plisboa.banking.domain.entity.Client;
import com.plisboa.banking.domain.repository.ClientRepository;
import com.plisboa.banking.request.ClientRequest;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

  private final ClientRepository clientRepository;

  public ClientService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  public Page<Client> getAllClients(PageRequest pageRequest) {
    return clientRepository.findAll(pageRequest);
  }

  public ResponseEntity<Client> getClientById(String id) {
    Optional<Client> client = clientRepository.findById(id);
    return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  public Client createClient(ClientRequest clientRequest) {
    Client client = new Client(clientRequest);
    return createClient(client);
  }

  public Client createClient(Client client) {
    return clientRepository.save(client);
  }

  public ResponseEntity<Client> updateClient(String id, ClientRequest clientRequest) {
    Client client = new Client(clientRequest);
    return updateClient(id, client);
  }

  public ResponseEntity<Client> updateClient(String id, Client client) {
    if (!clientRepository.existsById(id)) {
      throw new EntityNotFoundException("Cliente não encontrado");
    }
    client.setAccountId(id);
    client = clientRepository.save(client);
    return ResponseEntity.ok(client);
  }

  public ResponseEntity<Void> deleteClient(String id) {
    if (!clientRepository.existsById(id)) {
      throw new EntityNotFoundException("Cliente não encontrado");
    }
    clientRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

}
