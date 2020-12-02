package fr.usmb.m1isc.compilation.tp;


public class Arbre {
	public enum NodeType{ SEQUENCE, EXPRESSION, EXPR, VAR, INT, OUTPUT, INPUT, NIL; }
	
	private String st;
	private Arbre fils_g;
	private Arbre fils_d;
	private NodeType type;
	
	public Arbre(NodeType type, String str) {
		this.type=type;
		this.st=str;
	}
	
	public Arbre(NodeType type, String st,  Arbre fils_g, Arbre fils_d ) {
		this.type=type;
		this.st=st;
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
}
