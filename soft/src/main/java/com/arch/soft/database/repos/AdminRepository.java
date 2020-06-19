package com.arch.soft.database.repos;

import com.arch.soft.database.model.person.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Query(value = "select max(id) from admins", nativeQuery = true)
    Long findMaxId();
}
