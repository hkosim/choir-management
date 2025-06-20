package com.hk.personal.choir_management.model.entity.composite;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AuthorityId implements Serializable {
    private String username;
    private String authority;


    public AuthorityId() {
    }

    public AuthorityId(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    // ✅ Must override equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorityId)) return false;
        AuthorityId that = (AuthorityId) o;
        return username.equals(that.username) && authority.equals(that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, authority);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}