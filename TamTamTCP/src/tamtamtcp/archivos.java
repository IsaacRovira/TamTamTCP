/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tamtamtcp;
import java.io.*;
import java.nio.*;
import java.util.*;

/**
 *
 * @author Isaac
 */
public class archivos {
    String nombreArchivo;
    String rutaArchivo;
    Directory directorio;
    File file; 
    Scanner lector;
    PrintWriter escritor;
    
    private ArrayList abrirfichero(){
        ArrayList datos = new ArrayList();
        try{
            file = new File(nombreArchivo);
            lector = new Scanner(file);
            while(lector.hasNextLine()){
                datos.add(lector.nextLine());
                
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
            return null;
        }finally{
            lector.close();            
        }
        return datos;
    }
    
    
}
