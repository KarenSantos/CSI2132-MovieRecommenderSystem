/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
    
    public void insertUser(UserAccount user) throws SQLException{

        int totalrows = connection.getTotalRows("UserAccount");
        
        String id = "'" + createID(FIVE_CHARAC, totalrows) + "',";
        String password = "'" + user.getPassword() +"',";
        String lastName = "'" + user.getLastName() +"',";
        String firstName = "'" + user.getFirstName() +"',";
        String email = "'" + user.getEmail() + "',";
        String city = "'" + user.getCity() + "',";
        String province = "'" + user.getProvince() + "',";
        String country =  "'" + user.getCountry() + "',";
        
        String userInfo = id + password + lastName + firstName + email + city + province + country;
        connection.insertValue("UserAccount", userInfo);
    }
    
    public List<Movie> selectAllMovies() throws SQLException{
        List<Movie> movies = new ArrayList<>();
        ResultSet rs = null;
        connection.selectAllFrom(rs, "Movie");
        
        while (rs.next()) {
            Calendar c = new GregorianCalendar();
            c.setTime(rs.getDate(3));
            Movie m = new Movie(rs.getString(1), rs.getString(2), c, rs.getString(4), 
                    rs.getBoolean(5), rs.getString(6), rs.getInt(7));
            movies.add(m);
        }
        rs.close();
        return movies;
    }
    
    public void insertMovie(Movie movie) throws SQLException{
        String id = "'" + createID(FIVE_CHARAC, connection.getTotalRows("Movie")) + "',";
        String name = "'" + movie.getName() +"',";
        String date = "'" + movie.getDateReleased().get(Calendar.YEAR) 
                + (movie.getDateReleased().get(Calendar.MONTH) + 1) + movie.getDateReleased().get(Calendar.DAY_OF_MONTH) + "',";
        String language = "'" + movie.getLanguage() + "',";
        String country =  ",'" + movie.getCountry() + "',";
        
        String movieInfo = id + name + date + language + movie.isSubtitled() + country + movie.getAgeRestriction();
        connection.insertValue("Movie", movieInfo);
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
