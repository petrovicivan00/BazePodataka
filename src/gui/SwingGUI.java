package src.gui;

import src.core.Database;
import src.core.GUI;
import src.core.QueryBuilder;
import src.errorHandler.Error;
import src.gui.table.TableModel;
import src.gui.view.MainFrame;
import src.observer.Notification;
import src.observer.enums.NotificatonCode;
import src.repository.resource.implementation.InformationResource;

public class SwingGUI implements GUI {


    private MainFrame instance;
    private TableModel tableModel;
    private Database database;
    String s;
    private final QueryBuilder queryBuilder;

    public SwingGUI(Database database, QueryBuilder queryBuilder) {
        this.database = database;
        this.queryBuilder = queryBuilder;
    }

    @Override
    public void start() {
        instance = MainFrame.getInstance();
        tableModel = new TableModel();
        MainFrame.getInstance().getjTable().setModel(this.getTableModel());
        s = queryBuilder.start(MainFrame.getInstance().getQuery());
        load();
    }

    @Override
    public void update(Object update) {

        if (update instanceof Notification) {
            Notification notification = (Notification) update;
            if (notification.getCode() == NotificatonCode.RESOURCE_LOADED) {
                System.out.println(notification.getData());
            } else {
                MainFrame.getInstance().getjTable().setModel((javax.swing.table.TableModel) notification.getData());
            }
        }

        if (update instanceof Error) {
            Error error = (Error) update;
            MainFrame.getInstance().showError((Error) update);
        }
    }

    public void load() {
        readDataFromTable(s);
        loadResource();
    }

    public void loadResource() {

        database.loadResource();
        InformationResource ir = (InformationResource) this.database.loadResource();
        database.notifySubscribers(new Notification(NotificatonCode.RESOURCE_LOADED, ir));
    }

    public void readDataFromTable(String fromTable) {
        tableModel.setRows(database.readDataFromTable(fromTable));
        database.notifySubscribers(new Notification(NotificatonCode.DATA_UPDATED, tableModel));
    }

    public TableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(TableModel tableModel) {
        this.tableModel = tableModel;
    }
}
