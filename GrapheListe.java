/*
	Representation d'un graphe sous la forme 
	de listes d'adjacence
	
	Chaque noeud a une liste chainee qui contient
	les arcs sortants
*/

import java.lang.Iterable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.UnsupportedOperationException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class GrapheListe implements IGraphe
{
	
	private int nbnoeuds, nbarcs;
	private ArrayList< LinkedList<Arc> > arcs;
	
	public GrapheListe(int noeuds)
	{
		nbnoeuds = noeuds;
		arcs = new ArrayList< LinkedList<Arc> >();
		for(int i=0;i<nbnoeuds;i++)
			arcs.add(new LinkedList<Arc>());
	}
	
	public int NbNoeuds(){return nbnoeuds;}
	public int NbArcs(){return nbarcs;}
	
	
	public void Ajouter(Arc a)
	{
		arcs.get(a.getDe().getId()).addFirst(a);
		nbarcs++;
		
		
	}
	
	public int getNbarcs(){ return nbarcs;}
	
	public void Retirer(Arc a)
	{
		new UnsupportedOperationException("La methode doit etre implementee");
	}
	
	//retour vrai si l'arc de i a j existe
	public boolean Existe(int i, int j)
	{
		for(Arc a : arcs.get(i))
			if(a.getVers().getId() == j)
				return true;
		return false;
	}
	
	public Arc TrouverArc(int i, int j)
	{
		for(Arc a : arcs.get(i))
			if(a.getVers().getId() == j)
				return a;
		return null;
	}
	
	//retourne un objet contenant tous les arcs sortant de i
	public Iterable<Arc> Adjacents(int i)
	{
		return arcs.get(i);
	}
	
        @Override
	public String toString()
	{
            String s;
            s = "Graphe noeuds:" + nbnoeuds + " arcs:" + nbarcs + "\n";
		for(int i=0;i<nbnoeuds;i++)
                    for(Arc a : Adjacents(i))
                        if(a.getDe().getId() < a.getVers().getId()) 
                            s += a + "\n";
		return s;
	}
	
	
}




