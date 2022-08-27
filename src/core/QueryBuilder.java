package src.core;

import javax.management.Query;

public interface QueryBuilder {

    String start(String s);
    String validate(String s);
    String compile(String s);
}
