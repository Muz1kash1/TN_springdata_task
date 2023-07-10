package com.example.tn_data_task.service.hibernate;

import com.example.tn_data_task.entities.Bank;
import com.example.tn_data_task.entities.Person;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@AllArgsConstructor
@Service
public class HibernateService {
  private final SessionFactory sessionFactory;

  @Transactional
  public void run() {
    Session session = sessionFactory.getCurrentSession();

    List<Person> personList = session.createQuery("FROM Person", Person.class).list();
    for (Person person : personList) {
      System.out.println(person.getFullName().split(" ")[0]);
    }

    List<Bank> banks = session.createQuery("FROM Bank", Bank.class).list();
    for (Bank bank : banks) {
      bank.setName("Новое имя банка");
    }
    for (Bank bank : banks) {
      session.merge(bank);
    }
    session.flush();
  }
}
