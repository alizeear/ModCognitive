package modcognitive;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Alizée ARNAUD, Jordan DAITA
 */
public class ModCognitive {

    private static HashMap<String, ArrayList> mapSem;
    private HashMap<String, ArrayList> mapMem;
    private HashMap<String, ArrayList> mapVisu;
 
    public static HashMap<String, ArrayList> extractDonnees(File fichier, int nbMots) throws FileNotFoundException, IOException {
        
        HashMap<String, ArrayList> map = new HashMap<>();   
        String key;
        String value;
        String ligne;
        int count = 0;
        
        FileReader lecteurDeFichier = new FileReader(fichier);
        BufferedReader buff = new BufferedReader(lecteurDeFichier);
        
        while (count < nbMots) {
            ArrayList<Double> listValues = new ArrayList<>();
            ligne = buff.readLine();
            java.util.StringTokenizer coupeur = new java.util.StringTokenizer(ligne, " ");
            key = coupeur.nextToken();
            
            while (coupeur.hasMoreElements()) {
                value = coupeur.nextToken();
                listValues.add(Double.parseDouble(value));
            };
            
            map.put(key, listValues);
            count++;
        }
        
        buff.close();
        return map;
    }
    
    public static void main(String[] args) throws IOException {

        File fichier = new File("D:/MASTER WIC/Modélisation Cognitive Computationnelle/TPWIC/Semantique/sportsem_cos.txt");
        int nbMots = 40;
        String keyWord = "antenne";
        mapSem = new HashMap<>();
        
        mapSem = extractDonnees(fichier, nbMots);
        System.out.println(mapSem);
        System.out.println(mapSem.get(keyWord).get(0));  

    }
    
}
