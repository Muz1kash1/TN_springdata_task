package com.example.tn_data_task.jpa;

import com.example.tn_data_task.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
