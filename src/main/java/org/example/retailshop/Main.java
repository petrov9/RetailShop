package org.example.retailshop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    private static final int RETAIL_SHOPS = 10;
    private static final int AVAILABLE_COUNT_OF_PRODUCTS_IN_WAREHOUSE = 5;
    private static final int AVAILABLE_COUNT_OF_PRODUCTS_IN_REMOTE_WAREHOUSE = RETAIL_SHOPS;

    private static final List<Product> WAREHOUSE_PRODUCTS = Collections
        .synchronizedList(generateLocalProducts());
    private static final List<Product> REMOTE_WAREHOUSE_PRODUCTS = Collections
        .synchronizedList(generateRemoteProducts());

    public static void main(String[] args) {
        Runnable businessLogic = () -> {
            Product product;

            product = getProductFromWarehouse();
            System.out.println(Thread.currentThread().getName() + ": got product '" + product + "'");
        };

        for (int i = 0; i < RETAIL_SHOPS; i++) {
            new Shop("shop " + i, businessLogic);
        }

    }

    private static Product getProductFromWarehouse() {
        if (WAREHOUSE_PRODUCTS.isEmpty()) {
            WAREHOUSE_PRODUCTS.add(getProductFromRemoteWarehouse());
        }

        return WAREHOUSE_PRODUCTS.remove(0);
    }

    private static synchronized Product getProductFromRemoteWarehouse() {
        int sec = (int) (Math.random() * 10);
        try {
            System.out.println(Thread.currentThread().getName() + ": delivery time from remote warehouse " + sec + " sec.");
            Thread.sleep(1000 * sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return REMOTE_WAREHOUSE_PRODUCTS.remove(0);
    }

    private static List<Product> generateLocalProducts() {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < AVAILABLE_COUNT_OF_PRODUCTS_IN_WAREHOUSE; i++) {
            products.add(new Product("local product " + i));
        }

        return products;
    }

    private static List<Product> generateRemoteProducts() {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < AVAILABLE_COUNT_OF_PRODUCTS_IN_REMOTE_WAREHOUSE; i++) {
            products.add(new Product("remote product " + i));
        }

        return products;
    }


}
