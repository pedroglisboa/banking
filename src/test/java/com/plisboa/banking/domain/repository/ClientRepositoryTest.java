package com.plisboa.banking.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.plisboa.banking.domain.entity.Client;
import com.plisboa.banking.domain.repository.ClientRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ClientRepositoryTest {

  @Autowired
  private ClientRepository clientRepository;

  @Test
  void testSaveClient() {
    // Criar um objeto Client para salvar no banco de dados
    Client client = new Client();
    client.setAccountId("12345");
    client.setName("John Doe");
    client.setIsPrimeExclusive(true);
    client.setBalance(new BigDecimal("1000.00"));
    client.setBirthDate(LocalDate.of(1985, 5, 15));

    // Salvar o cliente no banco de dados usando o repositório
    clientRepository.save(client);

    // Buscar o cliente pelo ID
    Optional<Client> savedClientOptional = clientRepository.findById("12345");

    // Verificar se o cliente foi salvo com sucesso
    assertTrue(savedClientOptional.isPresent());
    Client savedClient = savedClientOptional.get();
    assertEquals("12345", savedClient.getAccountId());
    assertEquals("John Doe", savedClient.getName());
    assertTrue(savedClient.getIsPrimeExclusive());
    assertEquals(new BigDecimal("1000.00"), savedClient.getBalance());
    assertEquals(LocalDate.of(1985, 5, 15), savedClient.getBirthDate());
  }

  @Test
  void testFindClientById() {
    // Salvar um cliente no banco de dados usando o repositório
    Client client = new Client();
    client.setAccountId("54321");
    client.setName("Jane Smith");
    client.setIsPrimeExclusive(false);
    client.setBalance(new BigDecimal("500.00"));
    client.setBirthDate(LocalDate.of(1990, 10, 20));
    clientRepository.save(client);

    // Buscar o cliente pelo ID
    Optional<Client> foundClientOptional = clientRepository.findById("54321");

    // Verificar se o cliente foi encontrado
    assertTrue(foundClientOptional.isPresent());
    Client foundClient = foundClientOptional.get();
    assertEquals("54321", foundClient.getAccountId());
    assertEquals("Jane Smith", foundClient.getName());
    assertFalse(foundClient.getIsPrimeExclusive());
    assertEquals(new BigDecimal("500.00"), foundClient.getBalance());
    assertEquals(LocalDate.of(1990, 10, 20), foundClient.getBirthDate());
  }
}
