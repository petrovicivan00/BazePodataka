package src.querybuilder.mssqlBuilder;

import src.core.Database;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class StringParser {

    String s;
    String sql = "";
    ArrayList<String> params = new ArrayList<>();
    ArrayList<String> priority = new ArrayList<>();

    public StringParser(String s) {
        this.s = s;
    }

    //var query = new Query("departments").Where("department_id",">",50).AndWhere("department_id","<",100)
    //var query = new Query("departments").Where("department_id",">",50).OrderByDesc("manager_id")
    //var query = new Query("departments").Where("department_id",">",50).AndWhere("department_id","<",100).GroupBy("department_name")
    //var query = new Query("Departments").OrderBy("manager_id").Where("department_id",">",50).OrWhere("department_name","like","C%")
    //var query = new Query("jobs").Join("jobs").On("jobs.job_id","=","employees.job_id")
    //var query = new Query("jobs").Select("job_title").GroupBy("job_title").Join("jobs").On("jobs.job_id","=","employees.job_id")
    //var b = new Query("jobs").Select("job_title","job_id")
    //var a = new Query("employees").Join("departments").On("employees.department_id","=","departments.department_id")
    //var b = new Query("employees").WhereEndsWith("last_name","g").AndWhere("salary",">","10000")
    // var a = new Query("employees").WhereIn("last_name").ParametarList("King","Ernst")
    //var b = new Query("jobs").Select("job_title","prosecnaPlata").Avg("min_salary","prosecnaPlata").GroupBy("job_title")
    //var b = new Query("jobs").Select("job_title").Avg("min_salary").GroupBy("job_title").Having("prosecnaPlata","<",1500)
    //select max(salary), department_id, job_id from hr.employees where department_id > 50
    //group by department_id, job_id having max(salary)>11500 order by department_id
    // var b = new Query("employees").Select("department_id","job_id").Max("salary").Where("department_id",">",50).GroupBy("department_id","job_id").Having("salary",">",11500).AndHaving("salary","<",20000)

    public String generate(Database database){

        makePriority();
        String query = "";
        String tableName[] = new String[100];
        Validator val = new Validator();
        Compiler comp = new Compiler();


        if (val.isQueryOK(s)) {
            String parts[] = s.split(" ");
            query = parts[4];
            tableName = parts[4].split("\"");


            if (s.contains(").")) {

                String queryParts[] = query.split("\\).");

                for (int i = 0; i < priority.size(); i++) {

                    for (String str : queryParts) {
                        str += ")";

                            String funName[] = str.split("\\(");

                            if (funName[0].equalsIgnoreCase(priority.get(i))) {

                                if (str.contains(",")) {
                                    String parameters[] = str.split(",");
                                    System.out.println("Parametri korisnik : " + parameters);

                                    if (val.isParametersOK(funName[0], parameters)) {
                                        for (String s : parameters) {

                                            if (s.contains(")")) {
                                                String s1[] = s.split("\\)");
                                                s = s1[0];
                                            }

                                            if (s.contains("\"")) {
                                                String paramParts[] = s.split("\"");

                                                if (paramParts[1].contains("%") || funName[0].equalsIgnoreCase("ParametarList")) {
                                                    String temp = "'" + paramParts[1] + "'";
                                                    params.add(temp);
                                                } else {
                                                    params.add(paramParts[1]);
                                                }
                                            } else {
                                                if(s.contains("(")) {
                                                    String paramParts[] = s.split("\\(");
                                                    params.add(paramParts[1]);
                                                }else {
                                                    params.add(s);
                                                }
                                            }
                                        }
                                    }
                                } else {

                                    if(str.contains("\"")) {

                                        String parameters[] = str.split("\"");

                                        String[] p = new String[]{parameters[1]};

                                        if (val.isParametersOK(funName[0], p))
                                            params.add(parameters[1]);
                                    }else{
                                        String parameters[] = str.split("\\(");
                                        String a[] = parameters[1].split("\\)");
                                        String p[] = new String[]{a[0]};
                                        if(val.isParametersOK(funName[0], p))
                                            params.add(a[0]);
                                    }
                                }

                                if (val.isValid()) {
                                    comp = new Compiler(funName[0], params);
                                    System.out.println("Ime funkcije : " + funName[0]);
                                    //System.out.println("Parametri : " + params.toString());
                                    if(funName[0].equalsIgnoreCase("Avg") ||
                                            funName[0].equalsIgnoreCase("Count") ||
                                            funName[0].equalsIgnoreCase("Min") ||
                                            funName[0].equalsIgnoreCase("Max")){

                                        if(params.size() > 1) sql = comp.getResult();
                                        else sql += comp.getResult();

                                    } else {
                                        sql += comp.getResult();
                                    }
                                    System.out.println("SQL REZULTAT : " + sql);
                                    comp.eraseParams();
                                    //System.out.println(sql);
                                } else {
                                    sql = comp.getDef();
                                    return sql;
                                }
                            }

                    }
                }
            } else {
                String funName[] = query.split("\\(");

                String[] parameters;

                if(!(tableName[1].equals("")) && !(tableName[1].isEmpty()) && !(tableName[1].equals(null))) {
                    parameters = new String[]{tableName[1]};
                }else{
                    parameters = new String[10];
                }

                if (val.isParametersOK(funName[0], parameters)) {
                        params.add(tableName[1]);
                }

                if(val.isValid()) {
                    comp = new Compiler(funName[0], params);
                    sql = comp.getResult();
                    comp.eraseParams();
                }else{
                    sql = comp.getDef();
                    return sql;
                }
            }
        }else{
            sql = comp.getDef();
            return sql;
        }
        return sql;
    }

    public void makePriority() {
        priority.add("Select");
        priority.add("Avg");
        priority.add("Count");
        priority.add("Min");
        priority.add("Max");
        priority.add("Query");
        priority.add("Join");
        priority.add("On");
        priority.add("Where");
        priority.add("WhereEndsWith");
        priority.add("WhereStartsWith");
        priority.add("WhereContains");
        priority.add("AndWhere");
        priority.add("OrWhere");
        priority.add("WhereBetween");
        priority.add("WhereIn");
        priority.add("ParametarList");
        priority.add("GroupBy");
        priority.add("Having");
        priority.add("AndHaving");
        priority.add("OrHaving");
        priority.add("OrderBy");
        priority.add("OrderByDesc");
    }

}


