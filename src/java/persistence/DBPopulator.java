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
import model.Movie;

/**
 *
 * @author karensantos
 */
public class DBPopulator {
    
    private static DBHelper helper;
    
    public static void main(String[] args) throws IOException {

        helper = new DBHelper();
        insertData();

    }
    
    private static void insertData() {
        
        DBHelper DBHelper = new DBHelper();
        try {
            if (DBHelper.getTotalRows("Movie") == 0){
                System.out.println("nao tem nada");
                insertDirectors();
                insertMovies();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private static void insertMovies(){
        File file = new File("web/resources/data/movies.txt");
        System.out.println("leu o file");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Info:") && line.length() > 10) {
                    line = line.substring(5);
                    String[] info = line.split("-");
                    if (info.length > 0){
                        Movie movie = new Movie();
                        movie.setName(info[0]);
                        movie.setDateReleased(info[1]);
                        movie.setLanguage(info[2]);
                        movie.setSubtitled(Boolean.parseBoolean(info[3]));
                        movie.setCountry(info[4]);
                        movie.setAgeRestriction(Integer.parseInt(info[5]));
                        helper.insertMovie(movie);
                        System.out.println(movie.getName() + " inserted");
                    }
                }
            }
            System.out.println("All movies inserted \n");
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

    
    }
    
}
