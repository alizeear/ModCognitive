package modcognitive;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
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
        //data.fixationsHumain(fHumain, "41", "coul");
        data.extractDonnees(fSem, Datas.typeSem);
        data.extractDonnees(fTaille, Datas.typeTaille);
        data.extractDonnees(fCoord, Datas.typeCoord);
        data.allFixationsHumain(fHumain);
        
        Mot but = Calc.motCorrespondant(BONNEREPONSE, data.listeMots);
        List<Mot> modele = new ArrayList<>();
        Mot debut = Calc.motPlusProche(XDEPART,YDEPART, data.listeMots);
        
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
    }
    
}
