/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tamtamtcp;
import java.io.*;
import java.net.*;
/**
 *
 * @author Isaac
 */
public class conexion {
    String direccion;
    
//Modo cliente = 0 | servidor = 1
    int puerto, modo;
    Socket cliente;
    DataInputStream inStream;
    DataOutputStream outStream;
    PrintStream outPrintStream;
    boolean status;
    
    
    
    conexion(){
        this.direccion = "";
        this.puerto = 0;
        this.modo = 0;
        this.cliente = null;
        this.inStream = null;
        this.outStream = null;
        this.outPrintStream = null;
        setStatus();
    }
    //Constructot modo cliente
    conexion(String direccion, int puerto){
        this.direccion = direccion;
        this.puerto = puerto;
        this.modo = 0;
        this.cliente = null;
        this.inStream = null;
        this.outStream = null;
        this.outPrintStream = null;
        setStatus();
    }
    //Constructor modo servidor
    conexion (int puerto){
        this.direccion = null;
        this.puerto = puerto;
        this.modo = 1;
        this.cliente = null;
        this.inStream = null;
        this.outStream = null;
        this.outPrintStream = null;
        status = false;
    }
    
    private void setStatus(){
        this.status = this.cliente.isClosed();
    }
    public boolean getStatus(){
        return this.status;
    }
    
    public int Connectar(){
        try{
            cliente = new Socket (this.direccion, this.puerto);
            this.inStream = new DataInputStream(cliente.getInputStream());
            this.outStream = new DataOutputStream(cliente.getOutputStream());
            this.outPrintStream = new PrintStream(cliente.getOutputStream());
            setStatus();
            return 1;
        }catch(Exception e){
            System.out.println(e.getClass().toString() + " " + e.getMessage());
            setStatus();
            if(this.status){
                this.cliente.close();
            }
            return 0;
        }finally{            
        }
    }

    public void Close(){
        try{
           this.inStream.close();
           this.outPrintStream.close();
           this.outStream.close();
           cliente.close();
           status = false;
       }catch(Exception e){
           System.err.print(e.getClass().getName() + " " + e.getMessage());
           status = false;
       }
    }   
}
