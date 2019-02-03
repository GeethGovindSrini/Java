//import java.util.jar;
import java.util.Scanner;
import java.io.*;
class Tree implements Serializable
{
	public String name;
	public Tree prnt;
	private Tree fc;
	protected Tree ns;
	protected Tree prev;
	protected int size;
	Tree() {}
	Tree(String n)
	{
		name= n;
		prnt= null;
		prev= null;
	}
	Tree(String n, Tree p, Tree pr)
	{
		name= n;
		prnt= p;
		prev= pr;
	}
	public static boolean isDirectory(String n)
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
	public Tree findPrev(String n)
	{
		Tree temp= find(n);
		return temp.prev;
	}
	public void delete(String name)
	{
		Tree temp= null;
		temp= find(name);
		if(temp== fc)
		{
			fc.decPrntSize();
			fc= fc.ns;
			return;
		}
		temp= findPrev(name);
		Tree temp1= temp.ns;
		temp1.decPrntSize();
		if(temp1.ns== null)
		{
			temp.ns= null;
			return;
		}
		temp.ns= temp1.ns;
		temp.ns.prev= temp;
	}
	public void insert(String n)
	{
		Tree temp= null,file= null;
		temp= find(n);
		if(isDirectory(n))
		{
			if(find(n)!= null)
			{
				System.out.println("Same name already Exists...!!!");
				return;
			}
			if(fc==null)
			{
				fc= new Tree(n,this,null);
				fc.size= fc.sizeSet();
				fc.incPrnt(1);
				return;
			}
			temp= fc;
			fc= new Tree(n,this,null);
			fc.ns= temp;
			temp.prev= fc;
			fc.size= fc.sizeSet();
			fc.incPrnt(1);
		}
		else
		{
			file= find(n);
			int old= 0;
			if(file== null)
			{
				if(fc==null)
				{
					fc= new File(n,this,null);
					fc.fileInput();
					fc.size= fc.sizeSet();
					fc.incPrnt(fc.size);
					return;
				}
				temp= fc;
				while(temp.ns!=null)
					temp= temp.ns;
				temp.ns= new File(n,this,temp);
				temp.ns.prev= temp;
				temp.ns.fileInput();
				temp.ns.size= temp.ns.sizeSet();
				temp.ns.incPrnt(temp.ns.size);
				return;
			}
			else
			{
				file.fileInput();
				old= file.size;
				file.size= file.sizeSet();
				if(old!= file.size)
					file.incPrnt(file.size- old);
				return;
			}
		}
	}
	public void incPrnt(int x)
	{
		Tree temp= prnt;
		while(prnt!=null)
		{
			temp.size+= x;
			if(temp.prnt!=null)
				temp= temp.prnt;
			else
				break;
		}
	}
	public void decPrntSize()
	{
		Tree temp= prnt;
		while(prnt!= null)
		{
			temp.size-= size;
			if(temp.prnt!=null)
				temp= temp.prnt;
			else
				break;
		}
	}
	public void fileInput() {}
	public String toString()
	{
		return (name+"\t"+size+" b");
	}
	public int sizeSet()
	{
		return 1;
	}
	public void list()
	{
		if(fc==null)
			return;
		Tree temp= fc.ns;
		System.out.println(fc);
		while(temp!=null)
		{
			System.out.println(temp);
			temp= temp.ns;
		}
		System.out.println();
	}
}
class File extends Tree
{
	private StringBuffer content;
	File()
	{
		content= new StringBuffer();
	}
	File(String n, Tree p,Tree pr)
	{
		name= n;
		prnt= p;
		prev= pr;
		content= new StringBuffer();
	}
	public void write(String x)
	{
		content= content.append("\n"+x);
	}
	public void fileInput()
	{
		Scanner scan= new Scanner(System.in);
		read();
		String input;
		do
		{
			input= scan.nextLine();
				if(input.contentEquals(""))
					break;
			write(input);
		}while(true);
	}
	public void read()
	{
		System.out.println(content);
	}
	public int sizeSet()
	{
		return (content.length()*2);
	}
}
class FileSystem implements Serializable
{
	private Tree dir;
	public boolean error;
	FileSystem()
	{
		dir= new Tree("usr");
		error= false;
	}
	public FileSystem cDir(Tree d)
	{
		dir= d;
		error= false;
		return this;
	}
	public void mkdir(String n)
	{
		char[] name= n.toCharArray();
		if(!Tree.isDirectory(n))
		{
			System.out.println("The entered Directory name is not valid...!!!");
			return;
		}
		
		dir.insert(n);
	}
	public void gbd(String n)
	{
			if(Tree.isDirectory(n))
			{
				System.out.println("The entered File name is not valid...!!!");
				return;
			}
			System.out.println("Show end of input by 'ctrl+b'");
			dir.insert(n);
			return;
	}
	public Tree cd(String name)
	{
		Tree temp= null;
		if(name.contentEquals(""))
		{
			temp= dir;
			while(temp.prnt!= null)
				temp= temp.prnt;
			return temp;
		}
		if(name.contentEquals(" .."))
			if(dir.prnt== null)
			{
				System.out.println("No previous directory exists...!!!");
				return dir;
			}
			else
				return dir.prnt;
		if(!Tree.isDirectory(name))
		{
			System.out.println("The entered Directory name is not valid...!!!");
			return dir;
		}
		temp= dir.find(name);
		if(temp== null)
		{
			System.out.println("' "+name+"' Directory Doesn't Exist...!!!");
			return dir;
		}
		return temp;
	}
	public void rm(String name)
	{
		Tree temp= null;
		temp= dir.find(name);
                if(temp== null)
                {
                        System.out.println("' "+name+"' Doesn't Exist...!!!");
                        return;
                }
		else
		{
			dir.delete(name);
			return;
		}
	}
	public void pwd()
	{
		Tree leaf= dir;
		StringBuffer path= new StringBuffer();
		while(true)
		{
			path= path.insert(0,(leaf.name+"/"));
			if(leaf.prnt==null)
				break;
			else
				leaf= leaf.prnt;
		}
		System.out.print(path);
	}
	public void ls()
	{
		Tree t= dir;
		t.list();
	}
/*	public void clear()
	{
		for(int i= 0;i<50;i++)
			System.out.println();
	}*/
	public void help()
	{
		System.out.println("\t\t\t\tUser Manual....!!!");
		System.out.println("The Commands Available Are: ");
		System.out.println("\t1. mkdir- Make Directory\n\t2. cd- Change Directory");
		System.out.println("\t'cd <file name>' to enter File or 'cd ..' to go to Previous Folder");
		System.out.println("\t3. rm- Delete\n\t4. pwd- Directory Path");
		System.out.println("\t5. gbd- Enter Editor\n\t6. quit- To Quit Maya...!!!");
	
	}
	public static void main(String[] args) 
	{
		Scanner scan= new Scanner(System.in);
		FileSystem fs;
		FileSystem temp= Serialization.deSerialize();
		if(temp!= null)
		{
		/*	while(temp.dir.prnt!=null)
				temp= temp.dir.prnt;*/
			fs= temp;
		}
		else
			fs= new FileSystem();
		FileSystem curdir= fs;
		System.out.println("\t\t\t\tWelcome to the Maya File System....!!!");
		String[] input= new String[2];
		System.out.print("Mayafs: ");
		curdir.pwd();
		System.out.print("~$ ");
		do
		{
			input[0]= scan.next();
			switch(input[0])
			{
				case "quit":	break;
				case "mkdir":	input[1]= scan.nextLine();
						curdir.mkdir(input[1]);
						System.out.print("Mayafs: ");
						curdir.pwd();
						System.out.print("~$ ");
						break;
				case "cd":	input[1]= scan.nextLine();
						curdir= curdir.cDir(curdir.cd(input[1]));
						System.out.print("Mayafs: ");
						curdir.pwd();
						System.out.print("~$ ");
						break;
				case "gbd":	input[1]= scan.nextLine();
						curdir.gbd(input[1]);
						System.out.print("Mayafs: ");
						curdir.pwd();
						System.out.print("~$ ");
						break;
				case "rm":	input[1]= scan.nextLine();
						curdir.rm(input[1]);
						System.out.print("Mayafs: ");
						curdir.pwd();
						System.out.print("~$ ");
						break;
				case "pwd":	curdir.pwd();
						System.out.println();
						System.out.print("Mayafs: ");
						curdir.pwd();
						System.out.print("~$ ");
						break;
				case "ls":	curdir.ls();
						System.out.print("Mayafs: ");
						curdir.pwd();
						System.out.print("~$ ");
						break;
				case "help":	curdir.help();
						System.out.print("Mayafs: ");
						curdir.pwd();
						System.out.print("~$ ");
						break;
				/*case "clear":	curdir.clear();
						System.out.print("Mayafs: ");
						curdir.pwd();
						System.out.print("~$ ");
						break;*/
				default: 	System.out.println("' "+input[0]+"' Command Not Found");
						System.out.print("Mayafs: ");
						curdir.pwd();
						System.out.print("~$ ");
			}
		}while(!input[0].contentEquals("quit"));
		curdir= curdir.cDir(curdir.cd(""));
		Serialization.serialize(fs);
	}
}
class Serialization
{
	public static void serialize(FileSystem fs)
        {
                try
                {
                        FileSystem object= fs;
                        FileOutputStream fos= new FileOutputStream("Directory");
                        ObjectOutputStream oos= new ObjectOutputStream(fos);
                        oos.writeObject(object);
                        oos.flush();
                        oos.close();
                }
                catch(IOException e)
                {
                        //System.out.println("Exception While Serializing: "+e);
                        fs.error= true;
                }

        }
	public static FileSystem  deSerialize()
        {
                try
                {
                        FileSystem object;
                        FileInputStream fis= new FileInputStream("Directory");
                        ObjectInputStream ois= new ObjectInputStream(fis);
                        object= (FileSystem) ois.readObject();
                        ois.close();
                        return object;
                }
                catch(Exception e)
                {
                        //System.out.println("Exception During Deserializing: "+e);
			return null;
                }
        }

	
}
