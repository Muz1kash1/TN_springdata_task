package com.example.tn_data_task.jpa;

import com.example.tn_data_task.entities.PersonBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonBankRepository extends JpaRepository<PersonBank, Long> {
}
