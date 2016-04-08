/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;

/**
 *
 * @author karensantos
 */
public class DBHelper {
    
    private final int FIVE_CHARAC = 5;
    
    private DBConnection connection;
    
    public DBHelper() {
        try {
            connection = new DBConnection();
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found error");
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println("SQLException error");
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<UserAccount> selectAllUsers() throws SQLException{
        List<UserAccount> users = new ArrayList<>();
        ResultSet rs = null;
        connection.selectAllFrom(rs, "User");
        
        while (rs.next()) {
            UserAccount u = new UserAccount(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
            users.add(u);
        }
        rs.close();
        return users;
    }
    
    public UserAccount selectUserByEmail(String email) throws SQLException{
        ResultSet rs = null;
        rs = connection.selectAllFromWhere(rs, "UserAccount", "email= '" + email + "'");
        
        UserAccount user = null;
        if (rs.next()){
            String id = rs.getString(1);
            String password = rs.getString(2);
            String lastName = rs.getString(3);
            String firstName = rs.getString(4);
            String city = rs.getString(6);
            String province = rs.getString(7);
            String country = rs.getString(8);
            user = new UserAccount(id, password, lastName, firstName, email, city, province, country);
        } 
        rs.close();
        connection.closeStatement();
        return user;
    }
    
    public void insertUser(UserAccount user) throws SQLException{

        int totalrows = getTotalRows("UserAccount");
        
        String id = "'" + createID(FIVE_CHARAC, totalrows) + "',";
        String password = "'" + user.getPassword() +"',";
        String lastName = "'" + user.getLastName() +"',";
        String firstName = "'" + user.getFirstName() +"',";
        String email = "'" + user.getEmail() + "',";
        String city = "'" + user.getCity() + "',";
        String province = "'" + user.getProvince() + "',";
        String country =  "'" + user.getCountry() + "'";
        
        String userInfo = id + password + lastName + firstName + email + city + province + country;
        connection.insertValue("UserAccount", userInfo);
    }
    
    public List<Movie> selectAllMovies() throws SQLException{
        List<Movie> movies = new ArrayList<>();
        ResultSet rs = null;
        connection.selectAllFrom(rs, "Movie");
        
        while (rs.next()) {
            Movie m = new Movie(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
                    rs.getBoolean(5), rs.getString(6), rs.getInt(7), rs.getString(8));
            movies.add(m);
        }
        rs.close();
        return movies;
    }
    
    public void insertMovie(Movie movie) throws SQLException{
        int totalrows = getTotalRows("Movie");
        
        String id = "'" + createID(FIVE_CHARAC, totalrows) + "',";
        String name = "'" + movie.getName() +"',";
        String date = "'" + movie.getDateReleased() + "',";
        String language = "'" + movie.getLanguage() + "',";
        String country =  ",'" + movie.getCountry() + "',";
        String directorID = ",'" + movie.getDirectorID() + "'";
        
        String movieInfo = id + name + date + language + movie.isSubtitled() 
                + country + movie.getAgeRestriction() + directorID;
        connection.insertValue("Movie", movieInfo);
    }
    
    public int getTotalRows(String table) throws SQLException{
        return connection.getTotalRows(table);
    }
    
    private String createID(int numberOfCharacters, int totalEntries){
        String id = "";
        String idNumber = "" + (totalEntries + 1);
        int numberOfZeros = numberOfCharacters - (idNumber.length());
        for (int i = 0; i < numberOfZeros; i++){
            id += "0";
        }
        id += idNumber;
        return id;
    }
    
}
