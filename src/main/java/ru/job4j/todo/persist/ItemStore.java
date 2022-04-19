package ru.job4j.todo.persist;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import java.util.List;
import java.util.function.Function;

@Repository
@ThreadSafe
public class ItemStore {
    private final SessionFactory sf;

    public ItemStore(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Item> findAll() {
        return this.tx(session -> session.createQuery(
                "from ru.job4j.todo.model.Item").list(), sf);
    }

    public Item create(Item item) {
        tx(session -> session.save(item), sf);
        return item;
    }

    public Item findById(Integer id) {
        return this.tx(session -> session.get(Item.class, id), sf);
    }

    public void update(Item item) {
        this.tx(session -> {
            session.update(item);
            return new Item();
        }, sf);
    }

    public void delete(int id) {
        Item item = new Item();
        item.setId(id);
        this.tx(session -> {
            session.delete(item);
            return new Item();
        }, sf);
    }

    public List<Item> findCompleted() {
        return findIsDone(true);
    }

    public List<Item> findNew() {
        return findIsDone(false);
    }

    private List<Item> findIsDone(boolean condition) {
        return this.tx(session -> session.createQuery(
                        "from ru.job4j.todo.model.Item where done = :condition")
                .setParameter("condition", condition).list(), sf);
    }

    public void itemDone(int id) {
        this.tx(session -> session.createQuery("update ru.job4j.todo.model.Item i set i.done = :done where i.id = :id")
                .setParameter("done", true)
                .setParameter("id", id)
                .executeUpdate(), sf);
    }

    private <T> T tx(final Function<Session, T> command, SessionFactory sf) {
        final Session session = this.sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}