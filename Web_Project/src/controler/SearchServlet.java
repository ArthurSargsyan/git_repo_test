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
	
	private static String requestVenderCode ="";
	private static String requestItemName ="";
	private static int requestQuantity = 0;
	boolean isRequest = false;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String itemName = request.getParameter("itemName");
		String venderCode = request.getParameter("venderCode");			System.out.println("******"+venderCode+"/////");
		String quantity = request.getParameter("quantity");
		String result = null;
		
		if(quantity.equals(""))	{
			quantity="0";
		}
		if(requestVenderCode.equals(venderCode) & requestItemName.equals(itemName) & requestQuantity==Integer.parseInt(quantity)) {
			result = "{ \"searchResult\":\"Repeated search\"}";
		}else {
			int venderCodeCount=0;
			String venderCodes = "";
			List<Invoice> invoiceList = null;
			List<String> venderCodeList= new ArrayList<>();
			DataBase db = new DataBase();
			
			invoiceList = db.searchInDB(MyContextListener.sf, "itemName", itemName);
			System.out.println(invoiceList +"////////////////invoiceList ");
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
						venderCodeCount++;
					}
				}
			}
//			System.out.println(venderCodes+"////////////////venderCodes");
//			System.out.println(!requestVenderCode.equals(venderCode)+"****");
//			System.out.println(requestQuantity!=Integer.parseInt(quantity));
			
			if(venderCodeCount>1) {			
				if(isRequest & (!requestVenderCode.equals(venderCode)||requestQuantity!=Integer.parseInt(quantity))) { 
					invoiceList = db.searchInDB(MyContextListener.sf, "venderCode", venderCode);
				}else {
					for(String vender_Code:venderCodeList) {
					venderCodes = venderCodes + vender_Code + "/ ";
					}
					
					
					result = "{ \"searchResult\":\"More than one Item\",\"venderCodes\":\"" + venderCodes + "\"}";
					isRequest = true;
				}
			}
			requestQuantity =Integer.parseInt(quantity);
			if(invoiceList.size() == 0) {
				result = "{ \"searchResult\":\"No such item\"}";			
			}else {
				if(invoiceList.size() == 1) {
					for(Item item : invoiceList.get(0).getItems()){
						if(item.getVenderCode().equals(venderCode) || item.getItemName().equals(itemName)) {
							requestVenderCode =item.getVenderCode();     System.out.println("requestVenderCode =" + requestVenderCode );
							requestItemName =item.getItemName();		System.out.println( "requestItemName =" + requestItemName);  System.out.println( "requestQuantity =" + requestQuantity);
							if(item.getQunatity()>=Integer.parseInt(quantity)) {
								result = "{ \"searchResult\":\"" + item.getItemName() + "\",\"unit\":\"" + item.getUnit() + "\",\"category\":\"" + item.getCategory() + "\",\"venderCode\":\"" + item.getVenderCode() + "\",\"description\":\"" +item.getDescription()+ "\",\"price\":\"" + item.getPrice() + "\"" + ",\"venderCodes\":\"" + venderCodes +"\"}";                
							}else {
								result = "{ \"searchResult\":\"Item quantity not enough\",\"quantity\":\""+item.getQunatity()+"\"}";		
							}
						}
					}
					//isRequest = false;
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
