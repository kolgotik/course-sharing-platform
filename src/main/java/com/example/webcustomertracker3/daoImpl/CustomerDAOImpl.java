package com.example.webcustomertracker3.daoImpl;

import com.example.webcustomertracker3.dao.CustomerDAO;
import com.example.webcustomertracker3.entity.Customer;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<Customer> getCustomers() {

        Session session = entityManager.unwrap(Session.class);
        Query<Customer> query = session.createQuery("from Customer order by lastName", Customer.class);
        List<Customer> list = query.getResultList();

        return list;

    }

    @Override
    public void saveCustomer(Customer customer) {

        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(customer);

    }

    @Override
    public Customer getCustomer(int id) {
        Session session = entityManager.unwrap(Session.class);
        Customer customer = session.get(Customer.class, id);
        return customer;
    }

    @Override
    public void deleteCustomer(int id) {
        Session session = entityManager.unwrap(Session.class);

        Query<Customer> query = session.createQuery("delete from Customer where id=:customerId", Customer.class);
        query.setParameter("customerId", id);
        query.executeUpdate();


        //MY VERSION
        /* Customer customer = session.get(Customer.class, id);
        session.remove(customer);*/
    }

    @Override
    public List<Customer> searchCustomer(String searchName) {

        Session session = entityManager.unwrap(Session.class);

        Query<Customer> query;

        if (searchName != null && searchName.trim().length() > 0) {
            query = session.createQuery("from Customer where lower(firstName) like: theName or lower(lastName) like: theName", Customer.class);
            query.setParameter("theName", "%" + searchName.toLowerCase() + "%");
        } else {
            query = session.createQuery("from Customer", Customer.class);

        }
        List<Customer> list = query.getResultList();

        return list;
    }
}
