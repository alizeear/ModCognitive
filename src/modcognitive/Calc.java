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
     public static int TAILLE_MEMOIRE=10;
    /**
     * Fonctionne avec les Maps, on ne l'utilise donc plus
     * 
     * @param x1
     * @param y1
     * @param mapCoord
     * @return 
     */
     
    public double ecart(int nbFixations, List<Mot> model, List<Mot> humain, Mot but){
        
        double result = 0;
        for (int i = 0; i < nbFixations; i++) {
            result += Math.abs(calcDistance(model.get(i), but) - calcDistance(humain.get(i), but));
        }
        
        return result/nbFixations;
    }
    /**
     * Recherche le mot le plus proche des coordonnées entrées en paramètre
     * @param x1
     * @param y1
     * @param listeMots
     * @return 
     */
    public Mot motPlusProche(double x1, double y1, List<Mot> listeMots){
        double min = Double.MAX_VALUE;
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
            //Plus la sémantique est forte plus la zone autour de ce mot aura un score élevé
            listeMots.get(i).augmenterScore(-courant.sem*listeMots.get(i).distanceActuelle*5);
            // Plus la sémantique est forte plus les mots à faible distance auront un score élevé
         }
    }
    
    private void calcTaille(List<Mot> listeMots) {
        for (int i = 0; i < listeMots.size() ; i++) {
            listeMots.get(i).augmenterScore(listeMots.get(i).taille/10);
            //Plus la taille est grande plus le score du mot sera élevé
            listeMots.get(i).augmenterScore(-listeMots.get(i).distanceActuelle/6);
            //Plus la distance est faible plus le score du mot sera élevé
         }
    }

    private void calcMemoire(List<Mot> listeMots) {
        for (int i = 0; i < listeMots.size() ; i++) {         
            if(listeMots.get(i).mem==TAILLE_MEMOIRE)
                listeMots.get(i).augmenterScore(-1000);
            // Le mot que l'on vient de voir a un score très faible
            if(listeMots.get(i).mem != 0)
               listeMots.get(i).mem--;
            listeMots.get(i).augmenterScore(-listeMots.get(i).mem*100);
            // Les mots que l'on a déjà vu ont un score plus faible (Plus il est récent plus son score est faible)
         }
        
    }
    
    public Mot prochainMot(Mot courant,  List<Mot> listeMots)
    {
        courant.mem  = TAILLE_MEMOIRE;
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
