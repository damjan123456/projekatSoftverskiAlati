/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.server.konfiguracija;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author damja
 */
public class Konfiguracija {
    private static Konfiguracija instanca;
    private Properties konfiguracija;

    private Konfiguracija() {
        try {
            konfiguracija = new Properties();
            konfiguracija.load(new FileInputStream("D:\\FON\\ProjektovanjeSoftvera\\seminarski\\netbeans\\config\\config.properties"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(Konfiguracija.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public static Konfiguracija getIntanca() {
        if (instanca == null)
            instanca = new Konfiguracija();
        return instanca;
    }
    
    public String getProperty(String key){
        return konfiguracija.getProperty(key,"n/a");
    }
    
    public void setProperty(String key, String value){
        konfiguracija.setProperty(key, value);
    }
    
    public void izmeni(){
        try {
            konfiguracija.store(new FileOutputStream("D:\\FON\\ProjektovanjeSoftvera\\seminarski\\netbeans\\config\\config.properties"), null);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(Konfiguracija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
