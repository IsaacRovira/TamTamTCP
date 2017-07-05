/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tamtamtcp;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import org.w3c.dom.*;



/**
 *
 * @author Isaac
 * Obtendrá las configuraciones de un fichero config.xml
 * Establecerá un array con la configuración de cada uno de los analizadores
 * definidos en el archivo config.xml.
 */
import java.util.*;
public class Config {
       
    Document Doc;
    File file;
    DocumentBuilderFactory factory;
    DocumentBuilder builder;
    Document doc;
    ArrayList <anaConfig> config;
    
    Config(){        
        try{
            this.file = new File("./resources/config.xml");            
        }catch(Exception e){
            System.out.println("Config builder error: "+ e.getMessage());
        }
    }
    
    public Document getConfig(){
        try{
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            doc = builder.parse(file);
        }catch(Exception e){
            System.out.println("Config getDoc error: "+e.getMessage());
        }
        return doc;
    }
    
    public void setConfig(String elemento, String valor, String id){        
        try{
            Node analizador = doc.getElementById(id);            
        }catch(Exception e){
            System.out.println("SetConfig error: " + e.getMessage());
        }        
    }
    
    /*Desarrollar métodos para actualizar configuración*/
}

class anaConfig{
    String serialNum, ip, labname, labalias, tipo, ananame, anaalias, petExt, resExt;
    int port, demo, query,timeout,maxnak,threadmainpause,threadlooppause;
    Path dirPet, dirRes, dirQuery;
    
    anaConfig(Document Config, int pos){
        Node node = Config.getElementsByTagName("analizador").item(pos);
        serialNum = node.getAttributes().getNamedItem("id").getNodeValue();
        NodeList nL = Config.getElementsByTagName("analizador").item(pos).getChildNodes();        
        getNodeValues(nL);        
    }    
    /**
     * Recorrera todos los nodos de una lista de nodos pasando cada nodo a la función setValue(nodo);
     * @param nL Lista de  nodos de un fichero XML.
     */
    private void getNodeValues(NodeList nL){
        for(int i = 0; i < nL.getLength();i++){
            if(nL.item(i).hasChildNodes()){
                getNodeValues(nL.item(i).getChildNodes());
            }else{
                setValue(nL.item(i));
            }
        }
    }
    /**
     * Asignara el valor establecido en el fichero XML a una variable según el nombre del nodo.
     * @param node nodo XML que contiene un valor.
     */
    private void setValue(Node node){
        String value = node.getNodeValue();
        switch(node.getNodeName()){
                case "tipo":
                    this.tipo = value;
                    break;
                case "name":
                    this.ananame = value;
                    break;
                case "alias":
                    this.anaalias = value;
                    break;
                case "ip":
                    this.ip = value;
                    break;
                case "port":
                    this.port = Integer.parseInt(value);
                    break;
                case "dirPeticiones": 
                    this.dirPet = Paths.get(value);
                    break;
                case "dirResultados":
                    this.dirRes = Paths.get(value);
                    break;
                case "dirQuery":
                    this.dirQuery = Paths.get(value);
                    break;
                case "demo":
                    this.demo = onOff(value);
                    break;
                case "query":
                    this.query = onOff(value);
                    break;
                case "timeout": 
                    this.timeout = onOff(value);
                    break;
                case "nasnaks":
                    this.maxnak = onOff(value);
                    break;
                case "mainpause":
                    this.threadmainpause = onOff(value);
                    break;
                case "looppause":
                    this.threadlooppause = onOff(value);
                    break;
                case "extension_Peticiones":
                    this.petExt = value;
                    break;
                case "extension_Resultados":
                    this.resExt = value;
                    break;
            default:break;
        }
    }
    
    private int onOff(String valor){        
        switch(valor.toLowerCase()){
            case"s":
            case "si":
                return 1;
            default:
                return 0;
        }        
    }
}


