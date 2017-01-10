package com.google.cloud.tools.examples.servers.jdo;

import javax.jdo.annotations.*;

/**
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Garbage {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private String contents;

    public Garbage(String contents) {
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
