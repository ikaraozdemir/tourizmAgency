package entity;

public class User {
    private int id;
    private Role role;
    private String name;
    private String password;

    public User() {
    }

    public User(int id, Role role, String name, String password) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.password = password;
    }


    public enum Role {
        ADMIN,
        EMPLOYEE
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role=" + role +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
