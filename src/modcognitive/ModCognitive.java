package modcognitive;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author Alizée ARNAUD, Jordan DAITA
 */

public class ModCognitive {
    private final static String NOMCARTE="alim1";  
    private final static String BONNEREPONSE="confiture";  
    private final static double XDEPART=0.8;
    private final static double YDEPART=-0.74;
        public static void main(String[] args) throws IOException {

        Datas data = new Datas(40);
        String nomCarte = NOMCARTE; 
        File fCoord = new File("datas/Coordonnees/"+nomCarte+"sem_coord.txt");
        File fSem = new File("datas/Semantique/"+nomCarte+"sem_cos.txt");
        File fTaille = new File("datas/TailleMots/"+nomCarte+"sem_tailleV3.txt");
        File fHumain = new File("datas/Humains/dataHumains.txt");
        data.extractDonnees(fSem, Datas.typeSem);
        data.extractDonnees(fTaille, Datas.typeTaille);
        data.extractDonnees(fCoord, Datas.typeCoord);
        data.allFixationsHumain(fHumain);
        
        Mot but = Calc.motCorrespondant(BONNEREPONSE, data.listeMots);
        List<Mot> modele = new ArrayList<>();
        Mot debut = Calc.motPlusProche(XDEPART,YDEPART, data.listeMots);
        
        /*
            ------------------------------------------------------------------------------------------------------------------------------
            Le code suivant permet de calculer l'écart entre le modèle et chacun des participants
            pour une carte donnée sans variation de paramètre
            Il affiche également les parcours de chaque humain et du modèle
            ------------------------------------------------------------------------------------------------------------------------------
        */
        
            for(Entry<String, List<Mot>> entry : data.mapHumain.entrySet()) {
                String key = entry.getKey();
                List<Mot> value = entry.getValue();
                if(key.contains(nomCarte)){
                    Calc.resetSemantique(data.listeMots);
                    modele.clear();
                    modele.add(debut);
                    for (int j = 0; j < value.size(); j++) {
                        modele.add(Calc.prochainMot(modele.get(j), data.listeMots));
                    }
                    System.out.println("----------------Humain : "+ key+"---------------");
                    System.out.println("Ecart avec le modèle : "+ Calc.ecart(modele, value, but)+"\n");
                    System.out.println(value);
                }
            }
            System.out.println("--------------Modele--------------------");
            System.out.println(modele);
        /*
        
            ------------------------------------------------------------------------------------------------------------------------------
            Le code suivant permet d'enregistrer dans un fichier au format CSV les écarts entre le modèle et chaque Humain 
            pour une carte donnée avec un paramètre qui varie de 1 à 30    
            ------------------------------------------------------------------------------------------------------------------------------
            
            
        FileWriter fw1;
        BufferedWriter out1;
        fw1 = new FileWriter("src/datas/"+nomCarte+".csv");
        out1 = new BufferedWriter(fw1);
        try{
            out1.write("Nom Carte;Numero Participant");
            out1.write("\n");
        }catch(IOException ioe){
                ioe.printStackTrace();
        }
        for(Entry<String, List<Mot>> entry : data.mapHumain.entrySet()) {
            String key = entry.getKey();
            List<Mot> value = entry.getValue();
            if(key.contains(nomCarte)){
                try{
                    out1.write(NOMCARTE+";"+key+";");
                }catch(IOException ioe){
                        ioe.printStackTrace();
                }
                for (int i = 1; i <= 30; i++) {
                    Calc.TAILLE_MEMOIRE = i;
                    Calc.resetSemantique(modele);
                    modele.clear();
                    modele.add(debut);
                    for (int j = 0; j < value.size(); j++) {
                        modele.add(Calc.prochainMot(modele.get(j), data.listeMots));
                    }
                    try{
                        out1.write(Calc.ecart(modele, value, but)+";");
                        }catch(IOException ioe){
                             ioe.printStackTrace();
                      }
                        System.out.println("valeur "+Calc.TAILLE_MEMOIRE+" sujet : "+key+" écart : "+Calc.ecart(modele, value, but));
                }
                out1.write("\n");
            }
        }
        out1.flush();
        out1.close();
                */
    }
    
}
