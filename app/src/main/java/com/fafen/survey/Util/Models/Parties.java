package com.fafen.survey.Util.Models;

/**
 * Created by andresrodriguez on 6/6/18.
 */

public class Parties {
    private int id = 0;
    private String name = "";
    private String abv = "";

    public Parties() {
    }

    public Parties(int id, String name, String abv) {
        this.id = id;
        this.name = name;
        this.abv = abv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }
}
