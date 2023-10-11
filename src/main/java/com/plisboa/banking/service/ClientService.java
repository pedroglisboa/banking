package com.plisboa.banking.service;

import com.plisboa.banking.entity.Client;
import com.plisboa.banking.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ClientService {

  private final ClientRepository clientRepository;

  public ClientService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  public Page<Client> getAllClients(PageRequest pageRequest) {
    return clientRepository.findAll(pageRequest);
  }

  public ResponseEntity<Client> getClientById(@PathVariable String id) {
    Optional<Client> client = clientRepository.findById(id);
    return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  public Client createClient(@RequestBody Client client) {
    return clientRepository.save(client);
  }

  public ResponseEntity<Client> updateClient(@PathVariable String id, @RequestBody Client client) {
    if (!clientRepository.existsById(id)) {
      throw new EntityNotFoundException("Cliente não encontrado");
    }
    client.setAccountId(id);
    client = clientRepository.save(client);
    return ResponseEntity.ok(client);
  }

  public ResponseEntity<Void> deleteClient(@PathVariable String id) {
    if (!clientRepository.existsById(id)) {
      throw new EntityNotFoundException("Cliente não encontrado");
    }
    clientRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

}
