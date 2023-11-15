package application.orowan_3;

//This class is used to create a new data base and store input and output values given by the orowan.exe

import java.sql.*;
import java.util.ArrayList;

public class DataBase {

    public static DataBase instance;

    int id = 0;
    public Connection connect;

    // The thing is that we want this object (instance) to be unique, and called only once => We transform it into a Singleton
    // To call it we must now go through #getInstance().
    public DataBase() throws SQLException {
    // Here we open the connection to the database, it takes a bit long to connect each time so it's better to do it only once
        // And we will reuse the connection each time.
        this.connect = DriverManager.getConnection("jdbc:h2:~/test" + "sa" + ";AUTO_SERVER=TRUE");
    }

    //Create the databases
    public void createDataFrame() {
        try {
            Statement stat = connect.createStatement();
            stat.executeUpdate("DROP TABLE IF EXISTS INPUT;");
            stat.executeUpdate("CREATE TABLE INPUT (\r\n"
                    + "    id INT PRIMARY KEY NOT NULL,\r\n"
                    + "    Cas INT,\r\n" //pk??
                    + "    He FLOAT(6),\r\n"
                    + "    Hs FLOAT(6),\r\n"
                    + "    Te FLOAT(6),\r\n"
                    + "    Ts FLOAT(6),\r\n"
                    + "    Diam_WR FLOAT(6),\r\n"
                    + "    WRyoung FLOAT(6),\r\n"
                    + "    offset_ini FLOAT(6),\r\n"
                    + "    mu_ini FLOAT(6),\r\n"
                    + "    Force FLOAT(6),\r\n"
                    + "    G FLOAT(6)\r\n)");

            stat.executeUpdate("DROP TABLE IF EXISTS Output;");
            stat.executeUpdate("CREATE TABLE Output (\r\n"
                    + "    id INT PRIMARY KEY NOT NULL,\r\n"
                    + "    coeff FLOAT(6)\r\n)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Add the entry values given by "orowan" in the data base "INPUT"
    public void storeInput(String cas, String he, String hs, String te, String ts, String diamWR, String youngWR, String offset, String mu, String f, String fs) throws SQLException {
        Statement stat = connect.createStatement();
        String strFormat = "VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s);%n";
        String str;
        str = String.format(strFormat, id, cas, he, hs, te, ts, diamWR, youngWR, offset, mu, f, fs);
        id++;
        stat.executeUpdate("INSERT INTO INPUT (ID, CAS, HE, HS, TE, TS, DIAM_WR, WRYOUNG, OFFSET_INI, MU_INI, FORCE, G)\r\n"
                + str);
    }

    //Add the exit values given by "orowan" in the data base "OUTPUT"
    public void storeOutput(Float coef) throws SQLException {
        Statement stat = connect.createStatement();
        String strFormat = "VALUES (%s, %s);%n";
        String str;
        str = String.format(strFormat, id, coef);
        id++; // ??
        System.out.println(str);
        stat.executeUpdate("INSERT INTO Output (ID, COEFF)\r\n" //??
                + str);
    }

    //Write the sql queries for the data base "output"
    public ArrayList<String> sqlQuery(String query) throws SQLException {
        Statement stat = connect.createStatement();
        ResultSet resultTable = stat.executeQuery(query);
        ArrayList<String> result = new ArrayList<String>();
        while (resultTable.next()) {
            result.add(String.valueOf(resultTable.getInt("id")));
            result.add(":");
            result.add(String.valueOf(resultTable.getFloat("Coeff")));
            result.add("   ");
        }
        return result;
    }

    //Write the sql queries in the data base "INPUT"
    public ArrayList<String> sqlQueryInput(String query) throws SQLException {
        Statement stat = connect.createStatement();
        ResultSet resultTable = stat.executeQuery(query);
        ArrayList<String> result = new ArrayList<String>();
        while (resultTable.next()) {
            result.add(String.valueOf(resultTable.getInt("id")));
            result.add(":");
            result.add(String.valueOf(resultTable.getFloat("CAS")));
            result.add(String.valueOf(resultTable.getFloat("HE")));
            result.add(String.valueOf(resultTable.getFloat("HS")));
            result.add(String.valueOf(resultTable.getFloat("TE")));
            result.add(String.valueOf(resultTable.getFloat("TS")));
            result.add(String.valueOf(resultTable.getFloat("DIAM_WR")));
            result.add(String.valueOf(resultTable.getFloat("WRYOUNG")));
            result.add(String.valueOf(resultTable.getFloat("OFFSET_INI")));
            result.add(String.valueOf(resultTable.getFloat("MU_INI")));
            result.add(String.valueOf(resultTable.getFloat("FORCE")));
            result.add(String.valueOf(resultTable.getFloat("G")));
            result.add("   ");
        }
        return result;

    }

    //Write other sql queries
    public ArrayList<String> otherSqlQueries(String query) throws SQLException {
        Statement stat = connect.createStatement();
        ResultSet resultTable = stat.executeQuery(query);
        ArrayList<String> result = new ArrayList<String>();
        while (resultTable.next()) {
            result.add("Executed query");
        }
        return result;
    }

    public static DataBase getInstance() throws SQLException {
        if (instance == null)
            instance = new DataBase();
        return instance;
    }
}