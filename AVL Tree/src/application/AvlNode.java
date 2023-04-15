package application;

public class AvlNode {
	 AvlNode left, right;
     Name name;
     
     int height;
     Frequency fre;
     List list;
     /* Constructor */
     public AvlNode(Name name,Frequency y)
     {
         left = null;
         right = null;
         this.name=name;
         
         list=new List();
         this.fre=y;
         list.addLast(y);
         height = 0;
     }
	@Override
	public String toString() {
		String lines=list.print();
		
		return "AvlNode [name=" + name + ", height=" + height + ", list="+lines+" ]";
	}
     
    
}
