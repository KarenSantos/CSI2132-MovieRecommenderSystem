package persistence;

import java.sql.*;

/**
 *
 * @author karensantos
 */
public class DBConnection {

    private final String schema = "movie_recommender_system";
    private final String url = "jdbc:postgresql://web0.site.uottawa.ca:15432/kdeal089";
    private final String username = "kdeal089";
    private final String password = "";
    private Connection db;
    private Statement st;

    public DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        db = DriverManager.getConnection(url, username, password);
    }
    
    public void closeStatement() throws SQLException{
        st.close();
    }

    public ResultSet selectAllFrom(ResultSet rs, String from) throws SQLException {
        st = db.createStatement();
        String query = "SELECT * FROM " + schema + "." + from;
        rs = st.executeQuery(query);
        st.close();
        return rs;
    }

    public ResultSet selectAllFromWhere(ResultSet rs, String table, String condition) throws SQLException{
        st = db.createStatement();
        String query = "SELECT * FROM " + schema + "." + table + " WHERE " + condition;
        rs = st.executeQuery(query);
        return rs;
    }

    public ResultSet selectFromJoin(ResultSet rs, String selection, String[] from) throws SQLException {
        st = db.createStatement();

        String allFrom = schema + "." + from[0];
        for (int i = 1; i < from.length; i++) {
            allFrom += ", " + schema + "." + from[i];
        }

        String query = "SELECT " + selection + " FROM " + allFrom;

        rs = st.executeQuery(query);

//        while (rs.next()) {
//            System.out.print("Column 1:");
//            System.out.println(rs.getString(1)); // we are getting information
//            // from column 1
//            // we can also getString by the name of the attribute
//        }
//
//        System.out.println();
//        rs.close();
        return rs;

    }

    public void insertValue(String table, String values) throws SQLException {
        st = db.createStatement();

        String insert = "INSERT INTO " + schema + "." + table + " VALUES (" + values + ");";
        System.out.println(insert);
        st.executeUpdate(insert);
        st.close();
    }

    public int getTotalRows(String table) throws SQLException {
        st = db.createStatement();
        String query = "SELECT COUNT(*) FROM " + schema + "." + table;
        ResultSet rs = st.executeQuery(query);
        rs.next();
        int result = rs.getInt(1);
        rs.close();
        st.close();
        return result;
    }

//    public static void SelectF(Connection db) throws SQLException {
//        Statement st = db.createStatement();
//        ResultSet rs = st
//                .executeQuery("SELECT aname, dateofbirth FROM laboratories.artist");
//
//        System.out.println("| Name \t| Birthday |");
//
//        while (rs.next()) {
//            System.out.println("| " + rs.getString("aname") + " \t| "
//                    + rs.getString("dateofbirth"));
//        }
//        System.out.println();
//        rs.close();
//        st.close();
//    }
//
//    public static void DynamicQuery(Connection db) throws SQLException {
//        String field = "aname, style";
//        String cond = "aname";
//        String value = "Caravaggio";
//        String table = "laboratories.artist";
//
//        Statement st = db.createStatement();
//        ResultSet rs = st.executeQuery("SELECT " + field + " FROM " + table
//                + " WHERE " + cond + " = '" + value + "';");
//
//        System.out.println("Column count: " + rs.getMetaData().getColumnCount());
//        // this is the metadata of the result set rs, in this case only two columns
//    }
//
//    public static void SecondExercise(Connection db) throws SQLException {
//        String[] fields = {"AName", "Style"};
//		// we can create a string from the array to filter the query
//
//        Statement st = db.createStatement();
//        ResultSet rs = st.executeQuery("SELECT * FROM laboratories.artist");
//
//        System.out.println("| Name \t| Style |");
//
//        while (rs.next()) {
//            for (String s : fields) {
//                System.out.print("| " + rs.getString(s) + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();
//        rs.close();
//        st.close();
//    }
}
