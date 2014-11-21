package modcognitive;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alizée ARNAUD, Jordan DAITA
 */
public class ModCognitive {
    public static final int TAILLE_MEMOIRE=7;
    public static void main(String[] args) throws IOException {

        Datas data = new Datas(40);
        File fCoord = new File("datas/Coordonnees/recipsem_coord.txt");
        File fSem = new File("datas/Semantique/recipsem_cos.txt");
        File fTaille = new File("datas/TailleMots/recipsem_tailleV3.txt");
        data.extractDonnees(fSem, Datas.typeSem);
        data.extractDonnees(fTaille, Datas.typeTaille);
        data.extractDonnees(fCoord, Datas.typeCoord);
        
        Calc calc = new Calc();
        
        List<Mot> parcourt = new ArrayList<>();
       
        Mot debut = calc.motPlusProche(-0.8, 0.74, data.listeMots);
        parcourt.add(debut);
        for (int i = 0; i < 20; i++) {
            parcourt.add(calc.prochainMot(parcourt.get(i), data.listeMots));
        }
        
        System.out.println(parcourt);
        


    }
    
}
