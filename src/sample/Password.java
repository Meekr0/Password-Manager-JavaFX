package sample;

public class Password {

    private String name;
    private String password;
    private String domain;
    private String login;
    private String category;

    public Password(String name, String password, String login, String domain, String category) {

        this.name = name;
        this.password = password;
        this.login = login;
        this.domain = domain;
        this.category = category;

    }

    @Override
    public String toString() {
        return (this.name + " " + this.password + " " + this.login + " " + this.domain + " " + this.category);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public String getDomain() {
        return domain;
    }

    public String getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
