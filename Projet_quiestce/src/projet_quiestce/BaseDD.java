/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_quiestce;

import Clavier.Clavier;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author clecann
 */
public class BaseDD {

    private ArrayList<Personnage> base;

    public BaseDD() {
        this.base = new ArrayList<Personnage>();
    }

    public BaseDD(ArrayList<Personnage> base) {
        this.base = base;
    }

    public ArrayList<Personnage> getBase() {
        return this.base;
    }

    /**
     * methode qui prend en argument le nom du fichier ou se situe la base de donnée
     * et modifie la base de this
     */
    public void lireFichier(String nomFichier) {
        try {
            BufferedReader fichier = new BufferedReader(new FileReader(nomFichier + ".csv"));
            String ligne = fichier.readLine();
            String[] caract = ligne.split(";");
            while (fichier.ready()) {
                ligne = fichier.readLine();
                String[] lignes = ligne.split(";");
                Personnage p = new Personnage(lignes[0]);
                for (int k = 1; k < lignes.length; k++) {
                    Caracteristiques c = new Caracteristiques(caract[k], lignes[k]);
                    p.addCaract(c);
                }
                base.add(p);
            }
            fichier.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * methode qui ecrit la base de donnée dans un fichier dont on donne le nom
     */
    public void ecrireFichier(String nomDuFichier) {
        try {
            FileWriter fichier = new FileWriter(nomDuFichier);
            fichier.write("Nom;Sexe;Chapeau;Cheveux;Yeux;Lunettes;Barbe;Moustache;Nez;Bouche;Boucle d'oreille"); // pour ecrire la ligne des intitulés
            fichier.write(System.getProperty("line.separator"));
            for (Personnage k : this.base) {
                fichier.write(k.getPrenom() + ";");
                for (int i = 0; i < 9; i++) {
                    fichier.write(k.getDescriptif().get(i).getValeur() + ";");
                }
                fichier.write(k.getDescriptif().get(9).getValeur() + " ");
                fichier.write(System.getProperty("line.separator"));
            }
            fichier.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * methode qui ajoute un personnage à la base de donnée
     */
    public void addPerso(Personnage p) {
        this.base.add(p);
    }

    @Override
    public String toString() {
        return "BaseDD{" + "base=" + base + '}';
    }

    /**
     * methode qui modifie la base de donnée en la remplacant par une base
     * dont la j eme colonne contient le mot mot.
     */
    public void rechercheOui(int j, String mot) {
        ArrayList<Personnage> t = new ArrayList<>();
        for (Personnage k : this.base) {
            if (k.getDescriptif().get(j).getValeur().contains(mot)) {
                t.add(k);
            }
        }
        this.base = t;
    }

    /**
     * methode qui modifie la base de donnée en la remplacant par une base
     * dont la j eme colonne ne contient pas le mot mot.
     */
    public void rechercheNon(int j, String mot) {
        ArrayList<Personnage> t = new ArrayList<Personnage>();
        for (Personnage k : this.base) {
            if (!k.getDescriptif().get(j).getValeur().contains(mot)) {
                t.add(k);
            }
        }
        this.base = t;
    }

    public Object clone() {
        BaseDD clone = new BaseDD(this.base);
        return (clone);
    }

    /**
     * methode qui creer une arraylist de caracteristique, dont chaque caracteristique est presente une seule fois
     */
    public ArrayList<Caracteristiques> caractList(int i) {
        ArrayList<Caracteristiques> adj = new ArrayList<>();
        for (Personnage k : this.base) {
            Boolean place = false;
            int j = 0;
            while (j < adj.size() && !place) {
                if (k.getDescriptif().get(i).equals(adj.get(j))) {
                    place = true;
                }
                j++;

            }
            if (!place) {
                adj.add(k.getDescriptif().get(i));
            }
        }
        return (adj);
    }

    /**
     * methode qui prend en argument l'indice d'une colonne et qui renvoie un tableau de double de deux éléments
     * tel que le premier nombre soit l'entropie de la colonne 
     * et le deuxieme soit l'indice de la ligne tel que la caracteristique soit la plus discriminante
     */
    public double[] entroShannon(int i) {
        double[] tab = new double[2];
        ArrayList<Integer> compte = new ArrayList<>();
        ArrayList<Caracteristiques> adj = new ArrayList<>();
        for (Personnage k : this.base) {
            Boolean place = false;
            int j = 0;
            while (j < adj.size() && !place) {
                if (k.getDescriptif().get(i).equals(adj.get(j))) {
                    compte.set(j, compte.get(j) + 1);
                    place = true;
                }
                j++;

            }
            if (!place) {
                adj.add(k.getDescriptif().get(i));
                compte.add(1);
            }
        }
        double entropie = 0;
        double solution = 0;
        double p0 = 0;
        double total = this.base.size();
        for (int l = 0; l < compte.size(); l++) {
            entropie = entropie - (compte.get(l) / total) * (Math.log((compte.get(l) / total) / Math.log(2)));
            double pl = -(compte.get(l) / total) * (Math.log((compte.get(l) / total) / Math.log(2))) - (1 - (compte.get(l) / total)) * (Math.log((1 - (compte.get(l) / total)) / Math.log(2)));
            if (pl > p0) {
                solution = l;
                p0 = pl;
            }
        }
        tab[0] = entropie;
        tab[1] = solution; // entropie colone , caractéristique plus discriminante
        return (tab);
    }

    /**
     * methode qui renvoie un tableau d'int de deux éléments
     * tel que le premier int soit l'indice de la colonne la plus discrimiante par l'entropie de shannon
     * et le deuxieme soit l'indice de la ligne tel que la ce soit la caractéristique la plus discriminante dans la colonne
     */
    public int[] entroShannonColonne() {
        int[] tab = new int[2];
        ArrayList<double[]> list = new ArrayList<>();
        for (int k = 0; k < this.base.get(0).getDescriptif().size(); k++) {
            list.add(entroShannon(k));
        }
        int solution = 0;
        int rang = 0;
        if (list.size() != 1) {
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i)[0] > list.get(solution)[0]) {
                    solution = i;
                    rang = (int) list.get(i)[1];
                }
            }
        }
        tab[0] = solution;
        tab[1] = rang; // colonne +discriminate ; ligne +discriminante
        return (tab);
    }

    /**
     * methode qui simule un tour de jeu
     */
    public void tour() {
        int[] tab = this.entroShannonColonne();
        int i = tab[0];
        int j = tab[1];
        String mot = this.base.get(j).getDescriptif().get(i).getValeur();
        System.out.println("le personnage " + mot + " ?");
        String reponse = Clavier.getString();
        if (reponse.equals("non")) {
            this.rechercheNon(i, mot);
        }
        if (reponse.equals("oui")) {
            this.rechercheOui(i, mot);
        }
    }

    /**
     * methode qui lance le jeu en itératif
     */
    public void jouer() {
        while (this.base.size() > 1) {
            this.tour();
        }
        if (this.base.size() == 1) {
            System.out.println(this.base.get(0).getPrenom());
        } else {
            System.out.println(" vous n'avez pas choisit de personnage");
        }
    }
}
