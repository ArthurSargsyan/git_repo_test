package controler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import Listeners.MyContextListener;
import Model.DataBase;
import beans.Invoice;
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
			List<Invoice>  list= db.searchInDB(MyContextListener.sf, argName1, argValue1);
			
			for (Invoice invoice : list) {
				for(Item item:invoice.getItems())
				
				result =result +"{ \"itemName\":\"" + item.getItemName() + "\",\"unit\":\"" + item.getUnit() + "\",\"quantity\":\"" + item.getQunatity() + "\",\"category\":\"" + item.getCategory() + "\",\"venderCode\":\"" + item.getVenderCode() + "\",\"description\":\"" +item.getDescription()+ "\",\"price\":\"" +item.getPrice() + "\",\"invoiceNo\":\"" + invoice.getInvoiceNo() + "\",\"vender\":\"" + invoice.getVender() + "\",\"currancy\":\"" + invoice.getCurrancy() + "\",\"date\":\"" + invoice.getDate() +"\"},";                
				
			}
			
		}else {
			if(argValue1!="" & argValue2!="") {
				ArrayList<Integer> iDList = new ArrayList<>();
				List<Invoice> invoiceList = db.returnInvoices(session);
				for (Invoice invoice : invoiceList) {
					int invoiceYear = Integer.parseInt(invoice.getDate().substring(0, 4));
					int invoiceMonth = Integer.parseInt(invoice.getDate().substring(5, 7));
					int invoiceDay = Integer.parseInt(invoice.getDate().substring(8, 10));
					int yearFrom = Integer.parseInt(argValue1.substring(0,4));
					int MonthFrom = Integer.parseInt(argValue1.substring(5, 7));
					int DayFrom = Integer.parseInt(argValue1.substring(8, 10));
					int yearTo = Integer.parseInt(argValue2.substring(0,4));
					int MonthTo = Integer.parseInt(argValue2.substring(5, 7));
					int DayTo = Integer.parseInt(argValue2.substring(8, 10));
					
					
					if(invoiceYear >= yearFrom & invoiceYear <= yearTo){
						if(invoiceMonth >= MonthFrom & invoiceMonth <= MonthTo) {
							if(invoiceDay >= DayFrom & invoiceDay <= DayTo) {
								iDList.add(invoice.getInvoiceID());
								System.out.println(invoice.getInvoiceID());
							}
						}
					}
				}
				
				List<Invoice> List = db.returnItemsDepONIDs(session, iDList);
				for (Invoice invoice : List) {
					for(Item item:invoice.getItems())
					result =result +"{ \"itemName\":\"" + item.getItemName() + "\",\"unit\":\"" + item.getUnit() + "\",\"quantity\":\"" + item.getQunatity() + "\",\"category\":\"" + item.getCategory() + "\",\"venderCode\":\"" + item.getVenderCode() + "\",\"description\":\"" +item.getDescription()+ "\",\"price\":\"" +item.getPrice() + "\",\"invoiceNo\":\"" + invoice.getInvoiceNo() + "\",\"vender\":\"" + invoice.getVender() + "\",\"currancy\":\"" + invoice.getCurrancy() + "\",\"date\":\"" + invoice.getDate() +"\"},";                
					
				}
				
			}
		}	
		if(result.length()>20) {
			result =result.substring(0, result.length()-1);
		}else {
			
		}
		System.out.println(result +"]}");
		response.getWriter().println(result +"]}");
		
	}


	@Override
	public void destroy() {
		session.close();
		
	}
	
	

}
