package apiTests.data;

public class DataPlayer {
    public String username;
    public String password_change;
    public String password_repeat;
    public String email;
    public String name;
    public String surname;
    public String currency_code;

    public DataPlayer(String username,
                      String password_change,
                      String password_repeat,
                      String email,
                      String name,
                      String surname,
                      String currency_code) {
        this.username = username;
        this.password_change = password_change;
        this.password_repeat = password_repeat;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.currency_code = currency_code;
    }
}