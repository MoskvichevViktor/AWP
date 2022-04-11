package application.dao;


import application.models.Contract;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ContractDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public ContractDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Contract> index() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from Contract p", Contract.class)
                .getResultList();
    }

    //sort by status
    @Transactional(readOnly = true)
    public List<Contract> indexOnlySigned() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from Contract p where p.status = 'signed'", Contract.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public Contract show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Contract.class, id);
    }

    @Transactional
    public void save(Contract contract) {
        Session session = sessionFactory.getCurrentSession();
        session.save(contract);
    }

    @Transactional
    public void update(int id, Contract updatedContract) {
        Session session = sessionFactory.getCurrentSession();
        Contract contractToBeUpdated = session.get(Contract.class, id);

        contractToBeUpdated.setName(updatedContract.getName());
        contractToBeUpdated.setPasport(updatedContract.getPasport());
        contractToBeUpdated.setPeriod(updatedContract.getPeriod());
        contractToBeUpdated.setSum(updatedContract.getSum());
        contractToBeUpdated.setStatus(updatedContract.getStatus());
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Contract.class, id));
    }
}
