package ShopProgram;

import javax.swing.JOptionPane;
import Hibernate.WareHouse;


public class Buy {
	
	boolean checkIsSmallInWarehouse = false;
		
	//Move product to the specific basket by name and quantity. Correcting warehouse data.  	
	public void buyProduct(String prodName,String prodQuantity,Basket basketName) {
		try {
		WareHouse chooseP = new WareHouse();
		int choosenid=chooseP.chooseProductId(prodName);
				
		Product choosenProduct = chooseP.chooseProduct(choosenid);
		if (choosenProduct.getQuantity()>=Double.parseDouble(prodQuantity)) {
			choosenProduct.setQuantity(Double.parseDouble(prodQuantity));
		
			basketName.addToBasket(choosenProduct);
			System.out.println("******add to basket******");
			System.out.println("Quantity is "+ choosenProduct.getQuantity());
		} else {
			JOptionPane.showMessageDialog(null, "Quantity Isn't Enough In Warehouse\nQuantity Is   "+choosenProduct.getQuantity());
			if (choosenProduct.getQuantity()>0) {
			checkIsSmallInWarehouse= true;
			}else {
				checkIsSmallInWarehouse= false;	
			}
		}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "This Product Is Missing In Waerhouse");
		}
	}
	
	//Exclude product from specific basket by name and return to warehouse.
	public void returnProduct(String prodName,double excludedQuantity, Basket basketName) {
		for (int i=0;i<basketName.getBasketList().size();i++) {
			if (basketName.getBasketList().get(i).getProductName().equals(prodName)) {
				basketName.removeFromBasket(basketName.getBasketList().get(i),excludedQuantity);
			}
		}
	}

		
	/*public static void main(String[] args) {
	Buy buy = new Buy();
	Basket basketToBuy = new  Basket();
	basketToBuy.basketList.clear();
	buy.buyProduct("Kotaik", "5",basketToBuy);
	buy.buyProduct("Kotaik", "5",basketToBuy);
	buy.buyProduct("Sexan  ", "2",basketToBuy);
	buy.buyProduct("Sexan  ", "2",basketToBuy);
	buy.buyProduct("Gtal", "3",basketToBuy);
	buy.buyProduct("Gtal", "2",basketToBuy);
	buy.buyProduct("Gtal", "5",basketToBuy);
	buy.buyProduct("Gtal", "5",basketToBuy);
	buy.returnProduct("Gtal",5,basketToBuy);
	
	System.out.println(basketToBuy.getBasketList());
	System.out.println(basketToBuy.getBasketList().size());
		
	for (int i=0; i<basketToBuy.basketList.size(); i++) {
	System.out.println(basketToBuy.getBasketList().get(i).getProductName());
	System.out.println(basketToBuy.getBasketList().get(i).getProductId());
	System.out.println(basketToBuy.getBasketList().get(i).getQuantity());
	}
	}*/
}
