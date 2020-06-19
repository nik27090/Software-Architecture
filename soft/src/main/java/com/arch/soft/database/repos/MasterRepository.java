package com.arch.soft.database.repos;

import com.arch.soft.database.model.person.Master;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterRepository extends JpaRepository<Master, Long> {
}
