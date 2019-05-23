package sample;

import Model.Account;
import Model.Member;


public class SceneData {


    //This is the data we want to share between LoginView and and memberView
    private Account loggedInMember;

    //Singleton pattern:
    private static SceneData instance = new SceneData(); //create one single hidden instance
    private SceneData(){} //private constructor
    public static SceneData getInstance(){return instance;} //getter for the single instance


    public Account getLoggedInMember() {
        return loggedInMember;
    }

    public void setLoggedInCustomer(Account loggedInMember) {
        this.loggedInMember = loggedInMember;
    }

}