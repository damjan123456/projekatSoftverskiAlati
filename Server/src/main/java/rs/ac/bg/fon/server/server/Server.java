/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.server.server;

import rs.ac.bg.fon.server.niti.ObradaKlijentskihZahteva;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author damja
 */
public class Server extends Thread{
    private boolean kraj = false;
    private ServerSocket serverSocket;
    List<ObradaKlijentskihZahteva> klijenti = new ArrayList<>();

    public Server() {
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(9000);
            System.out.println("Server je pokrenut");
            while (!kraj){
                try{
                Socket s = serverSocket.accept();
                System.out.println("Klijent je povezan");
                
                ObradaKlijentskihZahteva okz = new ObradaKlijentskihZahteva(s);
                klijenti.add(okz);
                okz.start();
                }catch(SocketException e){
                }    
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void zaustaviServer(){
        kraj = true;
        try {
            for (ObradaKlijentskihZahteva klijent : klijenti)
                klijent.prekiniNit();
            
            serverSocket.close();
            System.out.println("Server je zaustavljen");
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
            
}
