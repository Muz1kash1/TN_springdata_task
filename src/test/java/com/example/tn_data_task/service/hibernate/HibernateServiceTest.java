package com.example.tn_data_task.service.hibernate;

import com.example.tn_data_task.entities.Bank;
import com.example.tn_data_task.entities.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class HibernateServiceTest {

  @Mock
  private SessionFactory sessionFactory;

  @Mock
  private Session session;

  @Mock
  private org.hibernate.query.Query<Person> personQuery;

  @Mock
  private org.hibernate.query.Query<Bank> bankQuery;

  @InjectMocks
  private HibernateService hibernateService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Тест реализации через хибернейт")
  void testRun() {
    List<Person> personList = new ArrayList<>();
    personList.add(new Person(1L, "John Doe"));
    personList.add(new Person(2L, "Jane Smith"));

    List<Bank> banks = new ArrayList<>();
    banks.add(new Bank(1L, "Bank A"));
    banks.add(new Bank(2L, "Bank B"));

    when(sessionFactory.getCurrentSession()).thenReturn(session);
    when(session.createQuery("FROM Person", Person.class)).thenReturn(personQuery);
    when(personQuery.list()).thenReturn(personList);

    when(session.createQuery("FROM Bank", Bank.class)).thenReturn(bankQuery);
    when(bankQuery.list()).thenReturn(banks);

    hibernateService.run();

    verify(session, times(1)).flush();
    verify(session, times(2)).createQuery(anyString(), any());
    verify(session, times(1)).createQuery(anyString(), eq(Person.class));
    verify(session, times(1)).createQuery(anyString(), eq(Bank.class));
    verify(session, times(2)).merge(any(Bank.class));
  }
}