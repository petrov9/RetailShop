package org.example.retailshop;

public class Shop extends Thread {

    public Shop(String name, Runnable runnable) {
        super(runnable);
        setName(name);
        start();
    }
}
