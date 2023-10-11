package com.plisboa.banking.repository;

import com.plisboa.banking.entity.Param;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParamRepository extends CrudRepository<Param, String> {
}
