package application.dao;

import application.models.CreditResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CreditResponseDAO {

    private final SessionFactory sessionFactory;


    @Autowired
    public CreditResponseDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<CreditResponse> index() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from CreditResponse p", CreditResponse.class)
                .getResultList();
    }

    //sort by status
    @Transactional(readOnly = true)
    public List<CreditResponse> indexOnlyApproved() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from CreditResponse p where p.status = 'approved'", CreditResponse.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public CreditResponse show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(CreditResponse.class, id);
    }

    @Transactional
    public void save(CreditResponse creditResponse) {
        Session session = sessionFactory.getCurrentSession();
        session.save(creditResponse);
    }

    @Transactional
    public void update(int id, CreditResponse updatedCreditResponse) {
        Session session = sessionFactory.getCurrentSession();
        CreditResponse creditResponseToBeUpdated = session.get(CreditResponse.class, id);

        creditResponseToBeUpdated.setIdrequest(updatedCreditResponse.getIdrequest());
        creditResponseToBeUpdated.setName(updatedCreditResponse.getName());
        creditResponseToBeUpdated.setPasport(updatedCreditResponse.getPasport());
        creditResponseToBeUpdated.setPeriod(updatedCreditResponse.getPeriod());
        creditResponseToBeUpdated.setSum(updatedCreditResponse.getSum());
        creditResponseToBeUpdated.setStatus(updatedCreditResponse.getStatus());

    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(CreditResponse.class, id));
    }
}