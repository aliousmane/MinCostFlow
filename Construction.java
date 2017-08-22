/**  configuration du graphe pour que les ascenceurs partent chaque 1 min à la sortie comme à l'entrée
*    1441 noeuds à l'entrée comme à la sortie
*  les liaisons inter node se font entre un noeud et le noeud qui le suit 0-1,1-2,2,3 etc 
*  le coût de passage d'un chariot d'un noeud à l'autre est 1 
*   Capacité des monte charges: entrée=2 sortie=1
* Introduction du chariot de salle
*Modification du fichier de sortie pour que ce soit compatible avec le format dimacs
*/

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.Collections;

public class Construction
{
	//Classe pour construire le graphe et charger les operations
	GrapheListe g;
	ArrayList<Node> noeuds;
	int nbnoeuds,nbcart;
	String chaine;
	int iter;
	ArrayList<Integer> sequence;
	public Construction(String chaine,int capacite,int capacite2,int num,int cost) throws Exception
	{
		this.chaine=chaine;
		iter=num;
		noeuds=new ArrayList<Node>();
		sequence=new ArrayList<Integer>();
		CreateNode();
		g=new GrapheListe(nbnoeuds);
		CreateArc(capacite,capacite2,cost);
		Number_cart();
		
		System.out.println(nbcart+" "+nbnoeuds+" "+g.getNbarcs());
		print_graph();
		
		
	}
	public void CreateNode() throws Exception
	{
		int id=0;
		/*Noeud de depart: URDM-OUT
		id=0;
		type=0;
		time=0;
		*/
		int j=0;
		//j=id++;
		noeuds.add(new Node(id++,0,0));
       
		/* Monte charge servant les salles d'operation
		id: 1--?
		type:1
		time:0-? un monte charge part chaque  min à partir de 0h
		
		*/
		for(int i=0;i<1441;i++)
		{
			noeuds.add(new Node(id++,1,i));
			
		}
		
		
		/* Entrées des salles d'operation
		type:2
		Lecture des opérations dans le fichier
		*/
		id= ChargerNode(2,4,id);
		
		/* Sorties des salles d'operation
		type:3
		time:
		Lecture des opérations dans le fichier
		*/
		id= ChargerNode(3,5,id);

		/* Monte charge de sortie
		id:
		type:4
		time:0-1440
		1441 noeuds au total
		*/
		for(int i=0;i<1441;i++)
		{
			noeuds.add(new Node(id++,4,i));
		}
		
		/*Noeud de d'arrivée: URDM-IN
		id:
		type=5;
		*/
		noeuds.add(new Node(id,5));
				
		nbnoeuds=id+1;
	
	}
	public void CreateArc(int c,int c2,int cost) throws Exception
	{
		g=new GrapheListe(this.nbnoeuds);
		int i;
		// Arcs URDM OUT-monte charges Out
                
		for(i=0;i<1441;i++)
		{
			g.Ajouter(new Arc(noeuds.get(0),noeuds.get(i+1),0,0,c));
		}
		//System.out.println(g.getNbarcs());
		//System.out.println(g.toString());
		//Arcs entre les monte charges OUT
		for(Node n: noeuds)
			for(Node n1: noeuds)
			{
				if(n1.getTime()== n.getTime()+1 && n.getType()==1 && n1.getType()==1)
					g.Ajouter(new Arc(n,n1,cost,0,10000));
			}
			//System.out.println(g.getNbarcs());
		// Arcs entre les monte charges OUT et les salles d'operation
		for(Node n: noeuds)
			for(Node n1: noeuds)
			{
				if(n.getType()==1 && n1.getType()==2 && n.getTime()<n1.getTime()-15)
					g.Ajouter(new Arc(n,n1,0,0,10000));
			}
			//System.out.println(g.getNbarcs());
		//Arcs entre les salles d'opérations
		ChargerArc(g,noeuds);
		System.out.println(g.getNbarcs());
		// Arcs entre deux operations qui se suivent et se deroulent dans la même salle 
		    // Determiner liste des salles
			
			// Determiner les operations qui se déroulent dans la même salle et les mettre dans un tableau
			// ensuite trier ces opérations par ordre d'heure de début d'opération
			int []room1= {105,106,107,110,113,114,115,116,205,206,207,208,209,210,211,212,101,102,103,104,108,109,111,112,201,202,203,204,213,214};			
			CreationArc(g,room1,2,3);
			//System.out.println(g.getNbarcs());			
		//Arcs entre la sortie de la salle d'op et le monte charge souillé
		for(Node n: noeuds)
			for(Node n1: noeuds)
			{
				if(n.getType()==3 && n1.getType()==4 && n1.getTime()==n.getTime()+15)
					g.Ajouter(new Arc(n,n1,0,0,10000));
			}
			//System.out.println(g.getNbarcs());
		//Arcs entre les monte charges IN 
		for(Node n: noeuds)
			for(Node n1: noeuds)
			{
				if(n1.getTime()== n.getTime()+1 && n.getType()==4 && n1.getType()==4)
					g.Ajouter(new Arc(n,n1,cost,0,10000));
			}
			//System.out.println(g.getNbarcs());
		//Arcs entre les monte charges IN- URDM-IN
		for(Node n: noeuds)
		{
			if(n.getType()==4)
				g.Ajouter(new Arc(n,noeuds.get(nbnoeuds-1),0,0,c2));
		}
		//System.out.println(g.getNbarcs());

	}
	private  void CreationArc(GrapheListe g,int []room,int type_in,int type_out)
	{
		ArrayList<Node> listeNode;
		for(int iter=0;iter<room.length;iter++)
		{
			listeNode=new ArrayList<Node>();
			for(Node n:noeuds)
				if(n.getType()==type_in&&n.getRoom()==room[iter])
					listeNode.add(n);
			
			//System.out.println(listeNode.size());
			//tri de la liste listeNode
			Node n_1= new Node(0,0);
			Collections.sort(listeNode,n_1);
				
						
			for(int ind=0;ind<listeNode.size()-1;ind++)
			{

				for(Node n:noeuds)
 				{
					if(n.getType()==type_out && g.Existe(listeNode.get(ind).getId(),n.getId()))
 						g.Ajouter(new Arc(n,listeNode.get(ind+1),0,1,1));
				}
			}
		}
		
	}
	private void Number_cart() throws IOException
	{
		BufferedReader br=null;
		String ligne;
		try
		{
			File fichier= new File(this.chaine);
			FileReader fr=new FileReader(fichier);
			br=new BufferedReader(fr);

		}
		catch(FileNotFoundException e)
		{
			 System.out.println("erreur d'ouverture du fichier");
		}
		ligne=br.readLine();
		while((ligne=br.readLine())!=null)
		{

			String [] tab= ligne.split(",");
			nbcart +=Integer.parseInt(tab[6]);
		}
		
	}
	private int ChargerNode(int type,int pos,int id) throws IOException
	{
		
		int pos_room=1;
		ArrayList<Integer> Arrivee_MC=new ArrayList<Integer>();
		ArrayList<Integer> nbchariot=new ArrayList<Integer>();
		sequence.clear();
		BufferedReader br=null;
		String ligne;
		try
		{
			File fichier= new File(this.chaine);
			FileReader fr=new FileReader(fichier);
			br=new BufferedReader(fr);

		}
		catch(FileNotFoundException e)
		{
			 System.out.println("erreur d'ouverture du fichier");
		}
		
		ligne=br.readLine();
		int op_no=1;
		while((ligne=br.readLine())!=null)
		{

			String [] tab= ligne.split(",");
			noeuds.add(new Node(id++,type,Integer.parseInt(tab[pos]),op_no,Integer.parseInt(tab[pos_room])));
			Arrivee_MC.add(Integer.parseInt(tab[7]));
			nbchariot.add(Integer.parseInt(tab[6]));
			op_no++;
		}
		br.close();
		// ordonner les chariots selon leur heure d'arrivee au monte charge de sortie et en 
		//fonction du nombre de chariot.
		int i=0;
		for(int arrivee: Arrivee_MC)
		{
			for(int j=0;j<nbchariot.get(i);j++)
				sequence.add(arrivee);
			i++;
		}
			
		return id;
	}
	private void ChargerArc(GrapheListe g,ArrayList<Node> noeuds) throws IOException
	{

		BufferedReader br=null;
		String ligne;
		try
		{
			File fichier= new File(this.chaine);
			FileReader fr=new FileReader(fichier);
			br=new BufferedReader(fr);

		}
		catch(FileNotFoundException e)
		{
			 System.out.println("erreur d'ouverture du fichier");
		}
		
		ligne=br.readLine();
		for(Node n: noeuds)
			for(Node n1: noeuds)
			{
				if(n.getType()==2 && n1.getType()==3 && n.getOp()==n1.getOp())
				{
					ligne=br.readLine();
					if(ligne==null) break;
					//System.out.println(ligne);
					String [] tab= ligne.split(",");
					g.Ajouter(new Arc(n,n1,0,Integer.parseInt(tab[6]),Integer.parseInt(tab[6])));
				}
			}
		br.close();

	}
	private void print_graph() throws Exception
	{
		BufferedWriter bw = null;
		BufferedWriter bw1 = null;
        BufferedWriter bw2 = null;
		try 
		{
			 File file = new File("Input/Input"+iter+".txt");
			 File file1 = new File("Sequence/sequence"+iter+".csv");
			 File file2 = new File("Log"+iter+".csv");
		
		
				FileWriter fw = new FileWriter(file);
				bw = new BufferedWriter(fw);
				FileWriter fw1 = new FileWriter(file1);
				bw1 = new BufferedWriter(fw1);
                                FileWriter fw2 = new FileWriter(file2);
				bw2 = new BufferedWriter(fw2);
				// Ecriture dans le fichier des arcs
				// format d'ecriture: (de,vers,lb,ub,cost)
				String mycontent=" p min "+ nbnoeuds+" "+g.getNbarcs()+"\n";
				mycontent+="n "+(noeuds.get(0).getId()+1)+" "+nbcart+"\n";
				for(int i=1;i<nbnoeuds-1;i++)
				{
					
					mycontent+="n "+(noeuds.get(i).getId()+1)+" 0"+"\n";
				}
				mycontent+="n "+(noeuds.get(nbnoeuds-1).getId()+1)+" -"+nbcart+"\n";
				
				//mycontent += "Node De,Node Vers, LB,UB,Cost \n";
				String content2="Simulation day"+iter+"\n";
                                content2+="De:id,De:type,De:Operation,De:Time,Vers:id,Vers:type,Vers:Operation,Vers:Time"+"\n";
                for(int i=0;i<nbnoeuds;i++)
                	for(Arc a : g.Adjacents(i))
					 {	mycontent += "a "+(a.getDe().getId()+)1+","+(a.getVers().getId()+1)+","+
					 	 	a.getLb()+","+a.getUb()+","+a.getCost()+"\n";
						content2 +=(a.getDe().getId()+1)+","+(a.getDe().getType()+1)+","+a.getDe().getOp()+","+a.getDe().getTime()+","+a.getVers().getId()+
							","+a.getVers().getType()+","+a.getVers().getOp()+","+a.getVers().getTime()+"\n";
					}
					 
                  System.out.println(mycontent);      
                                bw.write(mycontent);
                                //bw2.write(content2);
				String content="Arrivee au MC"+"\n";
				for(int i=0;i<sequence.size();i++)
                                    content+=sequence.get(i)+"\n";
                               // bw1.write(content);
                                
				System.out.println("File "+iter+" written Successfully");
			

		} catch (IOException ioe) 
		{
			ioe.printStackTrace();
		}
		finally
		{ 
			try
			{
				if(bw!=null)
					bw.close();
				if(bw1!=null)
					bw1.close();
                                if(bw2!=null)
					bw2.close();
			}catch(IOException ex)
			{
				System.out.println("Error in closing the BufferedWriter"+ex);
			}
		}
		
   }
		
        @Override
	public  String toString()
	{

            String chaine;
            chaine = g.toString();
            for(Node n: noeuds)
                chaine += "Noeud : "+ n.getId()+" \n";
		
            return chaine;
	}
	public static void main(String [] args) throws Exception
	{
		
		int capacite=2;
		int capacite2=1;
        int cost=1;
		Construction c;
		String chaine= "Input/Fichier";
		for(int i=0;i<1;i++)
		{
			if(34==i) continue;
			c = new Construction(chaine+(i+1)+".csv",capacite,capacite2,i+1,cost);
			//System.out.println(c.toString());
		}
		

	}
}
