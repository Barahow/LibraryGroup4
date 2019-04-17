

public class Member {

    String name;
    String ID;
    String phoneNr;


    public Member(String name, String ID, String phoneNr) {
        this.name = name;
        this.ID = ID;
        this.phoneNr = phoneNr;


    }

    public String getOutput(String name, String ID, String phoneNr){
        return "Book   " + name + "\r\n(Member)      " +ID+ "\r\n";
    }

    }


