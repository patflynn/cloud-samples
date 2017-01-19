package com.google.cloud.tools.examples.servers.jdo;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

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

    @Persistent
    private String consistentString = "WWWWWWOOOOOOHHHHAAAA";

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

    public String getConsistentString() {
        return consistentString;
    }

    public void setConsistentString(String consistentString) {
        this.consistentString = consistentString;
    }
}
