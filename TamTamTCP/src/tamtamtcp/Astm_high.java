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
public class Astm_high {
    
    ArrayList farameList;
    
}

class trama{
    String[] trama;    
    
    trama(String frame, String spliter){
        this.trama = frame.split(spliter);        
    }
    
    trama(String frame){
        this.trama = frame.split("|");        
    }
    
    public String[] getTrama(){
        return this.trama;
    }
    
    public String getValue(int pos){
        if(pos < trama.length){
            return trama[pos];
        }
        return null;
    }
    
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
