package sample.model;


public class Member {
    private String name;
    private String ID;
    private String PhoneNr;

    public  Member (String name, String ID, String phoneNr){
        this.name = name;
        this.ID = ID;
        this.PhoneNr = phoneNr;

    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getID(){
        return ID;
    }
    public void setID(String ID){
        this. ID = ID;
    }
    public String getPhoneNr(){
        return PhoneNr;
    }
    public void setPhoneNr(String phoneNr){
        this.PhoneNr = PhoneNr;
    }
}
