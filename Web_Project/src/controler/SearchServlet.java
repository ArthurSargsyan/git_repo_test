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
import beans.Invoice;
import beans.Item;


@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Session session=null;

	@Override
	public void init() throws ServletException {
		session = MyContextListener.sf.openSession();
	}
	
	boolean requestCount = false;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String itemName = request.getParameter("itemName");
		String venderCode = request.getParameter("venderCode");			System.out.println("******"+venderCode+"/////");
		String quantity = request.getParameter("quantity");
		int venderCodeCount=0;
		String result = null;
		String venderCodes = "";
		List<Invoice> invoiceList = null;
		DataBase db = new DataBase();
		
		
		invoiceList = db.searchInDB(MyContextListener.sf, "itemName", itemName);
		for(int i = 0; i<invoiceList.size(); i++) {
			for(Item item:invoiceList.get(i).getItems()) {
			venderCodes = venderCodes + item.getVenderCode() + "/ ";
			venderCodeCount++;
			}
		}
		if(venderCodeCount>1) {
			result = "{ \"searchResult\":\"More than one Item\",\"venderCodes\":\"" + venderCodes + "\"}";	
			if(requestCount) {
				invoiceList = db.searchInDB(MyContextListener.sf, "venderCode", venderCode);
			}
			requestCount = true;
		}
		
		if(invoiceList.size() == 0) {
			result = "{ \"searchResult\":\"No such item\"}";			
		}else {
			if(invoiceList.size() == 1) {
				if(quantity=="")	quantity="0";
				
				for(Item item : invoiceList.get(0).getItems()){
				
					if(item.getQunatity()>Integer.parseInt(quantity)) {
						result = "{ \"searchResult\":\"" + item.getItemName() + "\",\"unit\":\"" + item.getUnit() + "\",\"category\":\"" + item.getCategory() + "\",\"venderCode\":\"" + item.getVenderCode() + "\",\"description\":\"" +item.getDescription()+ "\",\"price\":\"" + item.getPrice() + "\"" + ",\"venderCodes\":\"" + venderCodes +"\"}";                
					}else {
						result = "{ \"searchResult\":\"Item quantity not enough\",\"quantity\":\""+item.getQunatity()+"\"}";		
					}
				}
				requestCount = false;
			}
		}
		response.getWriter().println(result);   
	}
	
	@Override
	public void destroy() {
		session.close();
	}

	
}
