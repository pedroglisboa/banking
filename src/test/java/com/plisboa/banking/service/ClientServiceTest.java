package com.plisboa.banking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.plisboa.banking.entity.Client;
import com.plisboa.banking.repository.ClientRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class ClientServiceTest {

  @InjectMocks
  private ClientService clientService;

  @Mock
  private ClientRepository clientRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAllClients() {
    List<Client> clients = new ArrayList<>();
    clients.add(new Client());
    clients.add(new Client());
    when(clientRepository.findAll()).thenReturn(clients);

    List<Client> result = clientService.getAllClients();

    assertEquals(2, result.size());
  }

  @Test
  void testGetClientById() {
    String clientId = "test123";
    Client client = new Client();
    client.setAccountId(clientId);

    when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

    ResponseEntity<Client> result = clientService.getClientById(clientId);

    assertEquals(200, result.getStatusCode().value());
    assertEquals(clientId, result.getBody().getAccountId());
  }

  @Test
  void testGetClientById_NotFound() {
    String clientId = "nonExistentId";

    when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

    ResponseEntity<Client> result = clientService.getClientById(clientId);

    assertEquals(404, result.getStatusCode().value());
  }

  @Test
  void testCreateClient() {
    Client client = new Client();
    when(clientRepository.save(any(Client.class))).thenReturn(client);

    Client createdClient = clientService.createClient(client);

    assertEquals(client, createdClient);
  }

  @Test
  void testUpdateClient() {
    String clientId = "test123";
    Client client = new Client();
    client.setAccountId(clientId);

    when(clientRepository.existsById(clientId)).thenReturn(true);
    when(clientRepository.save(any(Client.class))).thenReturn(client);

    ResponseEntity<Client> result = clientService.updateClient(clientId, client);

    assertEquals(200, result.getStatusCode().value());
    assertEquals(clientId, result.getBody().getAccountId());
  }

  @Test
  void testUpdateClient_NotFound() {
    String clientId = "nonExistentId";
    Client client = new Client();

    when(clientRepository.existsById(clientId)).thenReturn(false);

    ResponseEntity<Client> result = clientService.updateClient(clientId, client);

    assertEquals(404, result.getStatusCode().value());
  }

  @Test
  void testDeleteClient() {
    String clientId = "test123";

    when(clientRepository.existsById(clientId)).thenReturn(true);

    ResponseEntity<Void> result = clientService.deleteClient(clientId);

    assertEquals(204, result.getStatusCode().value());
  }

  @Test
  void testDeleteClient_NotFound() {
    String clientId = "nonExistentId";

    when(clientRepository.existsById(clientId)).thenReturn(false);

    ResponseEntity<Void> result = clientService.deleteClient(clientId);

    assertEquals(404, result.getStatusCode().value());
  }
}

