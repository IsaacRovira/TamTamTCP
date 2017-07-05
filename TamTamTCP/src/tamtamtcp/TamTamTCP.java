/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tamtamtcp;
import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author Isaac
 */
public class TamTamTCP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        
        ArrayList<Astm> astmList;
        ArrayList<conexion> connList;
        ArrayList<archivos> fileList;
        
        int port;
        String IP;
        Config config = new Config();
        
        
        if(args.length < -1){
            System.out.println("Mostrar ayuda.");
            System.exit(0);
        }
        
        IP = "127.0.0.1";
        port = 5050;

        Astm conn1 = new Astm(IP, port);
        
        do{           
            switch(conn1.Check_ENQ()){
               case -3:
                   //System.out.print("Nada en el Stream");
                   break;
               case -1:
                   System.out.println("Error");
                   break;
               case 1:
                   boolean on = true;
                   do{
                       switch(conn1.RecibirResultado()){
                           case 0:
                               System.out.println("TimeOut");
                               break;
                           case -1:
                               on = false;
                               System.out.println("ERROR");
                               break;
                           case 1:                               
                               System.out.println("Recibido!");
                               on = false;                               
                       }
                   }while(false);                   
                   break;
               case 0:
                   System.out.println("No ENQ");
                   break;
               default:
                   System.out.println("Unknown");
           }            
            try{
                Thread.sleep(100);
            }catch(Exception e){
                System.out.println(e.getClass() + " " + e.getMessage());
            }            
        }while(true);
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
        
        public TcpClientConn(String ip, int port){
            this.direccionIP = ip;
            this.port = port;
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

