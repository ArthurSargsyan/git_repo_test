package controler;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import Listeners.MyContextListener;
import Model.DataBase;
import beans.Item;


@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Session session=null;

	@Override
	public void init() throws ServletException {
		session = MyContextListener.sf.openSession();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String itemName = request.getParameter("itemName");
		String venderCode = request.getParameter("venderCode");			System.out.println("******"+venderCode+"/////");
		String quantity = request.getParameter("quantity");
		if(quantity=="")quantity="0";

		String result = null;
		String venderCodes = "";
		List<Item> itemList = null;
		
		DataBase db = new DataBase();
		
		if(venderCode.equals("")) {
			itemList = db.searchInDB(session, "itemName", itemName);
			for(int i = 0; i<itemList.size(); i++) {
				venderCodes = venderCodes + itemList.get(i).getVenderCode() + "/ ";
			}
		}else {
			itemList = db.searchInDB(session, "venderCode", venderCode);
		}
		
		if(itemList.size() == 0) {
			result = "{ \"searchResult\":\"No such item\"}";			
		}else {
			if(itemList.size() == 1) {
				if(itemList.get(0).getQunatity()>Integer.parseInt(quantity)) {
					result = "{ \"searchResult\":\"" + itemList.get(0).getItemName() + "\",\"unit\":\"" + itemList.get(0).getUnit() + "\",\"category\":\"" + itemList.get(0).getCategory() + "\",\"venderCode\":\"" + itemList.get(0).getVenderCode() + "\",\"description\":\"" +itemList.get(0).getDescription()+ "\",\"price\":\"" +itemList.get(0).getPrice() + "\"" + ",\"venderCodes\":\"" + venderCodes +"\"}";                
				}else {
					result = "{ \"searchResult\":\"Item quantity not enough\",\"quantity\":\""+itemList.get(0).getQunatity()+"\"}";		
				}
				
			}else {
				result = "{ \"searchResult\":\"More than one Item\",\"venderCodes\":\"" + venderCodes + "\"}";	
			}
		}
		response.getWriter().println(result);   
	}
	
	@Override
	public void destroy() {
		session.close();
	}

	
}
