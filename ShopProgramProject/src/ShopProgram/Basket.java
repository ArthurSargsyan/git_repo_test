package ShopProgram;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Basket {
		
	ArrayList<Product> basketList = new ArrayList<Product>();
		
	public ArrayList<Product> getBasketList() {
		return basketList;
	}

	public void setBasketList(ArrayList<Product> basketList) {
		this.basketList = basketList;
	}
	//Add product to the basket , if there is product like this it updates only quantity.
	public void addToBasket(Product p) {    
		System.out.println(" p + "+p.getQuantity());
	
		if (basketList.isEmpty()) {
			basketList.add(p);
		} else {
			boolean bool = true ;
			for (int i=0; i<basketList.size(); i++) {
				Product prod = basketList.get(i);
								System.out.println(" prod "+prod.getQuantity()+ prod.getProductName());
				if (prod.getProductName().equals(p.getProductName())) {
					bool = false;
								System.out.println(prod.getProductName()+" ----" +p.getProductName() );
					double x= prod.getQuantity()+p.getQuantity();
								System.out.println(" prod "+prod.getQuantity());
								System.out.println(" p "+p.getQuantity());
					prod.setQuantity(x);
								System.out.println("General quantity is    " + Double.toString(x) + "  " + prod.getProductName());
					basketList.remove(i);
					basketList.add(i, prod);
				} 
			};
			if (bool) {
			basketList.add(p);
			}
		}
		
	}
	//Remove product from the basket by removed quantity.
	public void removeFromBasket(Product p,double removedQuantity) {
		 
		 for (int i=0; i<basketList.size(); i++) {
			Product prod = basketList.get(i);
			if (prod.getProductName().equals(p.getProductName())) {
				double x = prod.getQuantity() - removedQuantity;
				if (x<0) {
					JOptionPane.showMessageDialog(null, "Removed Quantity Is More Then In Basket");
				}else {
				prod.setQuantity(x);
				}
			}
			basketList.remove(i);
			basketList.add(i,prod);
		 }
	}
	//Return  ArrayList<Product> of products.
	public ArrayList<Product> basketsProductList(Basket basketName) { 
		ArrayList<Product>  basketArray = new ArrayList<Product>();
		int z =basketName.getBasketList().size();
		for (int i = 0; i<z; i++) {
	    	   Product prod = basketName.getBasketList().get(i);
	    	   basketArray.add(prod);
		}
		return basketArray;
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
		Basket bask = new Basket();
		Product prod0  = new Product();
		prod0.setProductName("Gtal");
		prod0.setQuantity(5);
		Product prod1 = new Product();
		prod1.setProductName("Kilikia");
		prod1.setQuantity(5);
		Product prod2 = new Product();
		prod2.setProductName("Sexan");
		prod2.setQuantity(5);
			
		bask.addToBasket(prod0);
		bask.addToBasket(prod0);
		bask.addToBasket(prod0);
		bask.addToBasket(prod0);
		bask.addToBasket(prod1);
		bask.addToBasket(prod2);
		bask.addToBasket(prod2);
		bask.addToBasket(prod2);
		bask.addToBasket(prod2);
	
		bask.removeFromBasket(prod2,2.5);
			
		for (int i=0; i<bask.getBasketList().size(); i++) {
			System.out.println(bask.getBasketList().get(i).getQuantity());
		}
		System.out.println(bask.basketsProductList(bask));
		}*/
}
