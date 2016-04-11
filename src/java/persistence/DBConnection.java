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

    public ResultSet selectAllFrom(String from) throws SQLException {
        st = db.createStatement();
        String query = "SELECT * FROM " + schema + "." + from;
        ResultSet rs = st.executeQuery(query);
        return rs;
    }

    public ResultSet selectAllFromWhere(ResultSet rs, String table, String condition) throws SQLException{
        st = db.createStatement();
        String query = "SELECT * FROM " + schema + "." + table + " WHERE " + condition;
        rs = st.executeQuery(query);
        return rs;
    }

    public void insertValue(String table, String values) throws SQLException {
        st = db.createStatement();

        String insert = "INSERT INTO " + schema + "." + table + " VALUES (" + values + ");";
        st.executeUpdate(insert);
        st.close();
    }
    
    public String selectIDFromWhereEquals(String table, String column, String attribute) throws SQLException {
        st = db.createStatement();
        String id = null;
        String condition = column + "=" + attribute;
        String query = "SELECT " + table + "_id FROM " + schema + "." + table + " WHERE " + condition + ";";
        ResultSet rs = st.executeQuery(query);
        if (rs.next()){
            id = rs.getString(1);
        }
        rs.close();
        st.close();
        return id;
    }
    
    /**
     * Returns the id of an entry in a particular table, where the specified
     * attributes are equal to specified values.
     * @param table The table to get the entry from.
     * @param columns The array of all the names of the columns to compare.
     * @param attributes The array of values of the attributes for the specified columns.
     * @return Returns the id of the entry or null if there is no match.
     * @throws SQLException
     * @throws Exception if the arrays have different lengths.
     */
    public String selectIDFromWhereEquals(String table, String columns[], String attributes[]) throws SQLException, Exception{
        st = db.createStatement();
        String id = null;
        String conditions = "";
        if (columns.length!=attributes.length) throw new Exception();
        for (int i = 0; i < columns.length; i++){
            if (i == columns.length-1){
                conditions += table + "." + columns[i] + "=" + attributes[i];
            } else {
                conditions += table + "." + columns[i] + "=" + attributes[i] + " AND "; 
            }
        }
        String query = "SELECT " + table + "_id FROM " + schema + "." + table + " WHERE " + conditions + ";";
        ResultSet rs = st.executeQuery(query);
        if (rs.next()){
            id = rs.getString(1);
        }
        rs.close();
        st.close();
        return id;
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
    
    public ResultSet doQuery(String query) throws SQLException{
        st = db.createStatement();
        ResultSet rs = st.executeQuery(query);
        return rs;
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
