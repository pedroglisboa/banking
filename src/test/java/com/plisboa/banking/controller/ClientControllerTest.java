package com.plisboa.banking.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.plisboa.banking.entity.Client;
import com.plisboa.banking.service.ClientService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class ClientControllerTest {

  @InjectMocks
  private ClientController clientController;

  @Mock
  private ClientService clientService;

  private Client testClient;

  @BeforeEach
  public void setUp() {
    testClient = new Client();
    testClient.setAccountId("test123");
    testClient.setName("Test Client");
    testClient.setIsPrimeExclusive(true);
    testClient.setBalance(new BigDecimal("1000.00"));
    testClient.setBirthDate(LocalDate.of(1990, 5, 15));
  }

  @Test
  void testGetAllClients() {
    Client client1 = new Client();
    client1.setAccountId("client1");
    client1.setName("Client 1");
    client1.setIsPrimeExclusive(false);
    client1.setBalance(new BigDecimal("500.00"));
    client1.setBirthDate(LocalDate.of(1985, 8, 10));

    Client client2 = new Client();
    client2.setAccountId("client2");
    client2.setName("Client 2");
    client2.setIsPrimeExclusive(true);
    client2.setBalance(new BigDecimal("1500.00"));
    client2.setBirthDate(LocalDate.of(1995, 3, 22));

    List<Client> clients = Arrays.asList(client1, client2);

    // Crie uma nova instância de Page<Client> com os elementos
    Pageable pageable = PageRequest.of(0, 10);
    Page<Client> page = new PageImpl<>(clients, pageable, clients.size());

    PageRequest pageRequest = PageRequest.of(0, 10);
    when(clientService.getAllClients(pageRequest)).thenReturn(page);

    Page<Client> result = clientController.getAllClients(0, 10);

    assertEquals(2, result.getTotalElements());
  }

  @Test
  void testGetClientById() {
    when(clientService.getClientById("test123")).thenReturn(ResponseEntity.ok(testClient));

    ResponseEntity<Client> result = clientController.getClientById("test123");

    assertEquals(200, result.getStatusCode().value());
    assertNotNull(result.getBody());
    assertEquals("test123", result.getBody().getAccountId());
  }

  @Test
  void testCreateClient() {
    when(clientService.createClient(any(Client.class))).thenReturn(testClient);

    Client createdClient = clientController.createClient(testClient);

    assertEquals("test123", createdClient.getAccountId());
    assertEquals("Test Client", createdClient.getName());
    assertEquals(true, createdClient.getIsPrimeExclusive());
    assertEquals(new BigDecimal("1000.00"), createdClient.getBalance());
    assertEquals(LocalDate.of(1990, 5, 15), createdClient.getBirthDate());
  }

  @Test
  void testUpdateClient() {
    when(clientService.updateClient("test123", testClient)).thenReturn(
        ResponseEntity.ok(testClient));

    ResponseEntity<Client> result = clientController.updateClient("test123", testClient);

    assertEquals(200, result.getStatusCode().value());
    assertNotNull(result.getBody());
    assertEquals("Test Client", result.getBody().getName());
  }

  @Test
  void testDeleteClient() {
    when(clientService.deleteClient("test123")).thenReturn(ResponseEntity.noContent().build());

    ResponseEntity<Void> result = clientController.deleteClient("test123");

    assertEquals(204, result.getStatusCode().value());
  }
}
