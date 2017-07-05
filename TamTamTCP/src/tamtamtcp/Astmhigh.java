/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tamtamtcp;

import java.util.*;

/**
 *
 * @author Isaac
 */
public class Astmhigh {
    
    ArrayList<trama> frameList;
    
    Astmhigh(){
        this.frameList = null;
    }
    
    public ArrayList getFrameList(){
        return this.frameList;
    }
    public String[] getFrame(int pos){
        return this.frameList.get(pos).getTrama();
    }
    
    /**
    *@author Isaac Rovira
    *@param pos Define la trama ha devolver según su posición en el array.
    *@param campo Definie el campo de la trama a devolver.
    *@return Devuelve un String con el valor del campo o null si está vacio.
    */
    public String getFrameValue(int campo, int pos){
        return this.frameList.get(pos).getValue(campo);
    }
}

class trama{
    String[] trama;    
    
    /**
     * 
     * @param frame String astm.
     * @param spliter delimitador de los campos de la cadena.
     */
    trama(String frame, String spliter){
        this.trama = frame.split(spliter);        
    }
    
    /**
     * 
     * @param frame String astm. Delimitar = "|".
     */
    trama(String frame){
        this.trama = frame.split("|");        
    }
    
    /**
     * 
     * @return devuelve un String[] con los datos del frame astm almacenado.
     */
    public String[] getTrama(){
        return this.trama;
    }
    
    /**
     * 
     * @param pos número del campo de la trama astm.
     * @return devuelve un dato cadena con el valor del campo de la posición inidicada mediante el parámetro "pos".
     */
    public String getValue(int pos){
        if(pos < trama.length){
            return trama[pos];
        }
        return null;
    }
    
    /**
     * Inserta el valor indicado en "value" en la posición "pos" de la trama astm.
     * @param value valor que se desea introducir.
     * @param pos campo donde se desea introducir el valor.
     */
    public void SetValue(String value, int pos){
        if(pos < this.trama.length){
            this.trama[pos] = value;
        }
    }
}

class header extends trama {
    String[] spliters;

    public header(String frame, String spliter) {
        super(frame, spliter);
    }
    
    public header (String frame){
        super(frame);
    }    
}

class comment extends trama{
    int id;
    
    public comment (String frame, String spliter, int id){
        super(frame, spliter);
        this.id = id;
    }
    
    public comment (String frame, int id){
        super(frame);
        this.id = id;
    }
    
    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }
}

class patient extends trama{
    int id;

    public patient(String frame, String spliter, int id) {
        super(frame, spliter);
        this.id = id;
    }
    public patient(String frame, int id){
        super(frame);
        this.id = id;
    }
    
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
}

class result extends trama{
    int id;
    
    public result(String frame, String spliter, int id) {
        super(frame, spliter);
        this.id = id;
    }
    
    public result(String frame, int id){
        super(frame);
        this.id = id;
    }
    
        public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
}
