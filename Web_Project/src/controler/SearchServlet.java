package controler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		String venderCode = request.getParameter("venderCode");			System.out.println("******"+venderCode+"/////");
		String quantity = request.getParameter("quantity");
		String result = null;
		String venderCodes = "";
		List<Invoice> invoiceList = null;
		List<String> venderCodeList= new ArrayList<>();
		DataBase db = new DataBase();
		
		if(quantity.equals(""))	quantity="0";
		if(venderCode.equals("")) {
			invoiceList = db.searchInDB(MyContextListener.sf, "itemName", itemName);
			if(invoiceList.size()==1) {
				for(Item item:invoiceList.get(0).getItems()) {
					if(item.getItemName().equals(itemName)) {
						if(item.getQuantity()>Integer.parseInt(quantity)) {
							result = "{ \"searchResult\":\"" + item.getItemName() + "\",\"unit\":\"" + item.getUnit() + "\",\"category\":\"" + item.getCategory() + "\",\"venderCode\":\"" + item.getVenderCode() + "\",\"description\":\"" +item.getDescription()+ "\",\"price\":\"" + item.getPrice() +"\"}"; 
							
						}else {
							result = "{ \"searchResult\":\"Item quantity not enough\",\"quantity\":\""+item.getQunatity()+"\"}";
						}
					}
				}
			}else {
				if(invoiceList.size()==0) {
					result = "{ \"searchResult\":\"No such item\"}";
				}else {
					for(int i = 0; i<invoiceList.size(); i++) {
						for(Item item:invoiceList.get(i).getItems()) {
							boolean checkAvalability=false;
							if(item.getItemName().equals(itemName)) {
								for(String vendercode:venderCodeList) {
									if(item.getVenderCode().equals(vendercode)) {
										checkAvalability=true;
									}
								}
								if(checkAvalability==false) {
								venderCodeList.add(item.getVenderCode());
								}
							}
						}
					}
					for(String vender_Code:venderCodeList) {
						venderCodes = venderCodes + vender_Code + "/ ";
						}
					result = "{ \"searchResult\":\"More than one Item\",\"venderCodes\":\"" + venderCodes + "\"}";
				}
			}
		}else {
			invoiceList = db.searchInDB(MyContextListener.sf, "venderCode", venderCode);
			
			if(invoiceList.size()==1) {
				for(Item item:invoiceList.get(0).getItems()) {
					if(item.getItemName().equals(itemName)) {
						if(item.getQuantity()>Integer.parseInt(quantity)) {
							result = "{ \"searchResult\":\"" + item.getItemName() + "\",\"unit\":\"" + item.getUnit() + "\",\"category\":\"" + item.getCategory() + "\",\"venderCode\":\"" + item.getVenderCode() + "\",\"description\":\"" +item.getDescription()+ "\",\"price\":\"" + item.getPrice() +"\"}"; 
							
						}else {
							result = "{ \"searchResult\":\"Item quantity not enough\",\"quantity\":\""+item.getQunatity()+"\"}";
						}
					}
				}
			}else {
				if(invoiceList.size()==0) {
					result = "{ \"searchResult\":\"No such item\"}";
				}else {
					String fetchedDate = invoiceList.get(0).getDate();
					Invoice oldestInvoice = invoiceList.get(0);
					for(Invoice invoice:invoiceList) {
						
						if(Integer.parseInt(invoice.getDate().substring(0,4)) < Integer.parseInt(fetchedDate.substring(0,4))){
							if(Integer.parseInt(invoice.getDate().substring(5,7)) < Integer.parseInt(fetchedDate.substring(5,7))) {
								if(Integer.parseInt(invoice.getDate().substring(8,10)) < Integer.parseInt(fetchedDate.substring(8,10))) {
									oldestInvoice=invoice;
								}
							}
						}
					}
					invoiceList.clear();
					invoiceList.add(oldestInvoice);
					for(Item item:invoiceList.get(0).getItems()) {
						if(item.getItemName().equals(itemName)) {
							if(item.getQuantity()>Integer.parseInt(quantity)) {
								result = "{ \"searchResult\":\"" + item.getItemName() + "\",\"unit\":\"" + item.getUnit() + "\",\"category\":\"" + item.getCategory() + "\",\"venderCode\":\"" + item.getVenderCode() + "\",\"description\":\"" +item.getDescription()+ "\",\"price\":\"" + item.getPrice() +"\"}"; 
								
							}else {
								result = "{ \"searchResult\":\"Item quantity not enough\",\"quantity\":\""+item.getQunatity()+"\"}";
							}
						}
					}
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
