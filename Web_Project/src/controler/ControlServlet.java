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

import Listeners.MyContextListener;
import Model.DataBase;
import beans.Invoice;
import beans.Item;

/**
 * Servlet implementation class ControlServlet
 */
@WebServlet(value="/ControlServlet",loadOnStartup=1)
public class ControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int i=0;
	
	Session session=null;
	
	@Override
	public void init() throws ServletException {
		session = MyContextListener.sf.openSession();
		}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	 String incomeDate = req.getParameter("incomeDate");
	 String invoiceNo = req.getParameter("invoiceNo");
	 String incomeCurrency = req.getParameter("incomeCurrency");
	 String	vender = req.getParameter("vender");
	 String	venderCode = req.getParameter("venderCode");
	 String	itemName = req.getParameter("itemName");
	 String	description = req.getParameter("description");
	 String	unit = req.getParameter("unit");
	 String	itemCategory = req.getParameter("itemCategory");
	 int quantity=0;
	 double	price;
	 if(req.getParameter("quantity")=="") {
		 quantity = 0;
	 }else {
		quantity = Integer.parseInt(req.getParameter("quantity"));
	 }
	 
	 if(req.getParameter("quantity")=="") {
		 price = 0.0;
	 }else {
		 price = Double.parseDouble(req.getParameter("price"));
	 }
	 Item item = new Item(itemName, itemCategory, venderCode, unit, quantity, description, price);
	 Invoice invoice = new Invoice(invoiceNo, vender, incomeCurrency, incomeDate);
	
	 DataBase db = new DataBase();
	 db.addInvoiceToDB(session, invoice);
	 db.addItemToDB(MyContextListener.sf, item);
	  
	 resp.getWriter().println("SUCCESSS!");
	} 

	@Override
	public void destroy() {
		session.close();
	}
	
	
}

