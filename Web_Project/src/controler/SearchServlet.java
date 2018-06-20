package controler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import Model.DataBase;
import beans.Item;


@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Session session=null;
	SessionFactory sf=null;
	
	@Override
	public void init() throws ServletException {
		Configuration cfg = new Configuration();
		cfg.configure("resources/hibernate.cfg.xml");
		sf = cfg.buildSessionFactory();
		session = sf.openSession();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = null;
		String itemName = request.getParameter("itemName");
		System.out.println(itemName);
		DataBase db = new DataBase();
		Item item = db.searchInDB(session, itemName);
		if(item == null) {
			result = "{ \"itemName\":\"No such item\"}";			
		}
		else {
		result = "{ \"itemName\":\"" + item.getItemName() + "\",\"unit\":\"" + item.getUnit() + "\",\"category\":\"" + item.getCategory() + "\",\"venderCode\":\"" + item.getVenderCode() + "\",\"description\":\"" +item.getDescription()+ "\",\"price\":\"" +item.getPrice() + "\"}";
		}
		
		response.getWriter().println(result);   
		
	}
	
	@Override
	public void destroy() {
		session.close();
		sf.close();
	}

	
}
