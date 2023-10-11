package com.plisboa.banking.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.plisboa.banking.entity.Param;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;

@DataRedisTest
class ParamRepositoryTest {

  @Autowired
  private ParamRepository paramRepository;

  @Test
  void testSaveAndRetrieveParam() {
    // Create a Param object
    Param param = new Param();
    param.setId("param-1");
    param.setName("TestParam");
    param.setValue(BigDecimal.TEN);
    param.setStringValue("ValueString");

    // Save the Param to the repository
    paramRepository.save(param);

    // Retrieve the Param from the repository
    Param retrievedParam = paramRepository.findById("param-1").orElse(null);

    // Assert that the retrieved Param matches the saved Param
    assertNotNull(retrievedParam);
    assertEquals(param.getId(), retrievedParam.getId());
    assertEquals(param.getName(), retrievedParam.getName());
    assertEquals(param.getValue(), retrievedParam.getValue());
    assertEquals(param.getStringValue(), retrievedParam.getStringValue());
  }
}
