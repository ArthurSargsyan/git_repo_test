package controler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.buf.CharChunk;
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
	
	private static String requestVenderCode ="v";
	private static String requestItemName ="n";
	private static int requestQuantity = 0;
	boolean secondRequest = false;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String itemName = request.getParameter("itemName");
		String venderCode = request.getParameter("venderCode");
		String quantity = request.getParameter("quantity");
		String result = null;
		String venderCodes = "";
		List<Invoice> invoiceList = null;
		Set<String> venderCodeList= new HashSet();
		DataBase db = new DataBase();
		
		if(quantity.equals(""))	quantity="0";
		if(venderCode.equals("")) {
			invoiceList = db.searchInDB(MyContextListener.sf, "itemName", itemName);
			if(invoiceList.size()==1) {
				result = db.checkAvalableItemQuantity(invoiceList, itemName);
			}else {
				if(invoiceList.size()==0) {
					result = "{ \"searchResult\":\"No such item\"}";
				}else {
					venderCodeList = db.getVenderCodeList(invoiceList, itemName);
					for(String vender_Code:venderCodeList) {
						venderCodes = venderCodes + "\"" + vender_Code + "\"" + ",";
					}
					venderCodes = venderCodes.substring(0, venderCodes.length()-1);
					result = "{ \"searchResult\":\"More than one Item\",\"venderCodes\":[" + venderCodes + "]}";
					
					System.out.println(result);
				}
			}
		}else {
			invoiceList = db.searchInDB(MyContextListener.sf, "venderCode", venderCode);
			
			if(invoiceList.size()==1) {
				result = db.checkAvalableItemQuantity(invoiceList, itemName);
			}else {
				if(invoiceList.size()==0) {
					result = "{ \"searchResult\":\"No such item\"}";
				}else {
					Invoice oldestInvoice = null;
					oldestInvoice = db.getOldestInvoice(invoiceList);
					invoiceList.clear();
					invoiceList.add(oldestInvoice);
					result = db.checkAvalableItemQuantity(invoiceList, itemName);
				}
			}
		}
		response.getWriter().println(result);   
	}
	
	@Override
	public void destroy() {
		session.close();
	}

	
}
