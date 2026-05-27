/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.zajednicki.komunikacija;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 *
 * @author damja
 */
public class Posiljalac {
    private Socket socket;
    private ObjectOutputStream out;

    public Posiljalac(Socket socket) {
        this.socket = socket;
        try {
            // Kreiraj ObjectOutputStream JEDNOM
            this.out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void posalji(Object object){
        try {
            out.writeObject(object);
            out.flush();
            out.reset();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
}
