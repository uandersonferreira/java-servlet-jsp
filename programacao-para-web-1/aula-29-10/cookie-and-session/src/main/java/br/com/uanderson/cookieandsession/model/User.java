package br.com.uanderson.cookieandsession.model;

public class User {
    private String login;
    private String password;


    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    // Getters e setters
    public String getLogin() { return login; }
    public String getPassword() { return password; }
}