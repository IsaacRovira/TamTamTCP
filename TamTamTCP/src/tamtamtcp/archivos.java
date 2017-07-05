/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tamtamtcp;
import java.io.*;
import java.nio.file.*;
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

/**
 *  
 * @author Isaac
 * 
 * Manejara la lectura de archivos con peticiones y
 * la creación de archivos con resultados.
 * 
 * Mediante un ArrayList<String> almacenaremos las líneas de los
 * ficheros con peticiones.
 * 
 */
public class archivos {
    Astmhigh astm;
    Path directorio, oldDirectorio;
    ArrayList<String> datos;
    String extension;
    
    archivos(){
        this.directorio = null;
        this.oldDirectorio = null;
        this.datos = null;
        this.extension = null;
    }
    
    archivos(Path directorio, Path oldDirectorio, String extension){
        this.directorio = directorio;
        this.oldDirectorio = oldDirectorio;
        this.extension = extension;
    }

    public Astmhigh getAstm(){
        return this.astm;
    }
    public void setAstm(Astmhigh astm){
        this.astm = astm;
    }
    public Path getDirectorio(){
        return this.directorio;
    }
    public void setDirectorio(Path directorio){
        this.directorio = directorio;
    }
    public Path getOldDirectorio(){
        return this.oldDirectorio;
    }
    public void setOldDirectorio(Path oldDirectorio){
        this.oldDirectorio = oldDirectorio;
    }
    public ArrayList<String> getDatos(){
        return datos;
    }
    public void setDatos(ArrayList<String> datos){
        this.datos = datos;
    }
    public void setDatotoDatos(String string){
        this.datos.add(string);
    }
    public String getExtension(){
        return this.extension;
    }
    public void setExtension(String extension){
        this.extension = extension;
    }
    
    /**
     * 
     * @param line Posición de la cadena que se desea recuperar en el ArrayList
     * @return Devuelve un String.
     */
    public String getDato(int line){
        if(line < datos.size()){
            return datos.get(line);
        }
        return null;
    } 
    
    public void BorrarDatos(){
        datos.clear();
    }

    
    /**
     * Recorre un fichero de texto rellenando un ArrayList<String> con las líneas leidas.
     * @param file Fichero de texto que queremos leer.
     * @return  1 si ha tenido exito.
     *          -1 en caso de error.
     *         
     */
    public int LeerFichero(File file){
        if(!this.datos.isEmpty()){this.datos.clear();}
        Scanner lector = null;
        try{            
            lector = new Scanner(file);
            while(lector.hasNextLine()){
                this.datos.add(lector.nextLine());                
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
            return -1;
        }finally{
            lector.close();            
        }
        return 1;
    }
    
    /**
     * @param borrar true | false : borrar o no el archivo origen al finalizar.
     * @param destino File de destino de los datos.
     * @param origen File de los datos de origen.
     * @return 1 si la operación termina con exito.
     *         -1 si ocurre un error.
     *          
     */
    public int CopiarFichero(File origen, File destino, boolean borrar){
        Scanner lector = null;
        PrintWriter escritor = null;
        try{
            lector = new Scanner (origen);
            escritor = new PrintWriter(destino);
            while(lector.hasNextLine()){
                escritor.write(lector.nextLine());
            }            
            if(borrar){origen.delete();}
        }catch(Exception e){
            System.out.println("Archivos-CopiarFichero error: "+e.getMessage());
            return -1;
        }finally{
            lector.close();
            escritor.close();
        }
        return 1;
    }    
    
}
