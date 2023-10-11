package com.plisboa.banking.initializer;

import com.plisboa.banking.entity.Client;
import com.plisboa.banking.repository.ClientRepository;
import java.math.BigDecimal;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ClientInitializer implements CommandLineRunner {

  private final ClientRepository clientRepository;

  @Autowired
  public ClientInitializer(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }


  @Override
  public void run(String... args) {
    // Inicialize os dados do cliente
    Client cliente1 = new Client();
    cliente1.setAccountId("1");
    cliente1.setName("pedro");
    cliente1.setBalance(new BigDecimal(100000));
    cliente1.setIsPrimeExclusive(true);
    cliente1.setBirthDate(new Date("10/13/1992"));
    clientRepository.save(cliente1);

    Client cliente2 = new Client();
    cliente2.setAccountId("2");
    cliente2.setName("gabriel");
    cliente2.setBalance(new BigDecimal(100000));
    cliente2.setIsPrimeExclusive(false);
    cliente2.setBirthDate(new Date("10/31/1992"));
    clientRepository.save(cliente2);
  }
}

