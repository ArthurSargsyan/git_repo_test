package beans;

import java.util.Date;

public class MoveToShopItems extends Item {

	private Date moveToShopDate = null;
	
	public MoveToShopItems() {
	}

	public MoveToShopItems(String itemName, String itemCategory, String venderCode, String unit, int quantity,
			String description, double price,Date date) {
		super(itemName, itemCategory, venderCode, unit, quantity, description, price);
		this.moveToShopDate = date;
	}

	public Date getMoveToShopDate() {
		return moveToShopDate;
	}

	public void setMoveToShopDate(Date moveToShopDate) {
		this.moveToShopDate = moveToShopDate;
	}

}
