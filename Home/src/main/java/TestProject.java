import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map.Entry;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Servlet implementation class TestProject
 */
@WebServlet("/TestProject")
public class TestProject extends HttpServlet {
	
	ArrayList <Keyword> lst;
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestProject() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		for(int i = 0; i < request.getParameter("keyword").hashCode(); i++) {
			String input = request.getParameter("keyword");
			Keyword keyword = new Keyword(input, 0);
			lst.add(keyword);
			if(input == null) {
				String requestUri = request.getRequestURI();
				request.setAttribute("requestUri", requestUri);
				request.getRequestDispatcher("Search.jsp").forward(request, response);
				return;
			}
		}
		
		String words = "";
		for(int i = 0; i < lst.size(); i++) {
			words += lst.get(i).name + " ";
		}
		
		GoogleQuery google = new GoogleQuery(words);
		Web webs = new Web(google.url, words);
		HashMap<String, String> query = google.query();
		
		String[][] s = new String[query.size()][2];
			request.setAttribute("query", s);
			int num = 0;
			for(Entry<String, String> entry : query.entrySet()) {
				webs.treeRootAddChild(query.entrySet().toString(), words);
			    String key = entry.getKey();
			    String value = entry.getValue();
			    s[num][0] = key;
			    s[num][1] = value;
			    num++;
			}
		
		request.getRequestDispatcher("googleitem.jsp").forward(request, response); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
