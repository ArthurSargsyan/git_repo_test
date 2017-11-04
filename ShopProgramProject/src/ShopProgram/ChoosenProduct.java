package ShopProgram;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class ChoosenProduct{
	
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
