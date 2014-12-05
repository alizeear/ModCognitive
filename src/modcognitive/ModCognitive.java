package modcognitive;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alizée ARNAUD, Jordan DAITA
 */

public class ModCognitive {
    public static void main(String[] args) throws IOException {

        Datas data = new Datas(40);
        File fCoord = new File("datas/Coordonnees/alim1sem_coord.txt");
        File fSem = new File("datas/Semantique/alim1sem_cos.txt");
        File fTaille = new File("datas/TailleMots/alim1sem_tailleV3.txt");
        File fHumain = new File("datas/Humains/dataHumains.txt");
        data.fixationsHumain(fHumain, "32", "alim1");
        data.extractDonnees(fSem, Datas.typeSem);
        data.extractDonnees(fTaille, Datas.typeTaille);
        data.extractDonnees(fCoord, Datas.typeCoord);
        int nbFixation = data.coordonneesHumains.size();
        
        
        Calc calc = new Calc();
        
        Mot but = calc.motPlusProche(-0.53,-0.44, data.listeMots);
        List<Mot> modele = new ArrayList<>();
        List<Mot> humain = new ArrayList<>();
        
        Mot debut = calc.motPlusProche(0.8,-0.74, data.listeMots);
        Mot motHumain;
        for(Coordonnees co:data.coordonneesHumains){
            motHumain = calc.motPlusProche(co.x, co.y, data.listeMots);
            humain.add(motHumain);
        }
        modele.add(debut);
        for (int i = 0; i < nbFixation; i++) {
            modele.add(calc.prochainMot(modele.get(i), data.listeMots));
        }
        System.out.println(humain);
        System.out.println(modele);
        
        double ecart = calc.ecart(nbFixation, modele, humain, but);
        System.out.println(ecart);

    }
    
}
