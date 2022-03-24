package ru.job4j.todo.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.persist.ItemStore;

import java.util.ArrayList;

@ThreadSafe
@Service
public class ItemService {

    private final ItemStore itemStore;

    public ItemService(ItemStore itemStore) {
        this.itemStore = itemStore;
    }

    public void create(Item item) {
        itemStore.add(item);
    }

    public Object findAll() {
        return new ArrayList<>(itemStore.findAll());
    }
}