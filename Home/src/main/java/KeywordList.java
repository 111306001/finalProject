import java.io.IOException;
import java.util.*;

public class KeywordList
{
	private LinkedList<Keyword> lst;

	public KeywordList()
	{
		this.lst = new LinkedList<Keyword>();
	}

	public void add(Keyword keyword)
	{
		// add keyword to proper index base on its count. ASCENDING SORT BY COUNT AND
		// WEIGHT
		// printKeywordList(lst) : check if elements are sorted
		for (int i = 0; i < lst.size(); i++)
		{
			Keyword k = lst.get(i);
			if (keyword.count <= k.count)
			{ // if count is smaller than original, placed in the front; if same count
				// then compare weight
				if (keyword.count < k.count)
				{
					lst.add(i, keyword);
					return;
				}
				else if (keyword.count == k.count && keyword.weight <= k.weight)
				{ // if same count, smaller weight
					// placed in the front
					lst.add(i, keyword);
					return;
				}
			}
		}
		lst.add(keyword);
	}

	public void outputIndex(int i)
	{
		if (i >= lst.size())
		{
			System.out.println("InvalidOperation");
			return;
		}
		Keyword k = lst.get(i);
		System.out.println(k);
	}

	public void outputCount(int c)
	{
		LinkedList<Keyword> results = new LinkedList<>();
		for (int i = 0; i < lst.size(); i++)
		{
			Keyword k = lst.get(i);
			if (k.count == c)
			{
				results.add(k);
			}
		}
		if (results.isEmpty())
		{
			System.out.println("NotFound");
		}
		else
		{
			printKeywordList(results);
		}
	}

	public void outputHas(String pattern)
	{
		LinkedList<Keyword> results = new LinkedList<>();
		for (int i = 0; i < lst.size(); i++)
		{
			Keyword k = lst.get(i);
			if (k.name.contains(pattern))
			{
				results.add(k);
			}
		}
		if (results.isEmpty())
		{
			System.out.println("NotFound");
		}
		else
		{
			printKeywordList(results);
		}
	}

	public void outputName(String pattern)
	{
		LinkedList<Keyword> results = new LinkedList<>();
		for (int i = 0; i < lst.size(); i++)
		{
			Keyword k = lst.get(i);
			if (k.name.equals(pattern))
			{
				results.add(k);
			}
		}
		if (results.isEmpty())
		{
			System.out.println("NotFound");
		}
		else
		{
			printKeywordList(results);
		}
	}


	public void outputScore()
	{
		float results = 0;
		// 1.To calculate all keyword's count*weight
		for (int i = 0; i < lst.size(); i++)
		{
			Keyword k = lst.get(i);
			results += k.weight * k.count;
		}
		System.out.println(results);
	}

	private void printKeywordList(LinkedList<Keyword> kLst)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < kLst.size(); i++)
		{
			Keyword k = kLst.get(i);
			if (i > 0)
				sb.append(" ");
			sb.append(k.toString());
		}
		System.out.println(sb.toString());
	}
	public void rankWebPagesByKeywords(ArrayList<WebPage> webPages, ArrayList<Keyword> keywords) throws IOException {
        for (WebPage page : webPages) {
            page.setScore(keywords);
            // Implement ranking logic here using the WebPage scores
        }
    }
}