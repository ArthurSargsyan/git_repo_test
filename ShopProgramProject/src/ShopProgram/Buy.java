package ShopProgram;

import javax.swing.JOptionPane;
import Hibernate.ChooseProduct;


public class Buy {
	
	boolean checkIsSmallInWarehouse = false;
	
	//Move product to the specific basket by name and quantity. Correcting warehouse data.  	
	public void moveToBasket(String prodName,String prodQuantity,Basket basketName) {
		try {
		ChooseProduct chooseP = new ChooseProduct();
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
	public void excludeFromBasket(String prodName,double excludedQuantity, Basket basketName) {
		for (int i=0;i<basketName.getBasketList().size();i++) {
			if (basketName.getBasketList().get(i).getProductName().equals(prodName)) {
				basketName.removeFromBasket(basketName.getBasketList().get(i),excludedQuantity);
			}
		}
	}

	//Calculate basket's products price and return common price.
	public double calculateAllProductsPriceInBasket(Basket basketName) {
		
		double basketProdsPrice = 0;
		for (int i=0; i<basketName.getBasketList().size(); i++) {
			Product y = basketName.getBasketList().get(i);
			double basketProdPrice = y.getQuantity()*y.getProductPrice();
			basketProdsPrice = basketProdsPrice + basketProdPrice;
		}
		System.out.println(Double.toString(basketProdsPrice));
		return basketProdsPrice;
	}
	
	/*public static void main(String[] args) {
	Buy buy = new Buy();
	Basket basketToBuy = new  Basket();
	basketToBuy.basketList.clear();
	buy.moveToBasket("Kotaik", "5",basketToBuy);
	buy.moveToBasket("Kotaik", "5",basketToBuy);
	buy.moveToBasket("Sexan  ", "2",basketToBuy);
	buy.moveToBasket("Sexan  ", "2",basketToBuy);
	buy.moveToBasket("Gtal", "3",basketToBuy);
	buy.moveToBasket("Gtal", "2",basketToBuy);
	buy.moveToBasket("Gtal", "5",basketToBuy);
	buy.moveToBasket("Gtal", "5",basketToBuy);
	buy.excludeFromBasket("Gtal",5,basketToBuy);
	
	System.out.println(basketToBuy.getBasketList());
	System.out.println(basketToBuy.getBasketList().size());
		
	for (int i=0; i<basketToBuy.basketList.size(); i++) {
	System.out.println(basketToBuy.getBasketList().get(i).getProductName());
	System.out.println(basketToBuy.getBasketList().get(i).getProductId());
	System.out.println(basketToBuy.getBasketList().get(i).getQuantity());
	}
	  double p = buy.calculateAllProductsPriceInBasket(basketToBuy);
    System.out.println(Double.toString(p));
	}*/
}
