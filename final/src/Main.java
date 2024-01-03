
import java.util.ArrayList;
import java.util.Scanner;
public class Main{
public static void Main(String[] args) {
	ArrayList<String> list=new ArrayList<>();
	Scanner sc =new Scanner(System.in);
	while(sc.hasNext()) {
		String keyword= sc.next();
		list.add(keyword);
		sc.close();
	}
}
}