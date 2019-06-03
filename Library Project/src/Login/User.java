package Login;

public class User {
    String name;
    String address;
    String memberId;
    String userName;
    String password;
    boolean member = true;

    public User(String name, String address, String memberId, String userName, String password, boolean member) {
        this.name = name;
        this.address = address;
        this.memberId = memberId;
        this.userName = userName;
        this.password = password;
        this.member = member;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }
}

