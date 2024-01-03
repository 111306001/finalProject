
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ArrayList;

class Client {

    public static ArrayList<keyword> getKeywordWeight(ArrayList<String> list) throws IOException
	{
        // Concat all string in list into one string
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i=0; i<list.size(); i++) {
            if (i != 0)
                sb.append(",");
            sb.append("\"" + list.get(i) + "\"");
        }
        sb.append("]");

        // Connect to python server
        String requestData = sb.toString();
		URL url = new URL("http://127.0.0.1:5000");
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        // add data into POST request
        String params = "data=" + requestData;
        try(OutputStream os = conn.getOutputStream()) {
            byte[] input = params.getBytes("utf-8");
            os.write(input, 0, input.length);			
        }
        InputStream in = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		String retVal = "";
		String line = null;
        // Get response from POST request

		while ((line = br.readLine()) != null)
		{
			retVal = retVal + line + "\n";
		}
        // Parse response data in following format
        // "歐洲,0.712:亞洲,0.234"
        ArrayList<keyword> al = new ArrayList<>();
        for (String result : retVal.split(":")){
            String name = result.split(",")[0];
            double weight = Double.parseDouble(result.split(",")[1]);
            al.add(new keyword(name, 0, weight));
        }

		return al;
	}
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

};
