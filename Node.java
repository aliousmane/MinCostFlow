import java.util.*;

public class Node
	{

		int id;
		int type=0;
		int time=0;
		int op_no=0;

		public Node(int id,int type,int time)
		{
			this.id = id;
			this.type = type;
			this.time = time;

		}
		public Node(int id,int type)
		{
			this.id = id;
			this.type = type;
		}

		public Node(int id)
		{
			this.id=id;
		}
		public Node(int id,int type,int time,int op_no)
		{
			this.id = id;
			this.type = type;
			this.time = time;
			this.op_no = op_no;
		}
		public int getId() {return id;}

		public void setId(int id) {	this.id = id;}

		public int getType() {return type;}

		public void setType(int type ){	this.type = type;}

		public int getTime() {return time;}

		public void setTime(int Time) {	this.time = time;}

		public int getOp() {return op_no;}

		public void setOp(int op_no) {	this.op_no = op_no;}

		public String toString(){return "id:  " +id +"  type  "+type+ "	  time:  " + time + " op:  " + op_no ;}

	}
