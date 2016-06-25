/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_quiestce;

import java.util.Objects;

/**
 *
 * @author clecann
 */
public class Caracteristiques {

    private String libelle;
    private String valeur;

    public Caracteristiques(String libelle, String valeur) {
        this.libelle = libelle;
        this.valeur = valeur;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getValeur() {
        return valeur;
    }

    @Override
    public String toString() {
        return (this.libelle + " = " + this.valeur);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Caracteristiques other = (Caracteristiques) obj;
        if (!Objects.equals(this.libelle, other.libelle)) {
            return false;
        }
        if (!Objects.equals(this.valeur, other.valeur)) {
            return false;
        }
        return true;
    }

}
