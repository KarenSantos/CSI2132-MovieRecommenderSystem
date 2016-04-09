/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import control.ContextListener;
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
                insertMovies();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void insertGenres() {
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
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void insertRoles() {
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
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void insertDirectors() {
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
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void insertStudios() {
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
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void insertUsersAndProfiles() throws Exception {
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
                        String userID = helper.insertUser(user);
                        UserProfile profile = new UserProfile(userID, DBO, gender, occupation);
                        helper.insertProfile(profile);
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
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void insertStars() {
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
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void insertMovies() throws Exception {
        File file = new File("web/resources/data/movies.txt");

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            int count = 0;
            int countNull = 0;
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
                            countNull++;
                        } else {
                            movie.setDirectorID(directorID);
                        }
                        helper.insertMovie(movie);
                        count++;
                    }
                }
            }
            System.out.println(count + " movies inserted, " + countNull + " with null directorID \n");
            br.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ContextListener.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ContextListener.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ContextListener.class
                    .getName()).log(Level.SEVERE, null, ex);
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
