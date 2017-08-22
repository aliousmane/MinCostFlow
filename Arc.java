import java.util.*;


public class Arc
{
	private int id;
	private Node de;		
	private Node vers;	
	private int cost;	
	private int lb;
	private int ub;
	
	

	public Arc(int id,Node s,Node t, int c,int lb,int ub)
	{
		this.id=id;
		this.de=s;
		this.vers = t;
		this.cost= c;
		this.lb = lb;
		this.ub = ub;
	}
	public Arc(Node s,Node t, int c,int lb,int ub)
	{
		this.de=s;
		this.vers = t;
		this.cost= c;
		this.lb = lb;
		this.ub = ub;
	}

	public Arc(Node s,Node t, int c)
	{
		de = s;
		vers = t;
		cost = c;
	}
	public Arc(Node s,Node t)
	{
		de = s;
		vers = t;
		
	}
	
	public Node getDe() 
	{
		return de;
	}

	public void setDe(Node de) 
	{
		this.de = de;
	}

	public Node getVers() 
	{
		return vers;
	}
	
	public void setVers(Node vers) 
	{
		this.vers = vers;
	}
	
	public int getCost() 
	{
		return cost;
	}
	public int getLb() 
	{
		return lb;
	}
	public int getUb() 
	{
		return ub;
	}

	public void setCost(int cout) 
	{
		this.cost = cout;
	}
	public String toString()
	{
		return "("+this.getDe()+" --> "+this.getVers()+")";
	}
}

