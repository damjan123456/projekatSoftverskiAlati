/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.server.broker;

import java.util.List;


public interface DBBrokerInterfejs<T>{
    default public void connect() throws Exception{
        DbKonekcija.getInstance().getConnection();
    }
    default public void disconnect() throws Exception{
        DbKonekcija.getInstance().getConnection().close();
    }
    default public void commit() throws Exception{
        DbKonekcija.getInstance().getConnection().commit();
    }
    default public void rollback() throws Exception{
        DbKonekcija.getInstance().getConnection().rollback();
    }
    List<T> getAll (T param,String uslov) throws Exception;
    void add(T param) throws Exception;
    void edit(T param) throws Exception;
    void delete(T param) throws Exception;
    int addReturnKey(T param) throws Exception;
}
