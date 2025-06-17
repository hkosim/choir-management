package com.hk.personal.choir_management.entity;

import com.hk.personal.choir_management.entity.composite.AuthorityId;
import jakarta.persistence.*;

@Entity
@Table(name = "authorities")
public class Authority {

    @EmbeddedId
    private AuthorityId id;

    public Authority() {
    }

    public Authority(String username, String authority) {
        this.id = new AuthorityId(username, authority);
    }

    // Getter for authority value if needed
    public String getAuthority() {
        return id != null ? id.getAuthority() : null;
    }
}