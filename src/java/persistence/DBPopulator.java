/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;

/**
 *
 * @author karensantos
 */
public class DBPopulator {

    private static DBHelper helper;

    public static void main(String[] args) throws IOException, Exception {

        helper = new DBHelper();
        insertData();

    }

    private static void insertData() throws Exception {

        DBHelper DBHelper = new DBHelper();
        try {
            if (DBHelper.getTotalRows("Movie") == 0) {
                insertGenres();
                insertRoles();
                insertDirectors();
                insertStudios();
                insertUsersAndProfiles();
                insertStars();
                insertStarRole();
                insertUserLikesGenre();
                insertMovies();
                insertUserWatchedMovie();
                insertUserRatesMovie();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void insertGenres() {
        System.out.println("Inserting genres");
        File file = new File("web/resources/data/genres.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            int count = 0;
            while ((line = br.readLine()) != null) {
                Genre genre = new Genre();
                genre.setName(line);
                helper.insertGenre(genre);
                count++;
            }
            System.out.println(count + " genres inserted \n");
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void insertRoles() {
        System.out.println("Inserting roles");
        File file = new File("web/resources/data/roles.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            int count = 0;
            while ((line = br.readLine()) != null) {
                Role role = new Role();
                role.setName(line);
                helper.insertRole(role);
                count++;
            }
            System.out.println(count + " roles inserted \n");
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void insertDirectors() {
        System.out.println("Inserting directors");
        File file = new File("web/resources/data/directors.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            int count = 0;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("lastname:")) {
                    Director dir = new Director();
                    dir.setLastName(line.split(":")[1]);
                    line = br.readLine();
                    dir.setFirstName(line.split(":")[1]);
                    line = br.readLine();
                    dir.setCountry(line.split(":")[1]);
                    helper.insertDirector(dir);
                    count++;
                }
            }
            System.out.println(count + " directors inserted \n");
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void insertStudios() {
        System.out.println("Inserting studios");
        File file = new File("web/resources/data/studios.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] info = line.split("-");
                Studio studio = new Studio();
                studio.setName(info[0]);
                studio.setCountry(info[1]);
                helper.insertStudio(studio);
                count++;
            }
            System.out.println(count + " studios inserted \n");
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void insertUsersAndProfiles() throws Exception {
        System.out.println("Inserting users and profiles");
        File file = new File("web/resources/data/users.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            int count = 0; // Admin1-Santos-Karen-karensantos@gmail.com-Ottawa-ON-Canada
            while ((line = br.readLine()) != null) {
                String[] info = line.split("-");
                String pass = info[0];
                String lastName = info[1];
                String firstName = info[2];
                String email = info[3];
                String city = info[4];
                String province = info[5];
                String country = info[6];
                String DBO = info[7];
                String gender = info[8];
                String occupation = info[9];
                try {
                    if (helper.selectUserByEmail(email) == null) {
                        PasswordService ps = null;
                        while (ps == null) {
                            ps = PasswordService.getInstance();
                        }
                        String encryptedPW = "";
                        encryptedPW = ps.encrypt(pass);
                        UserAccount user = new UserAccount("", encryptedPW, lastName, firstName,
                                email, city, province, country);
                        String userID = helper.insertUserAccount(user);
                        UserProfile profile = new UserProfile(userID, DBO, gender, occupation);
                        helper.insertUserProfile(profile);
                        count++;
                    } else {
                        System.out.println("The email account " + email + " already exists.");
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println(count + " users & profiles inserted \n");
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void insertStars() throws Exception {
        System.out.println("Inserting stars");
        File file = new File("web/resources/data/stars.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            int count = 0;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("lastname:")) {
                    Star star = new Star();
                    star.setLastName(line.split(":")[1]);
                    line = br.readLine();
                    star.setFirstName(line.split(":")[1]);
                    line = br.readLine();
                    star.setDBO(line.split(":")[1]);
                    line = br.readLine();
                    star.setCountry(line.split(":")[1]);
                    helper.insertStar(star);
                    count++;
                }
            }
            System.out.println(count + " stars inserted \n");
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void insertStarRole() throws Exception {
        System.out.println("Inserting star_role");
        File file = new File("web/resources/data/starRole.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] info = line.split("-");
                String[] columns = {"last_name", "first_name"};
                String[] nameLastFirst = {"'" + info[0].split(" ")[0] + "'", "'" + info[0].split(" ")[1] + "'"};
                String column = "name";
                String roleName = "'" + info[1] + "'";
                String starID = helper.selectIDFromWhereEquals("star", columns, nameLastFirst);
                String roleID = helper.selectIDFromWhereEquals("role", column, roleName);
                if (starID == null) {
                    System.out.println("Star ID for " + info[0].split(" ")[0] + " came back null");
                } else if (roleID == null) {
                    System.out.println("Role ID for " + info[1] + " came back null");
                } else {
                    helper.insertStarRole(starID, roleID);
                    count++;
                }
            }
            System.out.println(count + " star_roles inserted \n");
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void insertUserLikesGenre() throws Exception {
        System.out.println("Inserting user_likes_genre");
        File file = new File("web/resources/data/userLikesGenger.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] info = line.split("-");
                String email = info[0];
                UserAccount user = helper.selectUserByEmail(email);
                if (user == null) {
                    System.out.println("User for " + email + " came back null");
                } else {
                    String userID = user.getUserID();
                    String[] genres = info[1].split("%");
                    for (int i = 0; i < genres.length; i++) {
                        String genreID = helper.selectIDFromWhereEquals("genre", "name", "'" + genres[i] + "'");
                        if (genreID == null) {
                            System.out.println("GenreID for " + genres[i] + " came back null");
                        } else {
                            helper.insertUserLikesGenre(userID, genreID);
                            count++;
                        }
                    }
                }
            }
            System.out.println(count + " user_likes_genre inserted \n");
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void insertUserWatchedMovie() throws Exception {
        System.out.println("Inserting user_watched_movie");
        File file = new File("web/resources/data/userWatchedMovie.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] info = line.split("-");
                String email = info[0];
                UserAccount user = helper.selectUserByEmail(email);
                if (user == null) {
                    System.out.println("User for " + email + " came back null");
                } else {
                    String userID = user.getUserID();
                    String[] columns = {"name", "date_released"};
                    String[] movieInfo = {"'" + info[1].split("%")[0] + "'", "'" + info[1].split("%")[1] + "'"};
                    String movieID = helper.selectIDFromWhereEquals("movie", columns, movieInfo);
                    if (movieID == null) {
                        System.out.println("Movie ID for " + info[1].split("%")[0] + " came back null");
                    } else {
                        helper.insertUserWatchedMovie(userID, movieID, info[2]);
                        count++;
                    }
                }
            }
            System.out.println(count + " user_watched_movie inserted \n");
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void insertUserRatesMovie() throws Exception {
        System.out.println("Inserting user_rates_movie");
        File file = new File("web/resources/data/userRatesMovie.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] info = line.split("-");
                String email = info[0];
                UserAccount user = helper.selectUserByEmail(email);
                if (user == null) {
                    System.out.println("User for " + email + " came back null");
                } else {
                    String userID = user.getUserID();
                    String[] columns = {"name", "date_released"};
                    String[] movieInfo = {"'" + info[1].split("%")[0] + "'", "'" + info[1].split("%")[1] + "'"};
                    String movieID = helper.selectIDFromWhereEquals("movie", columns, movieInfo);
                    if (movieID == null) {
                        System.out.println("Movie ID for " + info[1].split("%")[0] + " came back null");
                    } else {
                        helper.insertUserRatesMovie(userID, movieID, Integer.parseInt(info[2]));
                        count++;
                    }
                }
            }
            System.out.println(count + " user_rates_movie inserted \n");
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void insertMovies() throws Exception {
        System.out.println("Inserting movies");
        File file = new File("web/resources/data/movies.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            int count = 0;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Info:")) {
                    line = line.substring(5);
                    String[] info = line.split("-");
                    if (info.length > 0) {
                        Movie movie = new Movie();
                        movie.setName(info[0]);
                        movie.setDateReleased(info[1]);
                        movie.setLanguage(info[2]);
                        movie.setSubtitled(Boolean.parseBoolean(info[3]));
                        movie.setCountry(info[4]);
                        movie.setAgeRestriction(Integer.parseInt(info[5]));
                        String[] columns = {"first_name", "last_name"};
                        String[] atts = {"'" + info[6].split(" ")[0] + "'", "'" + info[6].split(" ")[1] + "'"};
                        String directorID = getID("Director", columns, atts);
                        if (directorID == null) {
                            System.out.println("directorID for " + info[6] + " is null");;
                        } else {
                            movie.setDirectorID(directorID);
                        }
                        String movieID = helper.insertMovie(movie);
                        insertMovieAndGenre(br, movieID);
                        insertMovieAndStar(br, movieID);
                        insertMovieAndRole(br, movieID);
                        insertMovieAndStudio(br, movieID);
                        count++;
                    }
                }
            }
            System.out.println(count + " movies inserted\n");
            br.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBPopulator.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBPopulator.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBPopulator.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void insertMovieAndGenre(BufferedReader br, String movieID) throws IOException, SQLException {
        String line = br.readLine();
        line = line.substring(7);
        String[] genreInfo = line.split("-");
        if (genreInfo.length > 0) {
            for (int i = 0; i < genreInfo.length; i++) {
                String genreID = helper.selectIDFromWhereEquals("genre", "name", "'" + genreInfo[i] + "'");
                if (genreID == null) {
                    System.out.println("genreID for " + genreInfo[i] + " is null");
                } else {
                    helper.insertMovieGenre(movieID, genreID);
                }
            }
        }
    }

    private static void insertMovieAndStar(BufferedReader br, String movieID) throws IOException, SQLException, Exception {
        String line = br.readLine();
        line = line.substring(7);
        if (line.length() > 3) {
            String[] starInfo = line.split("-");
            for (int i = 0; i < starInfo.length; i++) {
                String[] columns = {"last_name", "first_name"};
                String[] attributes = {"'" + starInfo[i].split(" ")[1] + "'", "'" + starInfo[i].split(" ")[0] + "'"};
                String starID = helper.selectIDFromWhereEquals("star", columns, attributes);
                if (starID == null) {
                    System.out.println("starID for " + starInfo[i] + " is null");
                } else {
                    helper.insertMovieStar(movieID, starID);
                }
            }
        }
    }

    private static void insertMovieAndRole(BufferedReader br, String movieID) throws IOException, SQLException {
        String line = br.readLine();
        line = line.substring(6);
        if (line.length() > 3) {
            String[] roleInfo = line.split("-");
            for (int i = 0; i < roleInfo.length; i++) {
                String roleID = helper.selectIDFromWhereEquals("role", "name", "'" + roleInfo[i] + "'");
                if (roleID == null) {
                    System.out.println("roleID for " + roleInfo[i] + " is null");
                } else {
                    helper.insertMovieRole(movieID, roleID);
                }
            }
        }
    }

    private static void insertMovieAndStudio(BufferedReader br, String movieID) throws IOException, SQLException {
        String line = br.readLine();
        line = line.substring(8);
        String[] studioInfo = line.split("-");
        if (studioInfo.length > 0) {
            for (int i = 0; i < studioInfo.length; i++) {
                String studioID = helper.selectIDFromWhereEquals("studio", "name", "'" + studioInfo[i] + "'");
                if (studioID == null) {
                    System.out.println("studioID for " + studioInfo[i] + " is null");
                } else {
                    helper.insertMovieStudio(movieID, studioID);
                }
            }
        }
    }

    private static String getID(String table, String[] columns, String[] attributes) throws Exception {
        String s = null;
        try {
            s = helper.selectIDFromWhereEquals(table, columns, attributes);

        } catch (SQLException ex) {
            Logger.getLogger(DBPopulator.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

}
