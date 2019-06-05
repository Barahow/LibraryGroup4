package Home;

import Login.User;

public class DataManager {
    private static DataManager ourInstance = new DataManager();
    private static User loggedInUser;
    private static String searchQuery;


    public static DataManager getInstance() {
        return ourInstance;
    }

    private DataManager() {
    }

    public static DataManager getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(DataManager ourInstance) {
        DataManager.ourInstance = ourInstance;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        DataManager.searchQuery = searchQuery;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        DataManager.loggedInUser = loggedInUser;
    }
}
