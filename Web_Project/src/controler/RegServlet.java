package controler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.JDBC_Access;


@WebServlet("/reg")
public class RegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int i=0;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
	 String firstName=req.getParameter("firstName");
	 String lastName=req.getParameter("lastName");
	 String age=req.getParameter("age");
	  int id= Integer.parseInt(age);
	 JDBC_Access db = new JDBC_Access();
	 String res = db.addDB1(id);
	 
	 
	 System.out.println(firstName);
	 System.out.println(lastName);
	 System.out.println(age);
	 
	 resp.getWriter().println("SUCCESSSSSSSS!"+" "+ res + "-" + i++);
	
	} 

}
