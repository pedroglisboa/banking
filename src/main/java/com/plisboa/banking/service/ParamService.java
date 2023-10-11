package com.plisboa.banking.service;


import com.plisboa.banking.entity.Param;
import com.plisboa.banking.repository.ParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParamService {

  @Autowired
  private ParamRepository paramRepository;

  public Param saveParam(Param param) {
    return paramRepository.save(param);
  }

  public Param findParam(String id) {
    return paramRepository.findById(id).orElse(null);
  }

  public Iterable<Param> findAllParams() {
    return paramRepository.findAll();
  }
}

