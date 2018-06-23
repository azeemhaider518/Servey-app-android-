package com.fafen.survey.Util.Models;

/**
 * Created by andresrodriguez on 6/6/18.
 */

public class Constituencies {
    private int id = 0;
    private String name = "";
    private String assemblyName = "";
    private String region = "";
    private int geographical_clusters_id = 0;
    private String geographical_clusters = "";

    public Constituencies() {
    }

    public Constituencies(int id, String name, String assemblyName, String region, int geographical_clusters_id, String geographical_clusters) {
        this.id = id;
        this.name = name;
        this.assemblyName = assemblyName;
        this.region = region;
        this.geographical_clusters_id = geographical_clusters_id;
        this.geographical_clusters = geographical_clusters;
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

    public String getAssemblyName() {
        return assemblyName;
    }

    public void setAssemblyName(String assemblyName) {
        this.assemblyName = assemblyName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getGeographical_clusters_id() {
        return geographical_clusters_id;
    }

    public void setGeographical_clusters_id(int geographical_clusters_id) {
        this.geographical_clusters_id = geographical_clusters_id;
    }

    public String getGeographical_clusters() {
        return geographical_clusters;
    }

    public void setGeographical_clusters(String geographical_clusters) {
        this.geographical_clusters = geographical_clusters;
    }
}
