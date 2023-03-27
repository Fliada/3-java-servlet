package example.accounts;

import example.dbService.DBException;
import example.dbService.DBService;
import example.dbService.dataSets.UsersDataSet;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private final Map<String, UserProfile> sessionIdToProfile;
    DBService dbService = new DBService();

    public AccountService() {
        dbService.printConnectInfo();
        sessionIdToProfile = new HashMap<>();
    }

    public void addNewUser(UserProfile user) {
        try {
            dbService.addUser(user);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    public UserProfile getUserByLogin(String login) {
        //return loginToProfile.get(login);
        UsersDataSet set = null;
        try {
            set = dbService.getUserByLogin(login);
        } catch (DBException e) {
            return null;
        }
        return new UserProfile(set.getName(), set.getPass(), set.getEmail());
    }

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}