package src.core;

import src.observer.Publisher;
import src.repository.resource.DBNode;
import src.repository.resource.data.Row;

import java.util.List;

public interface Database extends Publisher {

    DBNode loadResource();

    List<Row> readDataFromTable(String tableName);
}

