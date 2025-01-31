package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Backpack implements ActorContainer<Collectible> {
    private int actualCapacity;
    private int capacity;
    private String name;

    private List<Collectible> items;

    public Backpack(String name, int capacity){
        this.name = name;
        this.capacity = capacity;
        this.actualCapacity = 0;
        items = new ArrayList<>();
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public int getSize() {
        return this.actualCapacity;
    }

    @Override
    public @NotNull List<Collectible> getContent() {
        return List.copyOf(items);
    }

    @Override
    public void add(@NotNull Collectible actor) {
        if(actualCapacity == capacity){
            throw new IllegalStateException(getName() + " is full!");
        }
        actualCapacity++;
        items.add(actor);
    }

    @Override
    public void remove(@NotNull Collectible actor) {
        items.remove(actor);
    }

    @NotNull
    @Override
    public Iterator<Collectible> iterator() {
        return items.iterator();
    }

    @Override
    public @Nullable Collectible peek() {
        if(!items.isEmpty()){
            return items.get(items.size() - 1);
        }
        return null;
    }

    @Override
    public void shift() {
        Collections.rotate(items,1);
    }
}
