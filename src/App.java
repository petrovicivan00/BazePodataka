package src;

import src.core.*;
import src.errorHandler.EHImp;
import src.gui.SwingGUI;
import src.querybuilder.QueryBuilderImp;
import src.repository.MSSQLrep;
import src.repository.database.DatabaseImp;
import src.repository.settings.SettingsImp;

public class App extends ApplicationFramework {

    private static App instance;

    private App(){
    }

    public static App getInstance(){

        if(instance==null){
            instance = new App();
        }
        return instance;
    }

    public static void main(String[] args) {

        ErrorHandler errorHandler = new EHImp();
        Settings settings = new SettingsImp().initSettings();
        Repository r = new MSSQLrep(settings);
        Database d = new DatabaseImp(r);
        QueryBuilder queryBuilder = new QueryBuilderImp(d);
        GUI gui = new SwingGUI(d, queryBuilder);
        ApplicationFramework app = App.getInstance();
        app.initialise(gui, r, d, settings, queryBuilder, errorHandler);
        app.run();
    }

    @Override
    public void run() {
        gui.start();
    }

    public static QueryBuilder getQueryBuilder() {
        return instance.queryBuilder;
    }

    public static GUI getGui() {
        return instance.gui;
    }

    public static ErrorHandler getErrorHandler() {
        return instance.errorHandler;
    }
}

