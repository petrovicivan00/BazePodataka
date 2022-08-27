package src.repository.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import src.core.Database;
import src.core.Repository;
import src.observer.implementation.PublisherImp;
import src.repository.resource.DBNode;
import src.repository.resource.data.Row;

import java.util.List;

@Data
@AllArgsConstructor
public class DatabaseImp extends PublisherImp implements Database {

    private Repository repository;

    @Override
    public DBNode loadResource() {
        return repository.getSchema();
    }

    @Override
    public List<Row> readDataFromTable(String tableName) {
        return repository.get(tableName);
    }
}