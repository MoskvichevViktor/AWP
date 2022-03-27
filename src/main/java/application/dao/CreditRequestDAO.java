package application.dao;


import application.models.CreditRequest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CreditRequestDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public CreditRequestDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<CreditRequest> index() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from CreditRequest p", CreditRequest.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public CreditRequest show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(CreditRequest.class, id);
    }

    @Transactional
    public void save(CreditRequest creditRequest) {
        Session session = sessionFactory.getCurrentSession();
        session.save(creditRequest);
    }

    @Transactional
    public void update(int id, CreditRequest updatedCreditRequest) {
        Session session = sessionFactory.getCurrentSession();
        CreditRequest creditRequestToBeUpdated = session.get(CreditRequest.class, id);

        creditRequestToBeUpdated.setName(updatedCreditRequest.getName());
        creditRequestToBeUpdated.setPasport(updatedCreditRequest.getPasport());
        creditRequestToBeUpdated.setMaritalstatus(updatedCreditRequest.getMaritalstatus());
        creditRequestToBeUpdated.setAdress(updatedCreditRequest.getAdress());
        creditRequestToBeUpdated.setPhone(updatedCreditRequest.getPhone());
        creditRequestToBeUpdated.setJobdetails(updatedCreditRequest.getJobdetails());
        creditRequestToBeUpdated.setCreditsum(updatedCreditRequest.getCreditsum());

    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(CreditRequest.class, id));
    }
}