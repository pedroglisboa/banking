package com.plisboa.banking.domain.repository;

import com.plisboa.banking.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

}
