/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.server.broker;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author damja
 */
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.server.konfiguracija.Konfiguracija;
public class DbKonekcija {
    private static DbKonekcija instance;
    private Connection connection= null;
    private DbKonekcija(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (connection == null || connection.isClosed()){
                String url = rs.ac.bg.fon.server.konfiguracija.Konfiguracija.getIntanca().getProperty("url");
                String username = Konfiguracija.getIntanca().getProperty("username");
                String password = Konfiguracija.getIntanca().getProperty("password");
                connection = DriverManager.getConnection(url,username,password);
                connection.setAutoCommit(false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbKonekcija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DbKonekcija getInstance() {
        if (instance == null)
            instance = new DbKonekcija();
        return instance;
    }
    
 
}
