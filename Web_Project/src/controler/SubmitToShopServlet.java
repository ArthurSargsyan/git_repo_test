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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Listeners.MyContextListener;
import Model.DataBase;
import beans.ItemInShop;


@WebServlet("/SubmitToShop")
public class SubmitToShopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Session session=null;
	DataBase db= new DataBase();
	
	@Override
	public void init() throws ServletException {
		session = MyContextListener.sf.openSession();
	}
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String toshopitemname = "";
		int toshopquantity = 0;
		String toshopdate = "";
		String toshopdescription = "";
		String toshopcategory = "";
		String toshopvendercode = "";
		String toshopunit = "";
		double toshopprice = 0.0;
		
		try {
			
			JSONObject resultJSON = new  JSONObject(request.getParameter("result"));
			JSONArray resultArray = resultJSON.getJSONArray("itemnames");
			
			for(int i=0 ; i< resultArray.length() ; i++) {
				
				toshopitemname = resultArray.getJSONObject(i).getString("toshopitemname");

				String quantity = resultArray.getJSONObject(i).getString("toshopquantity");
				
				if(!quantity.equals("")) {
					toshopquantity = Integer.parseInt(quantity);
				}
				toshopdate = resultArray.getJSONObject(i).getString("toshopdate");
				toshopdescription = resultArray.getJSONObject(i).getString("toshopdescription");
				toshopcategory = resultArray.getJSONObject(i).getString("toshopcategory");
				toshopvendercode = resultArray.getJSONObject(i).getString("toshopvendercode");
				toshopunit = resultArray.getJSONObject(i).getString("toshopunit");
				
				String price = resultArray.getJSONObject(i).getString("toshopprice");
				if(!price.equals("")) {
					toshopprice = Double.parseDouble(resultArray.getJSONObject(i).getString("toshopprice"));
				}
			
				ItemInShop itemInShop = new ItemInShop(toshopitemname, toshopcategory, toshopvendercode, toshopunit, toshopquantity, toshopdescription, toshopprice);
				
				System.out.println("Requset recives for itemInShop");

				db.addItemForShopToDB(MyContextListener.sf, itemInShop);
			}
		} catch (JSONException e) {
			e.printStackTrace(); 
		}
		response.getWriter().println("resut successes!!");
	}
	
	
	@Override
	public void destroy() {
		session.close();
	}
		
}
