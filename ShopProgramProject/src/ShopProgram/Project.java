package ShopProgram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.crypto.Data;


@Entity
public class Project {
	
	@TableGenerator(
    name="empGen", 
    table="ID_GEN", 
    pkColumnName="GEN_KEY", 
    valueColumnName="GEN_VALUE", 
    pkColumnValue="EMP_ID", 
    allocationSize=1)


	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="empGen")
	
	
	
	
	//@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column (name="PROJECT_ID")
	private int projectId;
	@Column (name = "PROJECT_NAME")
	private String projectName;
	@Column (name = "PERSON_Name")
	private String personName;
	@Temporal (TemporalType.DATE)
	private Date date;
	
	@ElementCollection
	private Collection<ChoosenProduct> productList = new ArrayList<ChoosenProduct>();
	
	
	
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public Collection<ChoosenProduct> getProductList() {
		return productList;
	}

	public void setProductList(Collection<ChoosenProduct> productList) {
		this.productList = productList;
	}
	
}
