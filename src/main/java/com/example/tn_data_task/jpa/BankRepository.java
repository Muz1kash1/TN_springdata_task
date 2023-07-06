package com.example.tn_data_task.jpa;

import com.example.tn_data_task.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
}
