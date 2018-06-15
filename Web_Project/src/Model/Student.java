package Model;

public class Student {
	int sid;
	String sName=null;
	String semail=null;
	String sphonenumber=null;
	
	public Student() {
		
	}
	public Student(int id,String firstName,String lastName,String age){
		this.sid = id;
		this.sName = firstName;
		this.semail = lastName;
		this.sphonenumber = age;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getsName() {
		return sName;
	}
	public void setsName(String sName) {
		this.sName = sName;
	}
	public String getSemail() {
		return semail;
	}
	public void setSemail(String semail) {
		this.semail = semail;
	}
	public String getSphonenumber() {
		return sphonenumber;
	}
	public void setSphonenumber(String sphonenumber) {
		this.sphonenumber = sphonenumber;
	}
	
	
	

}
