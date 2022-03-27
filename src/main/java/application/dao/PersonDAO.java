package application.dao;

import application.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Component
public class PersonDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from Person p", Person.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
    }

    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        Session session = sessionFactory.getCurrentSession();
        Person personToBeUpdated = session.get(Person.class, id);

        personToBeUpdated.setName(updatedPerson.getName());
        personToBeUpdated.setPasport(updatedPerson.getPasport());
        personToBeUpdated.setAdress(updatedPerson.getAdress());
        personToBeUpdated.setPhone(updatedPerson.getPhone());

    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Person.class, id));
    }

    //sort by name
    @Transactional
    public List<Person> serchByName(String name) {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from Person p where p.name = " + "'"+name+"'", Person.class)
                .getResultList();
    }
    //sort by pasport
    @Transactional
    public List<Person> serchByPasport(String pasport) {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from Person p where p.pasport = " + "'"+pasport+"'", Person.class)
                .getResultList();
    }

    //sort by phone
    @Transactional
    public List<Person> serchByPhone(String phone) {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from Person p where p.phone = " + "'"+phone+"'", Person.class)
                .getResultList();
    }
}