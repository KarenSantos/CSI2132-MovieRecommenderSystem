/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.Serializable;
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
public class DBHelper implements Serializable {

    private final int FIVE_CHARAC = 5;
    private final int THREE_CHARAC = 3;

    private DBConnection connection;

    public DBHelper() throws ClassNotFoundException, SQLException {
        connection = new DBConnection();
    }

    public List<UserAccount> selectAllUsers() throws SQLException {
        List<UserAccount> users = new ArrayList<>();
        ResultSet rs = connection.selectAllFrom("user_account");

        while (rs.next()) {
            UserAccount u = new UserAccount(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                    rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
            users.add(u);
        }
        rs.close();
        connection.closeStatement();
        return users;
    }
    
    public UserAccount selectUserByID(String userID) throws SQLException {
        ResultSet rs = null;
        rs = connection.selectAllFromWhere(rs, "user_account", "user_id = '" + userID + "'");

        UserAccount user = null;
        if (rs.next()) {
            String password = rs.getString(2);
            String lastName = rs.getString(3);
            String firstName = rs.getString(4);
            String email = rs.getString(5);
            String city = rs.getString(6);
            String province = rs.getString(7);
            String country = rs.getString(8);
            user = new UserAccount(userID, password, lastName, firstName, email, city, province, country);
        }
        rs.close();
        connection.closeStatement();
        return user;
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

    public String insertUserAccount(UserAccount user) throws SQLException {

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

    public List<Movie> selectAllMovies() throws SQLException, Exception {
        List<Movie> movies = new ArrayList<>();
        ResultSet rs = connection.selectAllFrom("movie");
        while (rs.next()) {
            Movie m = new Movie(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                    rs.getBoolean(5), rs.getString(6), rs.getInt(7), rs.getString(8));
            movies.add(m);
        }
        rs.close();
        connection.closeStatement();
        return movies;
    }
    
    public Movie selectMovieByID(String movieID) throws Exception{
        ResultSet rs = null;
        rs = connection.selectAllFromWhere(rs, "movie", "movie_id = '" + movieID + "'");

        Movie movie = null;
        if (rs.next()) {
            String name = rs.getString(2);
            String dateReleased = rs.getString(3);
            String language = rs.getString(4);
            boolean subtitles = rs.getBoolean(5);
            String country = rs.getString(6);
            int ageRestriction = rs.getInt(7);
            String directorID = rs.getString(8);
            movie = new Movie(movieID, name, dateReleased, language, subtitles, country, ageRestriction, directorID);
        }
        rs.close();
        connection.closeStatement();
        return movie;
    }

    public String insertMovie(Movie movie) throws SQLException {
        int totalrows = getTotalRows("movie");
        String id = createID(FIVE_CHARAC, totalrows);

        String movieID = "'" + id + "',";
        String name = "'" + movie.getName() + "',";
        String date = "'" + movie.getDateReleased() + "',";
        String language = "'" + movie.getLanguage() + "',";
        String country = ",'" + movie.getCountry() + "',";
        String directorID = ",'" + movie.getDirectorID() + "'";

        String movieInfo = movieID + name + date + language + movie.isSubtitled()
                + country + movie.getAgeRestriction() + directorID;
        connection.insertValue("movie", movieInfo);
        return id;
    }

    public void insertMovieGenre(String movieID, String genreID) throws SQLException {
        String movieGenreInfo = "'" + movieID + "','" + genreID + "'";
        connection.insertValue("movie_genre", movieGenreInfo);
    }

    public void insertMovieRole(String movieID, String roleID) throws SQLException {
        String movieRoleInfo = "'" + movieID + "','" + roleID + "'";
        connection.insertValue("movie_role", movieRoleInfo);
    }

    public void insertMovieStar(String movieID, String starID) throws SQLException {
        String movieStarInfo = "'" + movieID + "','" + starID + "'";
        connection.insertValue("movie_star", movieStarInfo);
    }

    public void insertMovieStudio(String movieID, String studioID) throws SQLException {
        String movieStudioInfo = "'" + movieID + "','" + studioID + "'";
        connection.insertValue("movie_studio", movieStudioInfo);
    }

    public List<Director> selectAllDirectors() throws SQLException {
        List<Director> directors = new ArrayList<>();
        ResultSet rs = connection.selectAllFrom("director");

        while (rs.next()) {
            Director d = new Director(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
            directors.add(d);
        }
        rs.close();
        connection.closeStatement();
        return directors;
    }
    
    public Director selectDirectorByID(String directorID) throws SQLException {
        ResultSet rs = null;
        rs = connection.selectAllFromWhere(rs, "director", "director_id = '" + directorID + "'");

        Director director = null;
        if (rs.next()) {
            String lastName = rs.getString(2);
            String firstName = rs.getString(3);
            String country = rs.getString(4);
            director = new Director(directorID, lastName, firstName, country);
        }
        rs.close();
        connection.closeStatement();
        return director;
    }

    public String insertDirector(Director dir) throws SQLException {
        int totalrows = getTotalRows("director");
        String id = createID(THREE_CHARAC, totalrows);

        String directorID = "'" + id + "',";
        String lastName = "'" + dir.getLastName() + "',";
        String firstName = "'" + dir.getFirstName() + "',";
        String country = "'" + dir.getCountry() + "'";

        String dirInfo = directorID + lastName + firstName + country;
        connection.insertValue("director", dirInfo);
        return id;
    }

    public List<Star> selectAllStars() throws SQLException, Exception {
        List<Star> stars = new ArrayList<>();
        ResultSet rs = connection.selectAllFrom("star");

        while (rs.next()) {
            Star s = new Star(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            stars.add(s);
        }
        rs.close();
        connection.closeStatement();
        return stars;
    }

    public Star selectStarByID(String starID) throws Exception{
        ResultSet rs = null;
        rs = connection.selectAllFromWhere(rs, "star", "star_id = '" + starID + "'");

        Star star = null;
        if (rs.next()) {
            String lastName = rs.getString(2);
            String firstName = rs.getString(3);
            String DBO = rs.getString(4);
            String country = rs.getString(5);
            star = new Star(starID, lastName, firstName, DBO, country);
        }
        rs.close();
        connection.closeStatement();
        return star;
    }
    
    public String insertStar(Star star) throws SQLException {
        int totalrows = getTotalRows("star");
        String id = createID(FIVE_CHARAC, totalrows);

        String starID = "'" + id + "',";
        String lastName = "'" + star.getLastName() + "',";
        String firstName = "'" + star.getFirstName() + "',";
        String DBO = "'" + star.getDBO() + "',";
        String country = "'" + star.getCountry() + "'";

        String starInfo = starID + lastName + firstName + DBO + country;
        connection.insertValue("star", starInfo);
        return id;
    }

    public List<Role> selectAllRoles() throws SQLException {
        List<Role> roles = new ArrayList<>();
        ResultSet rs = connection.selectAllFrom("role");

        while (rs.next()) {
            Role r = new Role(rs.getString(1), rs.getString(2));
            roles.add(r);
        }
        rs.close();
        connection.closeStatement();
        return roles;
    }

    public String insertRole(Role role) throws SQLException {
        int totalrows = getTotalRows("role");
        String id = createID(THREE_CHARAC, totalrows);

        String roleID = "'" + id + "',";
        String name = "'" + role.getName() + "'";

        String roleInfo = roleID + name;
        connection.insertValue("role", roleInfo);
        return id;
    }

    public List<Genre> selectAllGenres() throws SQLException {
        List<Genre> genres = new ArrayList<>();
        ResultSet rs = connection.selectAllFrom("genre");

        while (rs.next()) {
            Genre g = new Genre(rs.getString(1), rs.getString(2));
            genres.add(g);
        }
        rs.close();
        connection.closeStatement();
        return genres;
    }

    public String insertGenre(Genre genre) throws SQLException {
        int totalrows = getTotalRows("genre");
        String id = createID(THREE_CHARAC, totalrows);

        String genreID = "'" + id + "',";
        String name = "'" + genre.getName() + "'";

        String genreInfo = genreID + name;
        connection.insertValue("genre", genreInfo);
        return id;
    }

    public List<Studio> selectAllStudios() throws SQLException {
        List<Studio> studios = new ArrayList<>();
        ResultSet rs = connection.selectAllFrom("studio");

        while (rs.next()) {
            Studio s = new Studio(rs.getString(1), rs.getString(2), rs.getString(3));
            studios.add(s);
        }
        rs.close();
        connection.closeStatement();
        return studios;
    }

    public String insertStudio(Studio studio) throws SQLException {
        int totalrows = getTotalRows("studio");
        String id = createID(THREE_CHARAC, totalrows);

        String studioID = "'" + id + "',";
        String name = "'" + studio.getName() + "',";
        String country = "'" + studio.getCountry() + "'";

        String studioInfo = studioID + name + country;
        connection.insertValue("studio", studioInfo);
        return id;
    }

    public UserProfile selectProfileByID(String id) throws SQLException, Exception {
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

    public String insertUserProfile(UserProfile profile) throws SQLException {

        String id = "'" + profile.getUserID() + "',";
        String DBO = "'" + profile.getDBO() + "',";
        String gender = "'" + profile.getGender() + "',";
        String occupation = "'" + profile.getOccupation() + "'";

        String profileInfo = id + DBO + gender + occupation;
        connection.insertValue("user_profile", profileInfo);
        return profile.getUserID();
    }

    public void insertStarRole(String starID, String roleID) throws SQLException {
        String starRoleInfo = "'" + starID + "','" + roleID + "'";
        connection.insertValue("star_role", starRoleInfo);
    }

    public void insertUserLikesGenre(String userID, String genreID) throws SQLException {
        String userGenreInfo = "'" + userID + "','" + genreID + "'";
        connection.insertValue("user_likes_genre", userGenreInfo);
    }

    public void insertUserWatchedMovie(String userID, String movieID, String date) throws SQLException {
        String userWatchedMovie = "'" + userID + "','" + movieID + "','" + date + "'";
        connection.insertValue("user_watched_movie", userWatchedMovie);
    }

    public void insertUserRatesMovie(String userID, String moveiID, int rating) throws SQLException, Exception {
        if (rating < 1 || rating > 5) {
            throw new Exception();
        }
        String ratingInfo = "'" + userID + "','" + moveiID + "'," + rating;
        connection.insertValue("user_rates_movie", ratingInfo);
    }
    
    public String selectIDFromWhereEquals(String table, String column, String attribute) throws SQLException {
        String id = connection.selectIDFromWhereEquals(table, column, attribute);
        return id;
    }

    public String selectIDFromWhereEquals(String table, String columns[], String attributes[]) throws SQLException, Exception {
        String id = connection.selectIDFromWhereEquals(table, columns, attributes);
        return id;
    }

    public int getTotalRows(String table) throws SQLException {
        return connection.getTotalRows(table);
    }
    
    public ResultSet doQuery(String query) throws SQLException {
        return connection.doQuery(query);
    }

    public void closeStatement() throws SQLException{
        connection.closeStatement();
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
