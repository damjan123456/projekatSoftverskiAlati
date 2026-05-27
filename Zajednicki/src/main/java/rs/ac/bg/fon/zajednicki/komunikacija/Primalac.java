/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.zajednicki.komunikacija;


import java.io.ObjectInputStream;
import java.net.Socket;


/**
 *
 * @author damja
 */
public class Primalac {
    private Socket socket;
    ObjectInputStream in;

    public Primalac(Socket socket) {
        this.socket = socket;
        try {
            this.in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public Object primi(){
        try { 
            return in.readObject();
        } catch (Exception ex) {
            System.out.println("Klijent se odvezao");
        }
        return null;
    }
}
