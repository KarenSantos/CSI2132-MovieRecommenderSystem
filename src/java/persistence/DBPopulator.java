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
                insertDirectors();
                insertMovies();
            }
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
                    System.out.println(dir.getLastName() + " inserted");
                }
            }
            System.out.println("All " + count + " directors inserted \n");
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
                        if (directorID ==null) {
                            countNull++;
                        } else {
                            movie.setDirectorID(directorID);
                        }
                        helper.insertMovie(movie);
                        count++;
                        System.out.println(movie.getName() + " inserted");
                    }
                }
            }
            System.out.println("All " + count + " movies inserted, " + countNull + " with null \n");
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String getID(String table, String[] columns, String[] attributes) throws Exception {
        String s = null;
        try {
            s = helper.selectIDFromWhereEquals(table, columns, attributes);
        } catch (SQLException ex) {
            Logger.getLogger(DBPopulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

}
