package src.querybuilder.mssqlBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Compiler {

    private String name;
    private ArrayList<String> parameters;
    private final Map<String,String> functionsNames = new HashMap<>();
    private String result = "";
    static boolean flag = false;
    private String def = "SELECT * FROM employees";
    private static String joinon;
    private static String select;
    private static String alias;
    private static String aliasPrev;

    //mapa <korisnikov jezik, sql>
    //find kj in map, take second

    public Compiler(){

    }


    public Compiler(String name, ArrayList<String> parameters) {
        this.name = name;
        this.parameters = parameters;
        makeMap();
        makeQuery();
    }

    public void makeQuery() {
        String f = functionsNames.get(name);

        if (name.equalsIgnoreCase("Select")) {

            if (!flag) flag = true;
            result += f + addToQueryWithComma(parameters);
            select = f + addToQueryWithComma(parameters);
            // select = job_title, prosecnaPlata
            System.out.println("SELECT , RESULT : " + result);

        } else if (name.equalsIgnoreCase("Query")) {

            if (flag) {
                    result += "FROM " + addToQuery(parameters);
                    flag = false;
            } else {
                result += f + addToQuery(parameters);
            }

            System.out.println("QUERY , RESULT : " + result);
        } else if (name.equalsIgnoreCase("WhereBetween")) {
            result = "WHERE " + parameters.get(0) + " BETWEEN " + parameters.get(1) + " AND " + parameters.get(2);

        }else if (name.equalsIgnoreCase("WhereStartsWith")) {
            result = "WHERE " + parameters.get(0) + " LIKE '" + parameters.get(1) + "%'";

        }else if (name.equalsIgnoreCase("WhereEndsWith")) {
            result = "WHERE " + parameters.get(0) + " LIKE '%" + parameters.get(1) + "'";

        }else if (name.equalsIgnoreCase("WhereContains")) {
            result = "WHERE " + parameters.get(0) + " LIKE '%" + parameters.get(1) + "%'";

        }else if(name.equalsIgnoreCase("Join")){

            joinon = f + parameters.get(0) + " ";

        }else if(name.equalsIgnoreCase("On")){

            result += joinon + f + "( " + addToQuery(parameters) + ") ";

        }else if(name.equalsIgnoreCase("ParametarList")){

            result += f + addToQueryWithComma(parameters) + ") ";

        }else if(name.equalsIgnoreCase("Avg") || name.equalsIgnoreCase("Count") ||
                name.equalsIgnoreCase("Min") || name.equalsIgnoreCase("Max")){

            alias = name.toLowerCase();

            if(parameters.size() > 1) {
                String parts[] = select.split(parameters.get(1));
                result = "";
                result = parts[0] + " " + f + parameters.get(0) + ") AS " + parameters.get(1) + " ";
            }else{
                result += ", " +  f + parameters.get(0) + ") ";
            }
            System.out.println("ALIAS , RESULT : " + result);

        }else if(name.equalsIgnoreCase("Having")){

            result += f + alias + "(" + parameters.get(0) + ") " + parameters.get(1) + " " + parameters.get(2) + " ";

            aliasPrev = alias;
            alias = "";

        }else if(name.equalsIgnoreCase("AndHaving")){

            result += f + aliasPrev + "(" + parameters.get(0) + ") " + parameters.get(1) + " " + parameters.get(2) + " ";

            aliasPrev = "";

        }else if(name.equalsIgnoreCase("OrHaving")){

            result += f + aliasPrev + "(" + parameters.get(0) + ") " + parameters.get(1) + " " + parameters.get(2) + " ";

            aliasPrev = "";

        }else if(name.equalsIgnoreCase("GroupBy")){

            result += f + addToQueryWithComma(parameters);

        }else{
                result += f + addToQuery(parameters);

                if (name.equalsIgnoreCase("OrderByDesc"))
                    result += "DESC ";

        }
    }

    public String addToQuery(ArrayList<String> parameters){
        String ret = "";
        for (String s : parameters) {
            ret += s;
            ret += " ";
        }
        return ret;
    }

    public String addToQueryWithComma(ArrayList<String> parameters){
        String ret = "";
        for (int i = 0; i < parameters.size(); i++) {
            ret += parameters.get(i);
            if (i == parameters.size() - 1) ret += " ";
            else ret += ", ";
        }
        return ret;
    }

    public void makeMap(){
        functionsNames.put("Select" , "SELECT "); // select param1 as param2
        functionsNames.put("Query" , "SELECT * FROM ");
        functionsNames.put("OrderBy","ORDER BY ");
        functionsNames.put("OrderByDesc", "ORDER BY ");
        functionsNames.put("Where", "WHERE ");
        functionsNames.put("OrWhere", "OR ");
        functionsNames.put("AndWhere", "AND ");
        functionsNames.put("WhereBetween", "BETWEEN ");
        functionsNames.put("WhereIn", "WHERE ");
        functionsNames.put("Join", "JOIN ");
        functionsNames.put("On", "ON ");
        functionsNames.put("WhereEndsWith", "ENDS WITH ");
        functionsNames.put("WhereStartsWith", "STARTS WITH ");
        functionsNames.put("WhereContains", "CONTAINS ");
        functionsNames.put("GroupBy", "GROUP BY ");
        functionsNames.put("Avg", "avg(");
        functionsNames.put("ParametarList", "IN ( ");
        functionsNames.put("Count", "count(");
        functionsNames.put("Min", "min(");
        functionsNames.put("Max","max(");
        functionsNames.put("Having", "HAVING ");
        functionsNames.put("AndHaving", "AND ");
        functionsNames.put("OrHaving", "OR ");
       /* functionsNames.put("WhereInQ");
        functionsNames.put("WhereEqQ");
        */

    }

   /*
   select
   query (from)
   join
   on
   where
   groupby
   having
   orderby


    */

    public String getDef() {
        return def;
    }

    public boolean getFlag() {
        return flag;
    }

    public void eraseParams(){
        parameters.clear();
    }

    public String getResult() {
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<String> parameters) {
        this.parameters = parameters;
    }
}
