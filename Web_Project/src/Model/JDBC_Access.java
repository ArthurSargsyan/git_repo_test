package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_Access {
	
	
	public String addDB1(int i) {
		 String result = null;
		try {
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/DataBase", "postgres", "server");
	        Statement st = con.createStatement();
	        System.out.println("****");
	       int maxId=0;
	       ResultSet rs1= st.executeQuery("select max(sid) from student"); 
			while(rs1.next()) {
				maxId=rs1.getInt(1);
			}
			
			if(i>maxId) {
				result = "Student with "+i+" dosen't exist";
			}else if(i==0){
				result = "Student id's starts with 1, Please insert correct id";
			}else{
	       //st.executeUpdate("INSERT INTO student values("+s.sid + ","+s.sName + "," + s.semail + "," + s.sphonenumber + ");");
		ResultSet rs= st.executeQuery("select * from student where sid=" + i); 
		while(rs.next()) {
			System.out.println(result=rs.getString(3));
		}
			}
			}catch (SQLException | ClassNotFoundException e) {
	         e.printStackTrace();
		 }
		
		
		return  result;
	}
	
	
	public String addDB(Student s) {
		 String result = null;
		try {
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/DataBase", "postgres", "server");
	        Statement st = con.createStatement();
	        System.out.println("****");
	        //st.executeUpdate("INSERT INTO student values("+s.sid + ","+s.sName + "," + s.semail + "," + s.sphonenumber + ");");
		ResultSet rs= st.executeQuery("select * from student where sid=1"); 
		while(rs.next()) {
			System.out.println(result=rs.getString(3));
		}
		}catch (SQLException | ClassNotFoundException e) {
	         e.printStackTrace();
		 }
		return  result;
	}
	

//	public static void main(String[] args) {
//		Student stud = new Student(9, "artur", "Sargsyan", "35");
//		System.out.println(stud.sid);
//		System.out.println(stud.sName);
//		System.out.println(stud.semail);
//		System.out.println(stud.sphonenumber);
//
//		JDBC_Access db = new JDBC_Access();
//		db.addDB1();
//
//	}

}
