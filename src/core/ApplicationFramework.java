package src.core;

public abstract class ApplicationFramework {

    protected GUI gui;
    protected Repository repository;
    protected Database database;
    protected Settings settings;
    protected QueryBuilder queryBuilder;
    protected ErrorHandler errorHandler;

    public abstract void run();

    public void initialise(GUI gui, Repository repository, Database database, Settings settings, QueryBuilder queryBuilder, ErrorHandler errorHandler) {
        this.gui = gui;
        this.repository = repository;
        this.database = database;
        this.settings = settings;
        this.queryBuilder = queryBuilder;
        this.errorHandler = errorHandler;
        this.errorHandler.addSubscriber(gui);
    }

}