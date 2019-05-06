package sample;

import javafx.beans.property.SimpleStringProperty;

public class Member {

    private SimpleStringProperty id;
    private SimpleStringProperty fName;
    private SimpleStringProperty SSN;
    private SimpleStringProperty lName;

    public Member(String id, String fName, String SSN, String lName) {
        this.id = new SimpleStringProperty(  id);
        this.fName = new SimpleStringProperty(fName);
        this.SSN = new SimpleStringProperty(SSN);
        this.lName = new SimpleStringProperty(lName);
    }

    public String getId() {
        return id.get();
    }

    public String idProperty() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set( id );
    }

    public String getfName() {
        return fName.get();
    }

    public String fNameProperty() {
        return fName.get();
    }

    public void setfName(String fName) {
        this.fName.set( fName );
    }

    public String getSSN() {
        return SSN.get();
    }

    public String SSNProperty() {
        return SSN.get();
    }

    public void setSSN(String SSN) {
        this.SSN.set( SSN );
    }

    public String getlName() {
        return lName.get();
    }

    public String lNameProperty() {
        return lName.get();
    }

    public void setlName(String lName) {
        this.lName.set( lName );
    }
}


