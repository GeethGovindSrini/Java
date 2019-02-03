//This program contains class File for the project File System
class File
{
	private StringBuffer content;
	File()
	{
		content= new StringBuffer();
	}
	public void write(String x)
	{
		content= content.append("\n"+x);
	}
	public void read()
	{
		System.out.println(content);
	}
	public int sizeOfFile()
	{
		return(content.length()*2);
	}
	public static void main(String[] args)
	{
		File new1= new File();
		new1.write("om sri sairam");
		new1.write("This is testing...!!!");
		new1.write("Thank you...!!!");
		new1.read();
		System.out.println("The size of this file is: "+new1.sizeOfFile()+" Bytes");
	}
}

