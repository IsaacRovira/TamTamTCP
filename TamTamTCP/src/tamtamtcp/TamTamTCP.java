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
public class TamTamTCP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int port;
        String direccionIP;
        Socket cliente;
        ServerSocket Servicio;        
        TcpClientConn conn = new TcpClientConn();
        
        if(args.length < -1){
            System.out.println("Mostrar ayuda.");
            System.exit(0);
        }
        
        conn.direccionIP = "10.10.1.102";
        conn.port = 5050;
        
        conn.Connectar();
        
        if(conn.status){
            System.out.println("Conectado");
            conn.Enviar_D();
            conn.Enviar_P();
            conn.Recibir();
        }
        
        try{
            conn.ops.print((char)0x104);
        }catch(Exception e){
            System.err.print(e.getClass().getName() + " 2 " + e.getMessage());
        }
        
        
        int resp = 0;
        String response="";
        do{
            try{
                resp = conn.is.read();
                if(resp > 0){
                    response = String.valueOf(resp);
                    if(response != null){
                        System.out.print(response+" ");
                    }
                }
            }catch(Exception e){
                System.err.print(e.getClass().getName() + " " + e.getMessage());
            }            
        }while(resp != -1);
        
        conn.Close();        
    }
}

class TcpClientConn{
        String direccionIP;
        int port;
        Socket cliente;
        DataInputStream is;
        DataOutputStream os;
        PrintStream ops;
        boolean status;
        
        public TcpClientConn(){
            this.direccionIP = null;
            this.port = 0;
            this.cliente = null;
            this.is = null;
            this.os = null;            
            this.ops = null;
            this.status = false;
        }
        
        public void Connectar(){
            status = false;
            try{
                cliente = new Socket(direccionIP, port);
                status = true;
            }catch(Exception e){                
                System.err.print(e.getClass().getName() + " " + e.getMessage());
            }
        }
        
        public void Recibir(){
            try{
                is = new DataInputStream(cliente.getInputStream());
            }catch(Exception e){
                System.err.print(e.getClass().getName() + " " + e.getMessage());
            }
        }
        
        public void Enviar_D(){
            try{
                os = new DataOutputStream(cliente.getOutputStream());
            }catch(Exception e){
                System.err.print(e.getClass().getName() + " " + e.getMessage());
            }
        }
        public void Enviar_P(){
            try{
                ops = new PrintStream(cliente.getOutputStream());
            }catch(Exception e){
                System.err.print(e.getClass().getName() + " " + e.getMessage());
            }
        }
        
        public void Close(){
            try{
                os.close();
                is.close();
                ops.close();
                cliente.close();
                status = false;
            }catch(Exception e){
                System.err.print(e.getClass().getName() + " " + e.getMessage());
            }
        }
    }

