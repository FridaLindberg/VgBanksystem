package Users;

public abstract class User {

    private String name;
    private String password;
    private String idNumber;

    public User() {
    }

    public User(String name, String password, String idNumber) {
        this.name = name;
        this.password = password;
        this.idNumber = idNumber;
    }
    public String getName() {
        return name;
    }
    public String getNumber(){
        return idNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
