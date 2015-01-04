/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modcognitive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Alizée ARNAUD, Jordan DAITA
 */
public class Datas {
    public static final int typeCoord = 0;
    public static final int typeSem = 1;
    public static final int typeTaille = 2;
    protected List<Mot> listeMots;
    protected int nbMots;
    protected Map<String, List<Mot>> mapHumain; 
    
    Datas(int nbMots){
        this.nbMots = nbMots;
        listeMots = new ArrayList<>();
    }
     
     
     public void extractDonnees(File fichier, int type) throws IOException{
        String key;
        String ligne;
        int count = 0;
        
        FileReader lecteurDeFichier = new FileReader(fichier);
        BufferedReader buff = new BufferedReader(lecteurDeFichier);
        
        while (count < nbMots) {
            ligne = buff.readLine();
            java.util.StringTokenizer coupeur = new java.util.StringTokenizer(ligne, " ");
            key = coupeur.nextToken();
            if(listeMots.size() < nbMots)
                listeMots.add(count, new Mot(key));
            switch(type){
                case typeCoord:{
                    listeMots.get(count).setX(Double.parseDouble(coupeur.nextToken()));
                    listeMots.get(count).setY(Double.parseDouble(coupeur.nextToken()));
                    break;
                }
                case typeSem:{
                    listeMots.get(count).setSem(Double.parseDouble(coupeur.nextToken()));
                    break;
                }
                   
                case typeTaille :
                    listeMots.get(count).setTaille(Double.parseDouble(coupeur.nextToken()));
                    break;
            }
                
            
            
            count++;
        }
        buff.close();
     }
     
      public void allFixationsHumain(File fichier) throws FileNotFoundException, IOException{
        if(listeMots.size() == nbMots){
            String ligne;
            String key;
            double x,y;
            mapHumain = new HashMap<>();
            FileReader lecteurDeFichier = new FileReader(fichier);
            BufferedReader buff = new BufferedReader(lecteurDeFichier);
            buff.readLine();
            while ((ligne = buff.readLine()) != null) {
                String[] array = ligne.split(",");
                key = array[0]+"-"+array[1]+array[2];
                if(mapHumain.get(key)== null){
                    List<Mot> liste = new ArrayList<>();
                    mapHumain.put(key, liste);
                }
                x = Double.parseDouble(array[7]);
                y = Double.parseDouble(array[8]);
                x = (x/1024)*2-1;
                y = (((768-y)/768)*2)-1;
                Mot tmp = Calc.motPlusProche(x, y, listeMots);
                if(mapHumain.get(key).size()>0){
                    if(!tmp.nom.equals(mapHumain.get(key).get(mapHumain.get(key).size()-1).nom))
                        mapHumain.get(key).add(tmp);
                }
                else
                    mapHumain.get(key).add(tmp);
                
            }
        }
        else
              throw new RuntimeException("Liste de mot non initialisée");
     }

    @Override
    public String toString() {
        String ret="";
        for (int i = 0; i < nbMots; i++) {
            ret+= listeMots.get(i);
        }
        return ret;
    }
    
}
