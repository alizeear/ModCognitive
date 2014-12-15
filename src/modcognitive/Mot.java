/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modcognitive;

/**
 *
 * @author Alizée ARNAUD, Jordan DAITA
 */
public class Mot {
    protected String nom;
    protected double sem;
    protected double x,y;
    protected double mem;
    protected double taille;
    protected double scoreActuel;
    protected double scoreSemantique;
    protected double distanceActuelle;

    
    Mot(String nom){
        this.nom = nom;
        scoreActuel = 0;
        distanceActuelle = 0;
        scoreSemantique = 0;
    }
    
    public void setDistanceActuelle(double distanceActuelle) {
        this.distanceActuelle = distanceActuelle;
    }
    
    public void setScoreActuel(double scoreActuel) {
        this.scoreActuel = scoreActuel;
    }

    public void setSem(double sem) {
        this.sem = sem;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setMem(int mem) {
        this.mem = mem;
    }

    public void setTaille(double taille) {
        this.taille = taille;
    }
    
    public void augmenterScore(double val){
        scoreActuel+=val;
    }

    @Override
    public String toString() {
        return "Mot{" + "nom=" + nom + ", sem=" + sem + ", x=" + x + ", y=" + y + ", mem=" + mem + ", taille=" + taille + "}\n";
    }
    
}
