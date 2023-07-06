package com.example.tn_data_task.service.data;

import com.example.tn_data_task.entities.Bank;
import com.example.tn_data_task.entities.Person;
import com.example.tn_data_task.jpa.BankRepository;
import com.example.tn_data_task.jpa.PersonBankRepository;
import com.example.tn_data_task.jpa.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@AllArgsConstructor
@Service
public class JpaRepositoryService {
  private final PersonRepository personRepository;
  private final BankRepository bankRepository;
  @Transactional
  public void run(){
    List<Person> personList = personRepository.findAll();
    for (Person person : personList){
      System.out.println(person.getFullName().split(" ")[0]);
    }
    List<Bank> banks = bankRepository.findAll();
    for (Bank bank: banks){
      bank.setName("Новое имя банка");
    }
    bankRepository.saveAll(banks);
  }
}
