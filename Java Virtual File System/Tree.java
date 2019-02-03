class Tree
{
	public String name;
	public Tree prnt;
	private Tree fc;
	protected Tree ns;
	protected Tree prev;
	private int size;
	Tree() {}
	Tree(String n)
	{
		name= n;
	}
	Tree(String n, Tree p, Tree pr)
	{
		name= n;
		prnt= p;
		prev= pr;
	}
	public boolean isDirectory(String n)
	{
		for(int i= 0;i<n.length();i++)
			if(n.charAt(i)=='.')
				return false;
		return true;
	}
	public Tree find(String n)
	{
		Tree temp= null;
		if(fc==null)
			return null;
		temp= fc;
		do
		{
			if(temp.name.contentEquals(n))
				return temp;
			temp= temp.ns;
			if(temp==null)
				break;
		}while(true);
		return null;
	}
	public void delete(String name)
	{
		Tree temp= null;
		if(fc==null)
		{
			System.out.println("File Not Found");
			return;
		}
		temp= find(name);
		if(temp==null)
		{
			System.out.println("File Not Found");
			return;
		}
		if(temp== fc)
		{
			fc= fc.ns;
			return;
		}
		temp.prev.ns= temp.ns;
	}
	public void insert(String n)
	{
		Tree temp= null;
		if(isDirectory(n))
		{
			if(fc==null)
			{
				fc= new Tree(n,this,null);
				return;
			}
			temp= fc;
			fc= new Tree(n,this,null);
			fc.ns= temp;
			temp.prev= fc;
		}
		else
		{
			if(fc==null)
			{
				fc= new File(n,this,null);
				return;
			}
			temp= fc;
			while(temp.ns!=null)
				temp= temp.ns;
			temp.ns= new File(n,this,temp);
			temp.ns.prev= temp;
		}
	}
	public String toString()
	{
		return name;
	}
	public void list() //throws nullpointerexception
	{
		if(fc==null)
			return;
		Tree temp= fc.ns;
		System.out.print(fc.name+"   ");
		while(temp!=null)
		{
			System.out.print(temp+"   ");
			temp= temp.ns;
		}
		System.out.println();
	}
	public static void main(String[] args)
	{
		Tree head= new Tree("/");
		head.insert("bca1");
		head.insert("bin");
		head.insert("bi.txt");
		head.insert("i.txt");
		head.insert("usr");
		head.list();
		head.delete("i.txt");
		head.list();
	}
}
class File extends Tree
{
	File(String n)
	{
		name= n;
	}
	File(String n, Tree p, Tree pr)
	{
		name= n;
		prnt= p;
		prev= pr;
	}
	public String toString()
	{
		return name;
	}
}
