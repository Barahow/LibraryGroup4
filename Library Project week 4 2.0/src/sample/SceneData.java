package sample;

import Model.Member;

/*
 * This class is for sharing data between scenes.
 * - The LoginController will store the logged in customer.
 * - The CustomerController can then fetch this customer and show information for it.
 *
 * It's possible to add more data in this singleton class, if we have more scenes and more data to share.
 * Just add more fields and setters/getters.
 *
 */
public class SceneData {


    //This is the data we want to share between LoginView and and CustomerView
    private Member loggedInMember;

    //Singleton pattern:
    private static SceneData instance = new SceneData(); //create one single hidden instance
    private SceneData(){} //private constructor
    public static SceneData getInstance(){return instance;} //getter for the single instance


    public Member getLoggedInMember() {
        return loggedInMember;
    }

    public void setLoggedInCustomer(Member loggedInMember) {
        this.loggedInMember = loggedInMember;
    }

}