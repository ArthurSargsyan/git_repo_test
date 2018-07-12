package controler;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import Listeners.MyContextListener;
import Model.DataBase;
import beans.Item;

@WebServlet("/StatisticsServlet")
public class StatisticsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	Session session = null;
	
	@Override
	public void init() throws ServletException {
		session = MyContextListener.sf.openSession();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String argName1 = request.getParameter("argName1");
		String argValue1 = request.getParameter("argValue1");
		String argName2 = request.getParameter("argName2");
		String argValue2 = request.getParameter("argValue2");
		
		DataBase db = new DataBase();
		String result = "{ \"SearchResult\":[";
		
		if(argName2.equals("") & argValue2.equals("")) {
			List<Item>  list= db.searchInDB(session, argName1, argValue1);
			
			for (Item item : list) {
				result =result +"{ \"itemName\":\"" + item.getItemName() + "\",\"unit\":\"" + item.getUnit() + "\",\"quantity\":\"" + item.getQunatity() + "\",\"category\":\"" + item.getCategory() + "\",\"venderCode\":\"" + item.getVenderCode() + "\",\"description\":\"" +item.getDescription()+ "\",\"price\":\"" +item.getPrice() +"\"},";                
			}
			result =result.substring(0, result.length()-1) +"]}";
		
		
		
		}else {
			
			System.out.println("It is date type");
		}
		
		
		/////////////////////////////////////////////////////////////
		
		
		response.getWriter().println(result);
	
		
		
	
	}


	@Override
	public void destroy() {
		session.close();
		
	}
	
	

}
