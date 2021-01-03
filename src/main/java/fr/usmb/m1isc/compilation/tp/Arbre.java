package fr.usmb.m1isc.compilation.tp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Arbre {
	public enum NodeType{ SEQUENCE, EXPRESSION, EXPR, VAR, INT, OUTPUT, INPUT, NIL}
	
	private String st;
	private Arbre fils_g;
	private Arbre fils_d;
	private NodeType type;
	private String expressionGauche;
	private String expressionDroite;
	public static int COMPTEUR = 0;
	
	public Arbre(NodeType type, String str) {
		this.type=type;
		this.st=str;
		this.fils_g = null;
		this.fils_d = null;
	}
	
	public Arbre(NodeType type, String str,  Arbre fils_g, Arbre fils_d ) {
		this.type=type;
		this.st=str;
		this.fils_g=fils_g;
		this.fils_d=fils_d;
	}
	
    @Override
    public String toString() {
        String str="";
        if (!((fils_g == null) && (fils_d == null))) {
            str = "(";
        }
        
        str=str.concat(st);

        if (fils_g != null) {
            str=str.concat(" " +fils_g.toString()+ " ");
        }
        if (fils_d != null) {
            str=str.concat(" " +fils_d.toString()+ " ");
        }
        if (!((fils_g==null) && (fils_d==null))) {
            str=str.concat(")");
        }
        return str;
    }
    
    public Set<String> getLet() {

        Set<String> s = new HashSet<String>();
        if ((this.type) == NodeType.EXPRESSION && (this.st) == "let") {
            s.add(this.fils_g.st);
        }
        Set<String> setLeft = this.fils_g != null ? this.fils_g.getLet() : null;
        Set<String> setRight = this.fils_d != null ? this.fils_d.getLet() : null;
        if (setLeft != null)
            s.addAll(setLeft);
        if (setRight != null)
            s.addAll(setRight);
        return s;
    }
    
    ArrayList<Arbre> parcours( ArrayList<Arbre> parcArbre) {
    	if (this.st == "let") {
    		if (this.fils_g != null) {
    			boolean doublon = false;
    			for (Arbre a : parcArbre) {
    				System.out.println(a.st);
    				System.out.println(this.fils_g.st);
    				if (a.st.equals(fils_g.st)) {
    					doublon = true;
    					System.out.println("validation comparaison");
    				}	
    				System.out.println("validation du foreach");
    			}
    			if (doublon == false) {
    				System.out.println(this.fils_g);
    				parcArbre.add(this.fils_g);
    			}
    		}
    		else {
    			System.out.println("erreur: fils gauche n'existe pas");
    		}
    	}
    	else {
    		if (this.fils_g != null) {
    			parcArbre = this.fils_g.parcours(parcArbre);
    		}
    		if (this.fils_d != null) {
    			parcArbre = this.fils_d.parcours(parcArbre);
    		}
    	}
    	return parcArbre;
    }
    
    public String generer() {
        switch (this.type) {
            case SEQUENCE:
                return genererExpr();
            case EXPRESSION:
                return genererExpression();
            case EXPR:
                return genererExpr();
            case VAR:
            case INT:
            	return "\t\tmov eax, " + this.st + "\n" +
                "\t\tpush eax\n";
            case OUTPUT:
                return genererExpr();
            case INPUT:
                return genererExpr();
            case NIL:
                return genererExpr();
            default:
                return genererExpr();
        }

    }
    
    
    
    public String genererExpression() {
        String res = "";
        int tempCpt = 0;
        switch (this.st) {
            case "let":
                String fils_droit = this.fils_d.generer();
                res +=
                        fils_droit +
                                "\t\tmov " + this.fils_g.st +
                                ", eax\n"
                ;
                break;
            case "while":
                tempCpt = COMPTEUR++;
                res += "\tetiq_debut_while_" + tempCpt + ":\n" +
                        this.fils_g.generer() +
                        "jz etiq_fin_while_" + tempCpt + "\n" +
                        this.fils_d.generer() +
                        "\t\tjmp etiq_debut_while_" + tempCpt + "\n" +
                        "\tetiq_fin_while_" + tempCpt + ":\n";
                break;
            case "if":
                tempCpt = COMPTEUR++;
                res += this.fils_g.generer() +
                        "\t\tjz etiq_if_sinon_" + tempCpt + "\n" +
                        this.fils_d.fils_g.generer() +
                        "\t\tjmp etiq_if_fin_" + tempCpt + "\n" +
                        "\tetiq_if_sinon_" + tempCpt + ":\n" +
                        (this.fils_d.fils_d == null ? "" : this.fils_d.fils_d.generer()) +
                        "\tetiq_if_fin_" + tempCpt + ":\n";
                break;
        }


        return res;
    }
    
    public String genererExpr() {
    	String res="";
    	String expressionGauche;
        String expressionDroite;
    	int tempCpt = 0;
    	Arbre temp;
    	switch(this.st) {
    	case "+":
    		expressionGauche =this.fils_g.generer();
    		expressionDroite =this.fils_d.generer();
    		res+=expressionGauche+expressionDroite+
    						"\t\tpop ebx\n"+
    						"\t\tpop eax\n"+
    						"\t\tadd eax, ebx\n" +
    						"\t\tpush eax\n";
    		break;
    	case "-":
    		expressionGauche =this.fils_g.generer();
    		expressionDroite =this.fils_d.generer();
    		res+=expressionGauche+expressionDroite+
    						"\t\tpop ebx\n"+
    						"\t\tpop eax\n"+
    						"\t\tadd eax, ebx\n" +
    						"\t\tpush eax\n";
    		break;
    	case "*":
    		expressionGauche =this.fils_g.generer();
    		expressionDroite =this.fils_d.generer();
    		res+=expressionGauche+expressionDroite+
    						"\t\tpop ebx\n"+
    						"\t\tpop eax\n"+
    						"\t\tadd eax, ebx\n" +
    						"\t\tpush eax\n";
    		break;
    	case "/":
    		expressionGauche =this.fils_g.generer();
    		expressionDroite =this.fils_d.generer();
    		res+=expressionGauche+expressionDroite+
    						"\t\tpop ebx\n"+
    						"\t\tpop eax\n"+
    						"\t\tadd eax, ebx\n" +
    						"\t\tpush eax\n";
    		break;
    	case "<":
            tempCpt = COMPTEUR++;
            temp = new Arbre(NodeType.EXPR, "-", this.fils_g, this.fils_d);
            res += temp.generer() +
                    "\t\tjl etiq_debut_lt_" + tempCpt + "\n" +
                    "\t\tmov temp,0\n" +
                    "\t\tjmp etiq_fin_lt_" + tempCpt + "\n" +
                    "\tetiq_debut_lt_" + tempCpt + ":\n" +
                    "\t\tmov temp,1\n" +
                    "\tetiq_fin_lt_" + tempCpt + ":\n";
            break;
        case "<=":
            tempCpt = COMPTEUR++;
            temp = new Arbre(NodeType.EXPR, "-", this.fils_g, this.fils_d);
            res += temp.generer() +
                    "\t\tjg etiq_debut_lte_" + tempCpt + "\n" +
                    "\t\tmov temp,0\n" +
                    "\t\tjmp etiq_fin_lte_" + tempCpt + "\n" +
                    "\tetiq_debut_lte_" + tempCpt + ":\n" +
                    "\t\tmov temp,1\n" +
                    "\tetiq_fin_lte_" + tempCpt + ":\n";
            break;
        case ">":
            tempCpt = COMPTEUR++;
            temp = new Arbre(NodeType.EXPR, "-", this.fils_d, this.fils_g);
            res += temp.generer() +
                    "\t\tjl etiq_debut_gt_" + tempCpt + "\n" +
                    "\t\tmov temp,0\n" +
                    "\t\tjmp etiq_fin_gt_" + tempCpt + "\n" +
                    "\tetiq_debut_gt_" + tempCpt + ":\n" +
                    "\t\tmov temp,1\n" +
                    "\tetiq_fin_gt_" + tempCpt + ":\n";
            break;
        case ">=":
            tempCpt = COMPTEUR++;
            temp = new Arbre(NodeType.EXPR, "-", this.fils_d, this.fils_g);
            res += temp.generer() +
                    "\t\tjg etiq_debut_gte_" + tempCpt + "\n" +
                    "\t\tmov temp,0\n" +
                    "\t\tjmp etiq_fin_gte_" + tempCpt + "\n" +
                    "\tetiq_debut_gte_" + tempCpt + ":\n" +
                    "\t\tmov temp,1\n" +
                    "\tetiq_fin_gte_" + tempCpt + ":\n";
            break;
        case "and":
            tempCpt = COMPTEUR++;
            expressionGauche = this.fils_g.generer();
            expressionDroite = this.fils_d.generer();
            res +=
                    expressionGauche +
                            "\t\tjz etiq_debut_and_" + tempCpt + "\n" +
                            expressionDroite
                            + "\tetiq_fin_and_" + tempCpt + ":\n";
            break;
    	}
    	return res;
    }
    
}
