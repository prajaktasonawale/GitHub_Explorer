package com.dev.github_explorer.models;

import java.io.Serializable;

public class Owner implements Serializable {
    private String login;
    private String avatar_url;
    private String html_url;

    // Add serialVersionUID for better serialization
    private static final long serialVersionUID = 1L;

    // Getters and setters
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getAvatar_url() { return avatar_url; }
    public void setAvatar_url(String avatar_url) { this.avatar_url = avatar_url; }

    public String getHtml_url() { return html_url; }
    public void setHtml_url(String html_url) { this.html_url = html_url; }
}