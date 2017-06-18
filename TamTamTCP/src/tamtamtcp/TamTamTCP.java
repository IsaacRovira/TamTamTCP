/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tamtamtcp;
import java.io.DataInputStream;
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
        
        if(args.length < 1){
            System.out.println("Mostrar ayuda.");
            System.exit(0);
        }
        
        switch(args[0]){
            case "Cliente":
                try{
                    cliente = new Socket(direccionIP,port);
                }catch(Exception e){
                    System.out.println(e.getClass().getName() + " , " + e.getMessage());
                }
                break;
            case "servidor":
                try{
                    Servicio = new ServerSocket(port);                    
                    cliente = Servicio.accept();
                }catch(Exception e){
                    System.out.println(e.getClass().getName() + " , " + e.getMessage());
                }                
                break;
            default:
                System.out.println("Mostrar ayuda");
                System.exit(0);
                break;
        }
        
        //DataImputStream
        DataInputStream input;
        try{
            input = new DataInputStream(cliente.getInputStream());
        }catch(Exception e){
            System.out.println(e.getClass().getName() + " , " + e.getMessage());
        }        
    }
}


