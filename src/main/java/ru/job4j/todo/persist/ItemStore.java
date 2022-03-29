package ru.job4j.todo.persist;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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

    private static final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private static final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public Collection<Item> findAll() {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        List result = session.createQuery("from ru.job4j.todo.model.Item").list();
        tx.commit();
        session.close();
        return result;
    }

    public void add(Item item) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        session.beginTransaction();
        session.save(item);
        tx.commit();
        session.close();
    }
}
