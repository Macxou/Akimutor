/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_quiestce;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author clecann
 */
public class Personnage {
       
    private ArrayList <Caracteristiques> descriptif;
    private String prenom;

    public Personnage(String prenom) {
        this.descriptif = new ArrayList <Caracteristiques> ();
        this.prenom = prenom;
    }

    public ArrayList<Caracteristiques> getDescriptif() {
        return descriptif;
    }

    public String getPrenom() {
        return prenom;
    }
    
    @Override
    public String toString() {
        return ('\n' + prenom + ", descriptif=" + descriptif);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Personnage other = (Personnage) obj;
        if (!Objects.equals(this.descriptif, other.descriptif)) {
            return false;
        }
        return true;
    }
    
    public void addCaract(Caracteristiques c){
        this.descriptif.add(c); 
    }
    
    
    
    
    
}
