package com.arch.soft.database.repos;

import com.arch.soft.database.model.person.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
