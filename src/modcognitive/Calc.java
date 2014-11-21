/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modcognitive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jo
 */
public class Calc {
    /**
     * Fonctionne avec les Maps, on ne l'utilise donc plus
     * 
     * @param x1
     * @param y1
     * @param mapCoord
     * @return 
     */
    public String motPlusProche(double x1, double y1, HashMap<String, ArrayList> mapCoord){
        double min = 999999;
        String retour="";
        for ( Map.Entry<String, ArrayList> entry : mapCoord.entrySet()) {
            String key = entry.getKey();
            if(entry.getValue().size() != 2)
                throw new RuntimeException("Arraylist doit contenir des Double et avoir 2 valeurs");  
            Double x2 = (Double) entry.getValue().get(0);
            Double y2 = (Double) entry.getValue().get(1);
            
            double tmp = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
            if(tmp<min){
                min = tmp;
                retour = key;
            }
            
        }
        return retour;
    }
    /**
     * Recherche le mot le plus proche des coordonnées entrées en paramètre
     * @param x1
     * @param y1
     * @param listeMots
     * @return 
     */
    public Mot motPlusProche(double x1, double y1, List<Mot> listeMots){
        double min = 999999;
        Mot retour = null;
        for (int i = 0; i < listeMots.size() ; i++) {

            double tmp = calcDistance(x1, y1, listeMots.get(i));
            if(tmp<min){
                min = tmp;
                retour = listeMots.get(i);
            }
        }
        return retour;
    }
    
    public double calcDistance(double x1, double y1, Mot mot){
        
        Double x2 = mot.x;
        Double y2 = mot.y;
        return Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
    }
    
    private double calcDistance(Mot courant, Mot get) {
        return calcDistance(courant.x, courant.y, get);
    }
    
    public void calcSemantique(Mot courant, List<Mot> listeMots){
        for (int i = 0; i < listeMots.size() ; i++) {
            if(listeMots.get(i).distanceActuelle < 0.3)
                listeMots.get(i).scoreSemantique += (courant.sem-0.3)*5;
            listeMots.get(i).augmenterScore(-courant.sem*listeMots.get(i).distanceActuelle*5);
         }
    }
    
    private void calcTaille(List<Mot> listeMots) {
        for (int i = 0; i < listeMots.size() ; i++) {
            listeMots.get(i).augmenterScore(listeMots.get(i).taille/5);
            listeMots.get(i).augmenterScore(-listeMots.get(i).distanceActuelle/2);
         }
    }

    private void calcMemoire(List<Mot> listeMots) {
        for (int i = 0; i < listeMots.size() ; i++) {         
            if(listeMots.get(i).mem==7)
                listeMots.get(i).augmenterScore(-10);
            if(listeMots.get(i).mem != 0)
               listeMots.get(i).mem--;
            listeMots.get(i).augmenterScore(-listeMots.get(i).mem*2);
         }
        
    }
    
    public Mot prochainMot(Mot courant,  List<Mot> listeMots)
    {
        courant.mem  = ModCognitive.TAILLE_MEMOIRE;
         for (int i = 0; i < listeMots.size() ; i++) {
             listeMots.get(i).setScoreActuel(listeMots.get(i).scoreSemantique);
             listeMots.get(i).setDistanceActuelle(calcDistance(courant, listeMots.get(i)));
         }
         calcSemantique(courant, listeMots);
         calcTaille(listeMots);
         calcMemoire(listeMots);
         
         double max = courant.scoreActuel;
         Mot suivant = courant;
         
         for (int i = 0; i < listeMots.size() ; i++) {
             if(listeMots.get(i).scoreActuel > max){
                 max = listeMots.get(i).scoreActuel;
                 suivant = listeMots.get(i);
             }
                 
         }
         return suivant;
    }




}
