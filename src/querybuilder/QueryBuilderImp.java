package src.querybuilder;

import src.core.Database;
import src.core.QueryBuilder;
import src.querybuilder.mssqlBuilder.StringParser;

public class QueryBuilderImp implements QueryBuilder {

    Database database;
    String s;
    StringParser stringParser;

    public QueryBuilderImp(Database database) {
        this.database = database;
    }

    @Override
    public String start(String s) {
        this.s = s;
        return validate(s);
    }

    @Override
    public String validate(String s) {
            return compile(s);
    }

    @Override
    public String compile(String s) {
        stringParser = new StringParser(s);
        return stringParser.generate(database);
    }

    public void setS(String s) {
        this.s = s;
    }
}
