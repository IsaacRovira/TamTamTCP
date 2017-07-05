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
public class filesRes extends archivos{
    File fileOrg, fileDest;
    
    filesRes(){
        super();
        this.fileOrg = null;
        this.fileDest = null;
    }
    
    filesRes(Path directorio, Path oldDirectorio, String extension){
        super();
        this.fileOrg = null;
        this.fileDest = null;
    }
    
    public File getFileOrg(){
        return this.fileOrg;
    }
    public void setFileOrg(File fileOrg){
        this.fileOrg = fileOrg;
    }
    public File getFileDest(){
        return this.fileDest;        
    }
    public void setFileDest(File fileDest){
        this.fileDest = fileDest;
    }
    
    public int EnviarFicheroRes(){        
        PrintWriter escritor = null;        
        try{
            CrearFicheroRes(super.astm.getFrameValue(3, 3));
            escritor = new PrintWriter(super.file);
            for(String S: super.datos){
                escritor.write(S);
            }            
        }catch(Exception e){
            System.out.println("Archivos-CrearFicheroRes error: " + e.getMessage());
            return -1;
        }finally{
            escritor.close();
        }
        return 1;
    }
    
    private void CrearFicheroRes(String sid){
        Date now = new Date();
        file = new File(super.oldDirectorio.toFile()+ sid +"_"+now.toString()+"."+super.extension);        
    }
    
    private File RenameFile(){        
        File file = new File(super.directorio + NewFileName(super.file));        
        return file;
    }
    
    private String NewFileName(File file){
        if(file.getName().split("_").length > 1){
            return file.getName().split("_")[file.getName().split("_").length -1];
        }
        return file.getName().split("_")[0];
    }
}
