
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

	public Construction(String chaine,int capacite,int num) throws Exception
	{
		this.chaine=chaine;
		iter=num;
		noeuds=new ArrayList<Node>();
		CreateNode();
		CreateArc(capacite);
		Number_cart();
		System.out.println(nbcart+" "+nbnoeuds+" "+g.getNbarcs());
		print_graph();
		
		
	}
	//public Node(int id, int type,int time,int op_no=0,int num_salle=0)
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
		noeuds.add(new Node(id++,0,0,0,0));
		
		// 1er monte charge servant 16 salles
		/*
		id: 1-1441
		type:1
		time:0-1440
		1441 noeuds au total
		*/
		for(int i=0;i<1441;i++)
			noeuds.add(new Node(id++,1,i,0,0));
		
		/* 2e Monte charge servant 14 salles 
		id: 1442--2882
		type:2
		time:0-1440
		1441 noeuds au total
		*/
		for(int i=0;i<1441;i++)
		{
			noeuds.add(new Node(id++,2,i,0,0));
			
		}
		
		/* Entrées des  salles d'operation
		type:16
		Lecture des opérations dans le fichier
		*/
		
		id= ChargerNode(11,4,1,id);
	
		/* Entrées des 14 salles d'operation
		type:14
		Lecture des opérations dans le fichier
		*/
		//id= ChargerNode(14,4,1,id);
		
		/* Sorties des  salles d'operation
		type:161
		time:
		Lecture des opérations dans le fichier
		*/
		id= ChargerNode(22,5,1,id);

		/* Sorties des 16 salles d'operation
		type:141
		time:
		Lecture des opérations dans le fichier
		*/
		//id= ChargerNode(141,5,1,id);
		
		/* Monte charge de sortie
		id:
		type:3
		time:0-1440
		1441 noeuds au total
		*/
		for(int i=0;i<1441;i++)
		{
			noeuds.add(new Node(id++,3,i,0,0));
		}
		
		/*Noeud de d'arrivée: URDM-IN
		id:
		type=4;
		*/
		noeuds.add(new Node(id,4,0));
				
		nbnoeuds=id+1;
	
	}
	public void CreateArc(int c) throws Exception
	{
		g=new GrapheListe(this.nbnoeuds);
		int i;
		// Arcs URDM OUT- monte charges type 1 (desservant les 16 salles)
		for(i=0;i<1441;i++)
		{
			g.Ajouter(new Arc(noeuds.get(0),noeuds.get(i+1),0,0,c));
		} //ok
		//System.out.println(g.toString());
		// Arcs URDM OUT- monte charges type 2 (desservant les 14 salles)
		for(i=0;i<1441;i++)
		{
			g.Ajouter(new Arc(noeuds.get(0),noeuds.get(1442+i),0,0,c));
		}//ok
		//Arcs entre les monte charges de type 1
		for(Node n:noeuds)
			for(Node n1: noeuds)
				if(n.getType()==n1.getType()&& n.getType()==1&& n.getTime()==n1.getTime()-1)
					g.Ajouter(new Arc(n,n1,1,0,10000));
				//ok
		//Arcs entre les monte charges de type 2 
		for(Node n:noeuds)
			for(Node n1: noeuds)
				if(n.getType()==n1.getType()&& n.getType()==2&& n.getTime()==n1.getTime()-1)
					g.Ajouter(new Arc(n,n1,1,0,10000));
			//ok	
			int []room1= {105,106,107,110,113,114,115,116,205,206,207,208,209,210,211,212};
			int []room2= {101,102,103,104,108,109,111,112,201,202,203,204,213,214};			
		//Arcs entre le monte charge de type 1  et les 16 salles d'operation
		  set_type(g,room1,11,16);
		  set_type(g,room2,11,14);
		  set_type(g,room1,22,161);
		  set_type(g,room2,22,141);
		  
		for(Node n: noeuds)
			for(Node n1: noeuds)
			{
				if(n.getType()==1 && n1.getType()==16 && n.getTime()<=n1.getTime()-15)
					g.Ajouter(new Arc(n,n1,0,0,10000));
			}
			
			//Arcs entre le monte charge de type 2  et les 14 salles d'operation
		for(Node n: noeuds)
			for(Node n1: noeuds)
			{
				if(n.getType()==2 && n1.getType()==14 && n.getTime()<=n1.getTime()-15)
					g.Ajouter(new Arc(n,n1,0,0,10000));
			}
		//Arcs entre les 16 salles d'opération (in-out)
		ChargerArc(g,16,161);
		//Arcs entre les 14 salles d'opération (in-out)
		ChargerArc(g,14,141);	
		/*  // Arcs entre deux operations qui se suivent et se deroulent dans la même salle 
		    // Determiner liste des salles
			
			// Determiner les operations qui se déroulent dans la même salle et les mettre dans un tableau
			// ensuite trier ces opérations par ordre d'heure de début d'opération
			CreationArc(g,room1,16,161);
			CreationArc(g,room2,14,141);
		*/
		//Arcs entre la sortie de la salle d'op et le monte charge souillé
		for(Node n: noeuds)
			for(Node n1: noeuds)
			{
				if(n.getType()==161 && n1.getType()==3 && n1.getTime()==n.getTime()+15)
					g.Ajouter(new Arc(n,n1,0,0,10000));
			}
		for(Node n: noeuds)
			for(Node n1: noeuds)
			{
				if(n.getType()==141 && n1.getType()==3 && n1.getTime()==n.getTime()+15)
					g.Ajouter(new Arc(n,n1,0,0,10000));
			}
		//Arcs entre les monte charges IN 
		for(Node n: noeuds)
			for(Node n1: noeuds)
			{
				if(n1.getTime()== n.getTime()+1 && n.getType()==3 && n1.getType()==3)
					g.Ajouter(new Arc(n,n1,1,0,10000));
			}

		//Arcs entre les monte charges IN- URDM-IN
		for(Node n: noeuds)
		{
			if(n.getType()==3)
				g.Ajouter(new Arc(n,noeuds.get(nbnoeuds-1),0,0,c));
		}

	}
	private  void set_type(GrapheListe g,int []room,int type1,int type2)
	{
		for(int iter=0;iter<room.length;iter++)
		{
			for(Node n:noeuds)
				if(n.getType()==type1&&n.getRoom()==room[iter])
					n.setType(type2);
		}	
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
			
			System.out.println(listeNode.size());
			//tri de la liste listeNode
			Node n_1= new Node(0,0);
			Collections.sort(listeNode,n_1);
				
						
			for(int ind=0;ind<listeNode.size()-1;ind++)
			{

				for(Node n:noeuds)
 				{
					if(n.getType()==type_out && g.Existe(listeNode.get(ind),n))
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
		int op=1;
		while((ligne=br.readLine())!=null)
		{
			op++;
			String [] tab= ligne.split(",");
			if(tab.length <= 2) break;
			nbcart +=Integer.parseInt(tab[6]);
			//if(op==5) break;
		}
		br.close();
		
	}
	private int ChargerNode(int type,int pos_time,int pos_room,int id) throws IOException
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
		int op_no=1;
		while((ligne=br.readLine())!=null)
		{
				//public Node(int id, int type,int time,int op_no=0,int num_salle=0)
			String [] tab= ligne.split(",");
			if(tab.length <= 2) break;
			
			noeuds.add(new Node(id++,type,Integer.parseInt(tab[pos_time]),op_no,Integer.parseInt(tab[pos_room])));
			op_no++;
			//if(op_no==5) break;
		}
		br.close();
		return id;
	}
	private void ChargerArc(GrapheListe g,int type_in,int type_out) throws IOException
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
				if(n.getType()==type_in && n1.getType()==type_out && n.getOp()==n1.getOp())
				{
					ligne=br.readLine();
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
		try 
		{
			File file = new File("Input/Input"+iter+".csv");
			File file1 = new File("Log"+iter+".csv");
			if (!file.exists()) 
			{
				file.createNewFile();
			}
			if (!file1.exists()) 
			{
				file1.createNewFile();
			}
			FileWriter fw = new FileWriter(file);
			FileWriter fw1 = new FileWriter(file1);
			bw = new BufferedWriter(fw);
			bw1 = new BufferedWriter(fw1);
			// Ecriture dans le fichier des arcs
			// format d'ecriture: (de,vers,lb,ub,cost)
			
			//Ecriture dans le fichier log
			 String content="Simulation day"+iter+"\n";
			 content=nbnoeuds+"\n"+ nbcart+"\n";
			 String mycontent=nbnoeuds+"\n";
			 content += "De:id,De:type,De:Operation,De:Time,Vers:id,Vers:type,Vers:Operation,Vers:Time"+"\n";
			 
			mycontent+= g.getNbarcs()+"\n";
			mycontent+= nbcart+"\n";
			mycontent += "Node De,Node Vers, LB,UB,Cost \n";
			
			for(int i=0;i<nbnoeuds;i++)
				for(Arc a : g.Adjacents(i))
				{
					mycontent += a.getDe().getId()+","+a.getVers().getId()+","+
						a.getLb()+","+a.getUb()+","+a.getCost()+"\n";
						
					content +=a.getDe().getId()+","+a.getDe().getType()+","+a.getDe().getOp()+","+a.getDe().getTime()+","+a.getVers().getId()+
					","+a.getVers().getType()+","+a.getVers().getOp()+","+a.getVers().getTime()+"\n";
				}				
			bw.write(mycontent);
			bw1.write(content);
			
			 
			System.out.println("File written Successfully");

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
			}catch(Exception ex)
			{
				System.out.println("Error in closing the BufferedWriter"+ex);
			}
		}
   }
		
	public String toString()
	{

		//String chaine= g.toString();;
		//for(Node n: noeuds)
		//	chaine += "Noeud : "+ n.getId()+" \n";
		//for(Arc a: arcs)

		return chaine;
	}
	public static void main(String [] args) throws Exception
	{
		Construction c = new Construction("test.csv",2,1);
		/*int capacite=2;
		String chaine= "Input/Fichier";
		for(int i=0;i<1;i++)
		{
			Construction c = new Construction(chaine+(i+1)+".csv",capacite,i+1);
			//System.out.println(c.toString());
		}*/
		

	}
}
