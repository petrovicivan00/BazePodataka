package src.repository;

import lombok.Data;
import src.App;
import src.core.Repository;
import src.core.Settings;
import src.errorHandler.Type;
import src.repository.resource.DBNode;
import src.repository.resource.data.Row;
import src.repository.resource.enums.AttributeType;
import src.repository.resource.implementation.Attribute;
import src.repository.resource.implementation.Entity;
import src.repository.resource.implementation.InformationResource;
import java.util.Date;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
public class MSSQLrep implements Repository {

    private Settings settings;
    private Connection connection;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");

    public MSSQLrep(Settings settings) {
        this.settings = settings;
    }

    private void initConnection() throws SQLException, ClassNotFoundException {

        String ip = (String) settings.getParameter("mssql_ip");
        String database = (String) settings.getParameter("mssql_database");
        String username = (String) settings.getParameter("mssql_username");
        String password = (String) settings.getParameter("mssql_password");
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:jtds:sqlserver://" + ip + "/" + database, username, password);
    }

    public static void printSQLException(SQLException ex) {

        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                if (ignoreSQLException(
                        ((SQLException) e).
                                getSQLState()) == false) {

                    e.printStackTrace(System.err);
                    System.err.println("SQLState: " +
                            ((SQLException) e).getSQLState());

                    System.err.println("Error Code: " +
                            ((SQLException) e).getErrorCode());

                    System.err.println("Message: " + e.getMessage());

                    Throwable t = ex.getCause();
                    while (t != null) {
                        System.out.println("Cause: " + t);
                        t = t.getCause();
                    }
                }
            }
        }
    }

    public static boolean ignoreSQLException(String sqlState) {

        if (sqlState == null) {
            System.out.println("The SQL state is not defined!");
            return false;
        }

        // X0Y32: Jar file already exists in schema
        if (sqlState.equalsIgnoreCase("X0Y32"))
            return true;

        // 42Y55: Table already exists in schema
        return sqlState.equalsIgnoreCase("42Y55");
    }

    @Override
    public List<Row> get(String from) {

        List<Row> rows = new ArrayList<>();

        try {
            this.initConnection();

            String query = from;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                Row row = new Row();
                row.setName(from);

                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {

                    int type = resultSetMetaData.getColumnType(i);

                    switch(type){
                        case Types.INTEGER:
                            row.addField(resultSetMetaData.getColumnName(i), rs.getInt(i));
                            break;
                        case Types.VARCHAR:
                           if((resultSetMetaData.getColumnName(i)).equals("hire_date")){
                                String dateString = rs.getString(i);
                                Date date = sdf1.parse(dateString);
                                String newDate = sdf.format(date);
                                row.addField(resultSetMetaData.getColumnName(i), newDate);
                            }else {
                                row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                            }
                            break;
                        case Types.CHAR:
                            row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                            break;
                        case Types.DATE:
                            row.addField(resultSetMetaData.getColumnName(i), rs.getDate(i));
                            break;
                        case Types.DOUBLE:
                            row.addField(resultSetMetaData.getColumnName(i), rs.getDouble(i));
                            break;
                        case Types.FLOAT:
                            row.addField(resultSetMetaData.getColumnName(i), rs.getFloat(i));
                            break;
                        case Types.TIMESTAMP:
                            row.addField(resultSetMetaData.getColumnName(i), sdf.format(rs.getTimestamp(i)));
                            break;
                        default:
                            row.addField(resultSetMetaData.getColumnName(i), rs.getObject(i));
                            break;
                    }
                }
                rows.add(row);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeConnection();
        }
        return rows;
    }

    private void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            connection = null;
        }
    }

    @Override
    public DBNode getSchema() {

        try {
            this.initConnection();

            DatabaseMetaData metaData = connection.getMetaData();
            InformationResource ir = new InformationResource("RAF");

            String[] tableType = {"TABLE"};
            ResultSet tables = metaData.getTables(connection.getCatalog(), null, null, tableType);

            while (tables.next()) {

                String tableName = tables.getString("TABLE_NAME");
                Entity newTable = new Entity(tableName, ir);
                ir.addChild(newTable);

                ResultSet columns = metaData.getColumns(connection.getCatalog(), null, tableName, null);

                while (columns.next()) {

                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");
                    int columnSize = Integer.parseInt(columns.getString("COLUMN_SIZE"));
                    Attribute attribute = new Attribute(columnName, newTable, AttributeType.valueOf(columnType.toUpperCase()), columnSize);
                    newTable.addChild(attribute);
                }
            }
            return ir;

        } catch (Exception e) {
            App.getErrorHandler().generateError(Type.COLUMN_NOT_FOUND);
        } finally {
            this.closeConnection();
        }

        return null;
    }

}