package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.persist.ItemStore;
import java.util.List;

@Service
public class ItemService {

    private final ItemStore itemStore;

    public ItemService(ItemStore itemStore) {
        this.itemStore = itemStore;
    }

    public List<Item> findAll() {
        return itemStore.findAll();
    }

    public Item create(Item item) {
        return itemStore.create(item);
    }

    public Item findById(int id) {
        return itemStore.findById(id);
    }

    public void update(Item item) {
        itemStore.update(item);
    }

    public void delete(int id) {
        itemStore.delete(id);
    }

    public List<Item> completed() {
        return itemStore.findCompleted();
    }

    public List<Item> findNew() {
        return itemStore.findNew();
    }

    public void itemDone(int id) {
        itemStore.itemDone(id);
    }
}