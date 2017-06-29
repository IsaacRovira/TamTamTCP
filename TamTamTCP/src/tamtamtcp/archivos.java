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

/*
File[] files = new File("/path/to/the/directory").listFiles(new FilenameFilter()
{ @Override public boolean accept(File dir, String name)
{ return name.endsWith(".xml"); } });
*/
public class archivos {
    String nombreArchivo;
    String rutaArchivos;
    File directorio;
    File file; 
    Scanner lector;
    PrintWriter escritor;
    File[] listaDeFicheros;
    
    archivos(){
        this.nombreArchivo = null;
        this.rutaArchivos = null;
        this.directorio = null;
        this.file = null;
        this.lector = null;
        this. escritor = null;
        this.listaDeFicheros = null;
    }
    
    archivos(String nombreArchivo, String rutaArchivos){
        this.nombreArchivo = nombreArchivo;
        this.rutaArchivos = rutaArchivos;
        this.directorio = new File(rutaArchivos);        
    }
    
    public ArrayList abrirfichero(File file){
        ArrayList datos = new ArrayList();
        try{            
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
    
    private File[] getFiles(){        
        directorio.listFiles();
    }    
    
    private int numOfFiles(){
        int num = 0;
        for(File f: listaDeFicheros){
            if(f.isFile()) num++;
        }
        return num;
    }    
}
