package example.accounts;

import example.dbService.DBException;
import example.dbService.DBService;
import example.dbService.dataSets.UsersDataSet;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private final Map<String, UsersDataSet> sessionIdToProfile;
    DBService dbService = new DBService();

    public AccountService() {
        dbService.printConnectInfo();
        sessionIdToProfile = new HashMap<>();
    }

    public void addNewUser(String name, String email, String pass) throws DBException {
        dbService.addUser(name, email, pass);
    }

    public UsersDataSet getUserByLogin(String login) {
        //return loginToProfile.get(login);
        UsersDataSet set = null;
        try {
            set = dbService.getUserByLogin(login);
        } catch (Exception e) {
            return null;
        }
        return set;
    }

    public UsersDataSet getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UsersDataSet userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}