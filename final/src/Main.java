
import java.util.ArrayList;
import java.util.Scanner;
public class Main{
	public static void main(String[] args) {
        try {
            // Example Input: ["歐洲", "法國"]
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add("京都");
            arrayList.add("旅遊");
            // Example Output: ArrayList<Keyword>
            // Will give keywords and responding weights.
            // Note that all the count in keyword is initialized to zero.
            System.out.println(getKeywordWeight(arrayList));
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

}