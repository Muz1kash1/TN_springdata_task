package com.example.tn_data_task.service.data;

import com.example.tn_data_task.entities.Bank;
import com.example.tn_data_task.entities.Person;
import com.example.tn_data_task.jpa.BankRepository;
import com.example.tn_data_task.jpa.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JpaRepositoryServiceTest {

  @Mock
  private PersonRepository personRepository;
  @Mock
  private BankRepository bankRepository;
  @InjectMocks
  private JpaRepositoryService jpaRepositoryService;

  @Test
  @DisplayName("Тест метода переписанного на спринг дату и хибернейт")
  void run() {
    MockitoAnnotations.openMocks(this);

    when(personRepository.findAll()).thenReturn(
      List.of(new Person(1L, "Иванов Сидор Петрович"), new Person(2L, "Сидоров Петр Иванович"),
        new Person(3L, "Петров Иван Сидорович"), new Person(4L, "Наличный Артем Андреевич")
      ));
    when(bankRepository.findAll()).thenReturn(
      List.of(new Bank(1L, "Сбер"), new Bank(2L, "Тинькофф"), new Bank(3L, "Хоум Банк"), new Bank(4L, "ВТБ"))
    );
    jpaRepositoryService.run();
    verify(personRepository, times(1)).findAll();
    verify(bankRepository, times(1)).findAll();
  }
}