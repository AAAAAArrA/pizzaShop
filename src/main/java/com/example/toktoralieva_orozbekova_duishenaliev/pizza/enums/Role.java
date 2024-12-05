package com.example.toktoralieva_orozbekova_duishenaliev.pizza.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    CLIENT(Set.of(Permission.PRODUCTS_READ, Permission.BUCKET_READ,Permission.BUCKET_WRITE,
            Permission.ORDERS_READ, Permission.ORDERS_WRITE)),
    ADMIN(Set.of(Permission.USERS_READ, Permission.USERS_WRITE, Permission.PRODUCTS_READ, Permission.PRODUCTS_WRITE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions=permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthority(){
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
