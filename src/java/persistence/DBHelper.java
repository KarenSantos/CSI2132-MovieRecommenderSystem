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
    private final int THREE_CHARAC = 3;

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

    public List<UserAccount> selectAllUsers() throws SQLException {
        List<UserAccount> users = new ArrayList<>();
        ResultSet rs = null;
        connection.selectAllFrom(rs, "user_account");

        while (rs.next()) {
            UserAccount u = new UserAccount(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                    rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
            users.add(u);
        }
        rs.close();
        connection.closeStatement();
        return users;
    }

    public UserAccount selectUserByEmail(String email) throws SQLException {
        ResultSet rs = null;
        rs = connection.selectAllFromWhere(rs, "user_account", "email= '" + email + "'");

        UserAccount user = null;
        if (rs.next()) {
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

    public String insertUser(UserAccount user) throws SQLException {

        int totalrows = getTotalRows("user_account");
        String id = createID(FIVE_CHARAC, totalrows);
        
        String userID = "'" + id + "',";
        String password = "'" + user.getPassword() + "',";
        String lastName = "'" + user.getLastName() + "',";
        String firstName = "'" + user.getFirstName() + "',";
        String email = "'" + user.getEmail() + "',";
        String city = "'" + user.getCity() + "',";
        String province = "'" + user.getProvince() + "',";
        String country = "'" + user.getCountry() + "'";

        String userInfo = userID + password + lastName + firstName + email + city + province + country;
        connection.insertValue("user_account", userInfo);
        return id;
    }

    public List<Movie> selectAllMovies() throws SQLException {
        List<Movie> movies = new ArrayList<>();
        ResultSet rs = null;
        connection.selectAllFrom(rs, "movie");

        while (rs.next()) {
            Movie m = new Movie(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                    rs.getBoolean(5), rs.getString(6), rs.getInt(7), rs.getString(8));
            movies.add(m);
        }
        rs.close();
        connection.closeStatement();
        return movies;
    }

    public void insertMovie(Movie movie) throws SQLException {
        int totalrows = getTotalRows("movie");

        String id = "'" + createID(FIVE_CHARAC, totalrows) + "',";
        String name = "'" + movie.getName() + "',";
        String date = "'" + movie.getDateReleased() + "',";
        String language = "'" + movie.getLanguage() + "',";
        String country = ",'" + movie.getCountry() + "',";
        String directorID = ",'" + movie.getDirectorID() + "'";

        String movieInfo = id + name + date + language + movie.isSubtitled()
                + country + movie.getAgeRestriction() + directorID;
        connection.insertValue("movie", movieInfo);
    }

    public List<Director> selectAllDirectors() throws SQLException {
        List<Director> directors = new ArrayList<>();
        ResultSet rs = null;
        connection.selectAllFrom(rs, "director");

        while (rs.next()) {
            Director d = new Director(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
            directors.add(d);
        }
        rs.close();
        connection.closeStatement();
        return directors;
    }

    public void insertDirector(Director dir) throws SQLException {
        int totalrows = getTotalRows("director");

        String id = "'" + createID(THREE_CHARAC, totalrows) + "',";
        String lastName = "'" + dir.getLastName() + "',";
        String firstName = "'" + dir.getFirstName() + "',";
        String country = "'" + dir.getCountry() + "'";

        String dirInfo = id + lastName + firstName + country;
        connection.insertValue("director", dirInfo);
    }

    public List<Star> selectAllStars() throws SQLException {
        List<Star> stars = new ArrayList<>();
        ResultSet rs = null;
        connection.selectAllFrom(rs, "star");

        while (rs.next()) {
            Star s = new Star(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            stars.add(s);
        }
        rs.close();
        connection.closeStatement();
        return stars;
    }

    public void insertStar(Star star) throws SQLException {
        int totalrows = getTotalRows("star");

        String id = "'" + createID(FIVE_CHARAC, totalrows) + "',";
        String lastName = "'" + star.getLastName() + "',";
        String firstName = "'" + star.getFirstName() + "',";
        String DBO = "'" + star.getDBO() + "',";
        String country = "'" + star.getCountry() + "'";

        String starInfo = id + lastName + firstName + DBO + country;
        connection.insertValue("star", starInfo);
    }

    public List<Role> selectAllRoles() throws SQLException {
        List<Role> roles = new ArrayList<>();
        ResultSet rs = null;
        connection.selectAllFrom(rs, "role");

        while (rs.next()) {
            Role r = new Role(rs.getString(1), rs.getString(2));
            roles.add(r);
        }
        rs.close();
        connection.closeStatement();
        return roles;
    }

    public void insertRole(Role role) throws SQLException {
        int totalrows = getTotalRows("role");

        String id = "'" + createID(THREE_CHARAC, totalrows) + "',";
        String name = "'" + role.getName() + "'";

        String roleInfo = id + name;
        connection.insertValue("role", roleInfo);
    }

    public List<Genre> selectAllGenres() throws SQLException {
        List<Genre> genres = new ArrayList<>();
        ResultSet rs = null;
        connection.selectAllFrom(rs, "genre");

        while (rs.next()) {
            Genre g = new Genre(rs.getString(1), rs.getString(2));
            genres.add(g);
        }
        rs.close();
        connection.closeStatement();
        return genres;
    }

    public void insertGenre(Genre genre) throws SQLException {
        int totalrows = getTotalRows("genre");

        String id = "'" + createID(THREE_CHARAC, totalrows) + "',";
        String name = "'" + genre.getName() + "'";

        String genreInfo = id + name;
        connection.insertValue("genre", genreInfo);
    }

    public List<Studio> selectAllStudios() throws SQLException {
        List<Studio> studios = new ArrayList<>();
        ResultSet rs = null;
        connection.selectAllFrom(rs, "studio");

        while (rs.next()) {
            Studio s = new Studio(rs.getString(1), rs.getString(2), rs.getString(3));
            studios.add(s);
        }
        rs.close();
        connection.closeStatement();
        return studios;
    }

    public void insertStudio(Studio studio) throws SQLException {
        int totalrows = getTotalRows("studio");

        String id = "'" + createID(THREE_CHARAC, totalrows) + "',";
        String name = "'" + studio.getName() + "',";
        String country = "'" + studio.getCountry() + "'";

        String studioInfo = id + name + country;
        connection.insertValue("studio", studioInfo);
    }

    public UserProfile selectProfileByID(String id) throws SQLException {
        ResultSet rs = null;
        rs = connection.selectAllFromWhere(rs, "user_profile", "user_id= '" + id + "'");

        UserProfile profile = null;
        if (rs.next()) {
            String DBO = rs.getString(1);
            String gender = rs.getString(2);
            String occupation = rs.getString(3);
            profile = new UserProfile(id, DBO, gender, occupation);
        }
        rs.close();
        connection.closeStatement();
        return profile;
    }

    public void insertProfile(UserProfile profile) throws SQLException {

        String id = "'" + profile.getUserID() + "',";
        String DBO = "'" + profile.getDBO() + "',";
        String gender = "'" + profile.getGender() + "',";
        String occupation = "'" + profile.getOccupation() + "'";

        String profileInfo = id + DBO + gender + occupation;
        connection.insertValue("user_profile", profileInfo);
    }

    public String selectIDFromWhereEquals(String table, String columns[], String attributes[]) throws SQLException, Exception {
        String id = connection.selectIDFromWhereEquals(table, columns, attributes);
        return id;
    }

    public int getTotalRows(String table) throws SQLException {
        return connection.getTotalRows(table);
    }

    private String createID(int numberOfCharacters, int totalEntries) {
        String id = "";
        String idNumber = "" + (totalEntries + 1);
        int numberOfZeros = numberOfCharacters - (idNumber.length());
        for (int i = 0; i < numberOfZeros; i++) {
            id += "0";
        }
        id += idNumber;
        return id;
    }

}
