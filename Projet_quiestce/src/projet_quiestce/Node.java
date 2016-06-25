/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_quiestce;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author mlardeux
 */
public class Node {

    public String nom;
    private BaseDD list;
    public ArrayList<Caracteristiques> branche;
    public ArrayList<Node> fils;

    public Node(BaseDD list) {
        this.list = list;
        this.nom = "";
        this.branche = new ArrayList<>();
        this.fils = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public BaseDD getList() {
        return list;
    }

    public ArrayList<Caracteristiques> getBranche() {
        return branche;
    }

    public ArrayList<Node> getFils() {
        return fils;
    }
    /**
     * methode creant l'abre grace à une base de donnée et à l'entropie de shannon
     * si la liste contenue dans le noeud est vide on renvoie un noeud vide
     * si la liste contenue dans le noeud possede 1 élément
     *      alors on renvoie un noeud avec le nom du perso en nom de neoud et le reste vide
     * sinon on trouve l'indice de la colonne la plus discrminante
     *      on constuit une arraylist de branche et de noeud tel que les indices correspondent
     */

    public void creeArbre() {

        if (this.list.getBase().size() == 0) {
            this.nom = "";
            this.list = new BaseDD();
            this.branche = new ArrayList<Caracteristiques>();
            this.fils = new ArrayList<Node>();
        }

        else if (this.list.getBase().size() == 1) {
            this.nom = this.list.getBase().get(0).getPrenom();
            this.branche = new ArrayList<Caracteristiques>();
            this.fils = new ArrayList<Node>();
        } else {
            int colonneDis = this.list.entroShannonColonne()[0];
            ArrayList<Caracteristiques> branche = this.list.caractList(colonneDis);
            ArrayList<Node> listNoeud = new ArrayList<>();
            for (Caracteristiques k : branche) {
                BaseDD liste = (BaseDD) this.list.clone();
                liste.rechercheOui(colonneDis, k.getValeur());
                Node noeud2 = new Node(liste);
                noeud2.creeArbre();
                listNoeud.add(noeud2);
            }
            this.branche = branche;
            this.nom = this.branche.get(0).getLibelle();
            this.fils = listNoeud;
        }

    }

    /**
     methode qui trouve la question correspondant au noeud ou la methode est appliquée
     */
    public String trouveQ(ArrayList<String[]> question) {
        boolean place = false;
        int i = 0;
        String solut = "";
        while (place == false && i < question.size()) {
            if (question.get(i)[0].equals(this.nom)) {
                place = true;
                solut = question.get(i)[1];
            }
            i++;
        }
        return (solut);
    }

    /* *
     * fonction recursive qui pose la question par fenetre dialogue composée d'un combobox
     * elle compare la reponse au nom de branche, puis donne l'indice de la branche qui correspond
     * comme  branches et noeuds ont le meme indice on reaplique la fonction au noeud suivant
     * si la liste contenue dans le noeud a 1 element alors le noeud est terminal 
     *      donc on affiche le nom
     * sinon
     *       trouve l'indice dont la branche correspond à la reponse
     *       on applique au noeud suivant
     *       si reponse != null (fenetre non fermée) => on continue le programme
     */
    public void poseQ(ArrayList<String[]> question) {
        if (this.list.getBase().size() == 1) {
            ImageIcon img2 = new ImageIcon("aki_triomphe.png");
            int reponse = JOptionPane.showConfirmDialog(null,
                    "Votre personnage est " + this.list.getBase().get(0).getPrenom() + " ?",
                    "AbraKadabra ! ",
                    JOptionPane.YES_NO_OPTION);
            if (reponse == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null,
                        "Génial j'ai encore trouvé !",
                        "AbraKadabra ! ",
                        JOptionPane.INFORMATION_MESSAGE,
                        img2);
            } else {

                FenetreAjoutPerso ajout = new FenetreAjoutPerso();
                ajout.generer();
            }
        } else {
            if (this.list.getBase().size() > 1) {
                JOptionPane jop = new JOptionPane();
                String[] nomBranches = new String[this.branche.size()];
                for (int k = 0; k < this.branche.size(); k++) {
                    nomBranches[k] = this.branche.get(k).getValeur();
                }
                ImageIcon img = new ImageIcon("akinator2.png");
                String reponse = (String) jop.showInputDialog(null,
                        this.trouveQ(question),
                        "Question",
                        JOptionPane.QUESTION_MESSAGE,
                        img,
                        nomBranches,
                        nomBranches[0]);

                int i = 0;
                Boolean trouve = false;
                int indiceSuivant = 0;
                while (reponse != null && !trouve && i < this.branche.size()) {
                    if (reponse.equals(this.branche.get(i).getValeur())) {
                        indiceSuivant = i;
                        trouve = true;
                    } else {
                        i++;
                    }
                }
                if (reponse != null) {
                    this.fils.get(indiceSuivant).poseQ(question);
                }
            }
        }
    }


    /**
     * affiche l'arbre dans la fenetre de dialogue netbeans
     */
    public void affiche(int indentation) {
        System.out.print(this.nom + '\n');
        String arbre = "";
        for (int k = 0; k < indentation; k++) {
            arbre += " ";
        }
        int i = 0;
        while (this.branche != null && i < this.branche.size()) {
            System.out.print(arbre + branche.get(i).getValeur() + " : ");
            fils.get(i).affiche(indentation + this.branche.get(i).getValeur().length() + 3);
            i++;
        }
    }

}
