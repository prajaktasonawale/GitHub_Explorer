package com.dev.github_explorer.models;

import java.io.Serializable;

public class Repository implements Serializable {
    private int id;
    private String name;
    private String full_name;
    private String description;
    private String html_url;
    private int stargazers_count;
    private int forks_count;
    private int open_issues_count;
    private String language;
    private String updated_at;
    private Owner owner;

    // Add serialVersionUID for better serialization
    private static final long serialVersionUID = 1L;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFull_name() { return full_name; }
    public void setFull_name(String full_name) { this.full_name = full_name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getHtml_url() { return html_url; }
    public void setHtml_url(String html_url) { this.html_url = html_url; }

    public int getStargazers_count() { return stargazers_count; }
    public void setStargazers_count(int stargazers_count) { this.stargazers_count = stargazers_count; }

    public int getForks_count() { return forks_count; }
    public void setForks_count(int forks_count) { this.forks_count = forks_count; }

    public int getOpen_issues_count() { return open_issues_count; }
    public void setOpen_issues_count(int open_issues_count) { this.open_issues_count = open_issues_count; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getUpdated_at() { return updated_at; }
    public void setUpdated_at(String updated_at) { this.updated_at = updated_at; }

    public Owner getOwner() { return owner; }
    public void setOwner(Owner owner) { this.owner = owner; }
}