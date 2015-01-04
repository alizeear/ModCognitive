/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modcognitive;

import java.util.List;

/**
 *
 * @author Alizée ARNAUD, Jordan DAITA
 */
public class Calc {
     public static int TAILLE_MEMOIRE=15;
     public static int AUGMENTE_INFLUENCE_SEMENTIQUE=5;
     public static int AUGMENTE_INFLUENCE_SEMDISTANCE=5;
     public static int DIMINUE_INFLUENCE_TAILLE=10;
     public static int DIMINUE_INFLUENCE_TAILDISTANCE=6;
 
    /**
     * Fonctionne avec les Maps, on ne l'utilise donc plus
     * 
     * @param x1
     * @param y1
     * @param mapCoord
     * @return 
     */
     
    /**
     * Fonctionne avec les Maps, on ne l'utilise donc plus
     * @param nbFixations
     * @param model
     * @param humain
     * @param but
     * @return
     */
    public static double ecart(List<Mot> model, List<Mot> humain, Mot but){
        
        double result = 0;
        for (int i = 0; i < humain.size(); i++) {
            result += Math.abs(calcDistance(model.get(i), but) - calcDistance(humain.get(i), but));
        }
        double size = humain.size();
        return result/size;
    }
    /**
     * Recherche le mot le plus proche des coordonnées entrées en paramètre
     * @param x1
     * @param y1
     * @param listeMots
     * @return 
     */
    public static Mot motPlusProche(double x1, double y1, List<Mot> listeMots){
        double min = Double.MAX_VALUE;
        Mot retour = null;
        for (int i = 0; i < listeMots.size() ; i++) {

            double tmp;
            tmp = calcDistance(x1, y1, listeMots.get(i));
            if(tmp<min){
                min = tmp;
                retour = listeMots.get(i);
            }
        }
        return retour;
    }
    
    public static Mot motCorrespondant(String str, List<Mot> listeMots){
        for(Mot m:listeMots){
            if(m.nom.equals(str)){
                return m;
            }
        }
        System.out.println("Le mot n'existe pas");
        return null;
    }
    
    public static double calcDistance(double x1, double y1, Mot mot){
        
        Double x2 = mot.x;
        Double y2 = mot.y;
        return Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
    }
    
    public static double calcDistance(Mot courant, Mot get) {
        return calcDistance(courant.x, courant.y, get);
    }
    
    private static void calcSemantique(Mot courant, List<Mot> listeMots){
         for (Mot listeMot : listeMots) {
             if (listeMot.distanceActuelle < 0.3) {
                 listeMot.scoreSemantique += (courant.sem-0.3)*AUGMENTE_INFLUENCE_SEMENTIQUE;
             }
             //Plus la sémantique est forte plus la zone autour de ce mot aura un score élevé
             listeMot.augmenterScore(-courant.sem * listeMot.distanceActuelle * AUGMENTE_INFLUENCE_SEMDISTANCE);
             // Plus la sémantique est forte plus les mots à faible distance auront un score élevé
         }
    }
    
    private static void calcTaille(List<Mot> listeMots) {
         for (Mot listeMot : listeMots) {
             listeMot.augmenterScore(listeMot.taille / DIMINUE_INFLUENCE_TAILLE);
             //Plus la taille est grande plus le score du mot sera élevé
             listeMot.augmenterScore(-listeMot.distanceActuelle / DIMINUE_INFLUENCE_TAILDISTANCE);
             //Plus la distance est faible plus le score du mot sera élevé
         }
    }

    private static void calcMemoire(List<Mot> listeMots) {
         for (Mot listeMot : listeMots) {
             if (listeMot.mem == TAILLE_MEMOIRE) {
                 listeMot.augmenterScore(-1000);
             }
             // Le mot que l'on vient de voir a un score très faible
             if (listeMot.mem != 0) {
                 listeMot.mem--;
             }
             listeMot.augmenterScore(-listeMot.mem * 100);
             // Les mots que l'on a déjà vu ont un score plus faible (Plus il est récent plus son score est faible)
         }
        
    }
    
    public static Mot prochainMot(Mot courant,  List<Mot> listeMots)
    {
        courant.mem  = TAILLE_MEMOIRE;
         for (Mot listeMot : listeMots) {
             listeMot.setScoreActuel(listeMot.scoreSemantique);
             listeMot.setDistanceActuelle(calcDistance(courant, listeMot));
         }
         calcSemantique(courant, listeMots);
         calcTaille(listeMots);
         calcMemoire(listeMots);
         
         double max = Double.MIN_VALUE;
         Mot suivant = courant;
         
         for (Mot listeMot : listeMots) {
             if (listeMot.scoreActuel > max) {
                 max = listeMot.scoreActuel;
                 suivant = listeMot;
             }
         }
         return suivant;
    }
    
    
    public static void resetSemantique(List<Mot> li){
        for(Mot m:li){
            m.scoreSemantique=0;
            m.scoreActuel=0;
            m.mem=0;
        }
    }




}
