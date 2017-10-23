package ShopProgram;

import javax.persistence.Column;
import javax.persistence.Embeddable;



@Embeddable
public class ChoosenProduct{
	
	/*@TableGenerator(
            name="empGen", 
            table="ID_GEN", 
            pkColumnName="GEN_KEY", 
            valueColumnName="GEN_VALUE", 
            pkColumnValue="EMP_ID", 
            allocationSize=1)


    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="empGen")
  	@Column (name="ID")
	private int productId;*/
	@Column (name="PRICE")
	private double productPrice;
	@Column (name="NAME")
	private String productName;
	@Column (name="QUANTITY")
	private double quantity;
		
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	/*public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}*/
	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
}
