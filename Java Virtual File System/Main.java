import java.util.Scanner;
class Main
{
	public static void main(String[] args)
	{
		Scanner scan= new Scanner(System.in);
		System.out.println("Welcome to the File System....!!!");
		System.out.print("fs$ ");
		do
		{
			String input= scan.next();
		}while(!input.contentEquals(quit));
		
	}
}
