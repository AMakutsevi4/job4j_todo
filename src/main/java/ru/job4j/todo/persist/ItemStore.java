package ru.job4j.todo.persist;

import net.jcip.annotations.ThreadSafe;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import java.util.Collection;
import java.util.List;

@ThreadSafe
@Repository
public class ItemStore {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    public Collection<Item> findAll() {
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ru.job4j.todo.model.Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public void add(Item item) {
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
    }
}