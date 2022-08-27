package src.querybuilder.mssqlBuilder;

import src.App;
import src.errorHandler.Type;
import java.util.HashMap;
import java.util.Map;

public class Validator {

    private final Map<String,Integer> num = new HashMap<>();
    private String name = "";
    private String params[] = new String[10];
    private boolean valid = true;

    public Validator() {
        numMap();
    }

    public boolean isParametersOK(String name,String[] params){
       this.name = name;
       this.params = params;

        for (String s : params) {
            if (s.isEmpty() || s.equals("") || s.equals(null)) {
                App.getErrorHandler().generateError(Type.WRONG_PARAMETERS);
                valid = false;
                return false;
            }
        }

        if (num.containsKey(name)){
            if (params.length == num.get(name)){
                return true;
            }else if(num.get(name) == 0){
                return true;
            }else{
                // wrong parameters
                App.getErrorHandler().generateError(Type.WRONG_PARAMETERS);
                valid = false;
                return false;
            }
        }else{
            // wrong function name
            App.getErrorHandler().generateError(Type.WRONG_FUNCTION_NAME);
            valid = false;
            return false;
        }


    }

    public boolean isQueryOK(String s) {

        if (!s.contains("var ") || !s.contains(" = new ") || s.isEmpty()) {
            App.getErrorHandler().generateError(Type.INVALID_QUERY);
            valid = false;
            return false;
        }

        if(!s.contains("\"")){
            App.getErrorHandler().generateError(Type.INVALID_QUERY);
            valid = false;
            return false;
        }

        if (s.contains("Join")) {
            if (!s.contains("On")) {
                App.getErrorHandler().generateError(Type.INVALID_QUERY);
                valid = false;
                return false;
            }
        }

        if (s.contains("WhereIn")){
            if (!s.contains("ParametarList")) {
                App.getErrorHandler().generateError(Type.INVALID_QUERY);
                valid = false;
                return false;
            }
        }

        if (s.contains("ParametarList")){
            if (!s.contains("WhereIn")) {
                App.getErrorHandler().generateError(Type.INVALID_QUERY);
                valid = false;
                return false;
            }
        }

        if (s.contains("OrHaving") || s.contains("AndHaving")){
            if (!s.contains(".Having(")) {
                App.getErrorHandler().generateError(Type.INVALID_QUERY);
                valid = false;
                return false;
            }
        }

        if (s.contains("AndWhere") || s.contains("OrWhere")){
            if (!s.contains(".Where(") && !s.contains("WhereBetween") && !s.contains("WhereIn")
                    && !s.contains("WhereStartsWith") && !s.contains("WhereEndsWith") && !s.contains("WhereContains")){

                App.getErrorHandler().generateError(Type.INVALID_QUERY);
                valid = false;
                return false;
            }
        }

        if(!s.contains("(")) {
            App.getErrorHandler().generateError(Type.SYNTAX_ERROR);
            valid = false;
            return false;
        }

        int cnt = 0;
        char[] chars = s.toCharArray();

        for (int i =0; i<chars.length; i++){
            if(chars[i]== '(') cnt++;
            if(chars[i]== ')') cnt--;
        }

        if(cnt != 0) {
            System.out.println("CNT = " + cnt);
            App.getErrorHandler().generateError(Type.SYNTAX_ERROR);
            valid = false;
            return false;
        }

        return true;
    }

    public void numMap() {

        num.put("Select", 0);
        num.put("Query",1);
        num.put("OrderBy",1);
        num.put("OrderByDesc",1);
        num.put("Where",3);
        num.put("WhereEndsWith",2);
        num.put("WhereStartsWith",2);
        num.put("WhereContains",2);
        num.put("OrWhere",3);
        num.put("AndWhere",3);
        num.put("WhereBetween",3);
        num.put("WhereIn",1);
        num.put("ParametarList",0);
        num.put("Join",1);
        num.put("On",3);
        num.put("Avg",1);
        num.put("Count",1);
        num.put("Min",1);
        num.put("Max",1);
        num.put("GroupBy",0);
        num.put("Having",3);
        num.put("AndHaving",3);
        num.put("OrHaving",3);
        num.put("WhereInQ",2);
        num.put("WhereEqQ",2);
    }

    public boolean isValid() {
        return valid;
    }
}
