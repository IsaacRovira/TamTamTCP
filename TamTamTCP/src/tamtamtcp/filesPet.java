/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tamtamtcp;

import java.util.*;
import java.nio.file.*;
import java.io.*;
/**
 *
 * @author Isaac
 */
public class filesPet extends archivos {
    
    File[] fileList;
    
    filesPet(){
        super();
    }    
    filesPet(Path directorio, Path oldDirectorio, String extension){
        super();
    }
    
     /**
     * @param path Direcotorio del cual queremos recuperar los ficheros.
     * @return Actualiza la variable listaDeFicheros con los ficheros del directorio "path" y devuelve su longitud o
     * -1 en caso de error.
     *
     */
    public int getFiles(Path path){
        try{
            fileList = path.toFile().listFiles();
            return fileList.length;
        }catch(Exception e){
            System.out.println("Archivos getQueryFiles error: " + e.getMessage());
        }
        return -1;
    }
     

    
}
