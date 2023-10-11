package com.plisboa.banking.initializer;

import static com.plisboa.banking.utils.BankingConstants.DEPOSIT_REDIS;
import static com.plisboa.banking.utils.BankingConstants.MAX_TAX_REDIS;
import static com.plisboa.banking.utils.BankingConstants.MAX_VALUE_REDIS;
import static com.plisboa.banking.utils.BankingConstants.MIN_TAX_REDIS;
import static com.plisboa.banking.utils.BankingConstants.MIN_VALUE_REDIS;
import static com.plisboa.banking.utils.BankingConstants.WITHDRAW_REDIS;

import com.plisboa.banking.entity.Client;
import com.plisboa.banking.entity.Param;
import com.plisboa.banking.service.ClientService;
import com.plisboa.banking.service.ParamService;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ClientInitializer implements CommandLineRunner {

  private final ClientService clientService;
  private final ParamService paramService;

  @Autowired
  public ClientInitializer(ClientService clientService, ParamService paramService) {
    this.clientService = clientService;
    this.paramService = paramService;
  }

  @Override
  public void run(String... args) {

    Client cliente1 = new Client();
    cliente1.setAccountId("1");
    cliente1.setName("pedro");
    cliente1.setBalance(new BigDecimal(100000));
    cliente1.setIsPrimeExclusive(true);
    cliente1.setBirthDate(LocalDate.of(1990, 5, 15));
    clientService.createClient(cliente1);

    Client cliente2 = new Client();
    cliente2.setAccountId("2");
    cliente2.setName("gabriel");
    cliente2.setBalance(new BigDecimal(100000));
    cliente2.setIsPrimeExclusive(false);
    cliente2.setBirthDate(LocalDate.of(1990, 5, 15));
    clientService.createClient(cliente2);

    Param paramMinValue = new Param();
    paramMinValue.setId(MIN_VALUE_REDIS);
    paramMinValue.setName(MIN_VALUE_REDIS);
    paramMinValue.setValue(new BigDecimal(100));
    paramMinValue.setStringValue(paramMinValue.getValue().toString());
    paramService.saveParam(paramMinValue);

    Param paramMaxValue = new Param();
    paramMaxValue.setId(MAX_VALUE_REDIS);
    paramMaxValue.setName(MAX_VALUE_REDIS);
    paramMaxValue.setValue(new BigDecimal(300));
    paramMaxValue.setStringValue(paramMinValue.getValue().toString());
    paramService.saveParam(paramMaxValue);

    Param paramMinTax = new Param();
    paramMinTax.setId(MIN_TAX_REDIS);
    paramMinTax.setName(MIN_TAX_REDIS);
    paramMinTax.setValue(BigDecimal.valueOf(0.004));
    paramMinTax.setStringValue(paramMinValue.getValue().toString());
    paramService.saveParam(paramMinTax);

    Param paramMaxTax = new Param();
    paramMaxTax.setId(MAX_TAX_REDIS);
    paramMaxTax.setName(MAX_TAX_REDIS);
    paramMaxTax.setValue(BigDecimal.valueOf(0.01));
    paramMaxTax.setStringValue(paramMinValue.getValue().toString());
    paramService.saveParam(paramMaxTax);

    Param deposit = new Param();
    deposit.setId(DEPOSIT_REDIS);
    deposit.setName(DEPOSIT_REDIS);
    deposit.setValue(BigDecimal.valueOf(0));
    deposit.setStringValue(DEPOSIT_REDIS);
    paramService.saveParam(deposit);

    Param withdraw = new Param();
    withdraw.setId(WITHDRAW_REDIS);
    withdraw.setName(WITHDRAW_REDIS);
    withdraw.setValue(BigDecimal.valueOf(1));
    withdraw.setStringValue(WITHDRAW_REDIS);
    paramService.saveParam(withdraw);

  }
}

