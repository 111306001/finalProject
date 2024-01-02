package fp;
import java.util.ArrayList;
import java.util.Scanner;
public class main{
public static void main(String[] args) {
	ArrayList<String> list=new ArrayList<>();
	Scanner sc =new Scanner(System.in);
	while(sc.hasNext()) {
		String keyword= sc.next();
		list.add(keyword);
		sc.close();
	}
	
	

}
}