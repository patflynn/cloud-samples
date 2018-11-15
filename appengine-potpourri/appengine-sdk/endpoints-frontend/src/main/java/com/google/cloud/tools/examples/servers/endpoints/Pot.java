package com.google.cloud.tools.examples.servers.endpoints;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Pot {

    @Id
    private Long id;

    private String framework;

  private String fanciful = "fancyfeast";

    public Pot() {
    }

    public Pot(long id, String framework) {
        this.id = id;
        this.framework = framework;
    }

    public Pot(String framework) {
        this.framework = framework;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFramework() {
        return framework;
    }

    public void setFramework(String frameworks) {
        this.framework = frameworks;
    }

  public String getFanciful() {
    return fanciful;
  }

  public void setFanciful(String fanciful) {
    this.fanciful = fanciful;
  }
}
