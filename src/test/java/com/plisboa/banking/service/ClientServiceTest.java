package com.plisboa.banking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.plisboa.banking.domain.entity.Client;
import com.plisboa.banking.domain.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    // Crie uma nova instância de Page<Client> com os elementos
    Pageable pageable = PageRequest.of(0, 10);
    Page<Client> page = new PageImpl<>(clients, pageable, clients.size());

    PageRequest pageRequest = PageRequest.of(0, 10);
    when(clientRepository.findAll(pageRequest)).thenReturn(page);

    Page<Client> result = clientService.getAllClients(pageRequest);

    assertEquals(2, result.getTotalElements());
  }

  @Test
  void testGetClientById() {
    String clientId = "test123";
    Client client = new Client();
    client.setAccountId(clientId);

    when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

    ResponseEntity<Client> result = clientService.getClientById(clientId);

    assertEquals(200, result.getStatusCode().value());
    assertNotNull(result.getBody());
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
    assertNotNull(result.getBody());
    assertEquals(clientId, result.getBody().getAccountId());
  }

  @Test
  void testUpdateClient_NotFound() {
    String clientId = "nonExistentId";
    Client client = new Client();

    when(clientRepository.existsById(clientId)).thenReturn(false);

    assertThrows(EntityNotFoundException.class, () -> clientService.updateClient(clientId, client));
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

    // Use assertThrows para verificar se a exceção é lançada
    assertThrows(EntityNotFoundException.class, () -> clientService.deleteClient(clientId));

  }
}

