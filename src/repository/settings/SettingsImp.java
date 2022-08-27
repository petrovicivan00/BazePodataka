package src.repository.settings;

import src.core.Settings;
import src.repository.settings.utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class SettingsImp implements Settings {

    private Map parameters = new HashMap();

    public Settings initSettings() {

        Settings settingsImplementation = new SettingsImp();
        settingsImplementation.addParameter("mssql_ip", Constants.MSSQL_IP);
        settingsImplementation.addParameter("mssql_database", Constants.MSSQL_DATABASE);
        settingsImplementation.addParameter("mssql_username", Constants.MSSQL_USERNAME);
        settingsImplementation.addParameter("mssql_password", Constants.MSSQL_PASSWORD);
        return settingsImplementation;
    }
    public Object getParameter(String parameter) {
        return this.parameters.get(parameter);
    }

    public void addParameter(String parameter, Object value) {
        this.parameters.put(parameter, value);
    }
}