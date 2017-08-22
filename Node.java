import java.util.*;

public class Node implements Comparator<Node>
	{

		int id;
		int room;
		int type=0;
		int time=0;
		int op_no=0;

		public Node(int id, int type,int time,int room)
		{
			this.id = id;
            		this.room = room;
			this.type = type;
			this.time = time;

		}
		public Node(int id, int time,int room)
		{
			this.id = id;
			this.room = room;
			this.time = time;
		}

		public Node(int id, int room)
		{
			this.id=id;
            		this.room = room;
		}
		public Node(int id, int type,int time,int op_no,int room)
		{
			this.id = id;
			this.room = room;
			this.type = type;
			this.time = time;
			this.op_no = op_no;
		}
		public int getId() {return id;}

		public void setId(int id) {	this.id = id;}
                
		public int getRoom() {return room;}
		public void setRoom(int room) { this.room=room;}

		public int getType() {return type;}

		public void setType(int type ){	this.type = type;}

		public int getTime() {return time;}

		public void setTime(int Time) {	this.time = time;}

		public int getOp() {return op_no;}

		public void setOp(int op_no) {	this.op_no = op_no;}

		public String toString(){return "id:  " +id + " room: " + room + "  type  "+type+ "	  time:  " + time + " op:  " + op_no ;}
		
		public int compare(Node  noeud1, Node noeud2)
		{
			if(noeud1.time< noeud2.time) return -1;
			else if(noeud1.time> noeud2.time) return 1;
			else return 0;
		}
	}
