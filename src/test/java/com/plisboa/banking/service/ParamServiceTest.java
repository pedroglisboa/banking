package com.plisboa.banking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import com.plisboa.banking.entity.Param;
import com.plisboa.banking.repository.ParamRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ParamServiceTest {

  @InjectMocks
  private ParamService paramService;

  @Mock
  private ParamRepository paramRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSaveParam() {
    // Configuração do cenário de teste
    Param paramToSave = new Param();
    paramToSave.setId("param123");
    when(paramRepository.save(paramToSave)).thenReturn(paramToSave);

    // Executa o método de teste
    Param savedParam = paramService.saveParam(paramToSave);

    // Verifica o resultado
    assertEquals("param123", savedParam.getId());
  }

  @Test
  void testFindParam() {
    // Configuração do cenário de teste
    Param expectedParam = new Param();
    expectedParam.setId("param123");
    when(paramRepository.findById("param123")).thenReturn(Optional.of(expectedParam));

    // Executa o método de teste
    Param foundParam = paramService.findParam("param123");

    // Verifica o resultado
    assertEquals("param123", foundParam.getId());
  }

  @Test
  void testFindParamNotFound() {
    // Configuração do cenário de teste
    when(paramRepository.findById("param123")).thenReturn(Optional.empty());

    // Executa o método de teste
    Param foundParam = paramService.findParam("param123");

    // Verifica o resultado
    assertNull(foundParam);
  }

  @Test
  void testFindAllParams() {
    // Configuração do cenário de teste
    List<Param> params = new ArrayList<>();
    params.add(new Param());
    params.add(new Param());
    when(paramRepository.findAll()).thenReturn(params);

    // Executa o método de teste
    Iterable<Param> allParams = paramService.findAllParams();

    // Verifica o resultado
    assertEquals(2, ((List<Param>) allParams).size());
  }
}

