package com.geekbrains.ru.springmvcdemo.repository.impl;

import com.geekbrains.ru.springmvcdemo.domain.Product;
import com.geekbrains.ru.springmvcdemo.repository.ProductDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {

    public static SessionFactory sessionfactory;

    private static void init() {
        sessionfactory = new Configuration()
                .configure("config/hibernate.cfg.xml")
                .buildSessionFactory();
    }

    @Override
    public Product findById(Long id) {
        try {
            init();
            Product product = sessionfactory.openSession().get(Product.class, id);
            return product;
        } finally {
            sessionfactory.close();
        }
    }

    @Override
    public List<Product> findAll() {
        try {
            init();
            List<Product> products = sessionfactory.openSession().createQuery("From Product").list();
            return products;
        } finally {
            sessionfactory.close();
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            init();
            Product product = sessionfactory.openSession().get(Product.class, id);
            Session session = sessionfactory.getCurrentSession();
            session.beginTransaction();
            session.delete(product);
            session.getTransaction().commit();
        } finally {
            sessionfactory.close();
        }
    }

    @Override
    public Product saveOrUpdate(Product product) {
        try {
            init();
            Session session = sessionfactory.getCurrentSession();
            session.beginTransaction();
            session.saveOrUpdate(product);
            session.getTransaction().commit();
            return product;
        } finally {
            sessionfactory.close();
        }
    }

    @Override
    public void add(Product product) {
        saveOrUpdate(product);
    }
}
