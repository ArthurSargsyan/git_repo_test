package beans;

public class Item {
	
	private int itemID = 0;
	private String itemName="";
	private String itemCategory="";
	private String venderCode="";
	private String unit="";
	private int quantity=0;
	private String description="";
	private double price=0.0;
	private Invoice invoice=null;
	
	
	public Item() {
	}
	public Item( String itemName, String itemCategory, String venderCode, String unit, int quantity, String description,
			double price) {
		this.itemName = itemName;
		this.itemCategory = itemCategory;
		this.venderCode = venderCode;
		this.unit = unit;
		this.quantity = quantity;
		this.description = description;
		this.price = price;
	}
		
	
	
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	public String getItemCategory() {
		return itemCategory;
	}
	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getCategory() {
		return itemCategory;
	}
	public void setCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}
	public String getVenderCode() {
		return venderCode;
	}
	public void setVenderCode(String venderCode) {
		this.venderCode = venderCode;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getQunatity() {
		return quantity;
	}
	public void setQunatity(int quantity) {
		this.quantity = quantity;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
		
}
