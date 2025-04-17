package com.example.toktoralieva_orozbekova_duishenaliev.pizza.enums;

public enum Permission {
    PRODUCTS_READ("products:read"),
    PRODUCTS_WRITE("products:write"),
    USERS_READ("users_read"),
    USERS_WRITE("users_write"),
    BUCKET_READ("bucket_read"),
    BUCKET_WRITE("bucket_write"),
    ORDERS_READ("orders_read"),
    ORDERS_WRITE("orders_write");


    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
