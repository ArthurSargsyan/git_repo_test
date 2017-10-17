package ShopProgram;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import Hibernate.AddToTable;
import Hibernate.CheckAvailability;
import Hibernate.HibernateTest;
import Hibernate.UpdateProduct;


public class SoldProductGUI extends JFrame {
	
	Buy shopBuy = new Buy();
	Basket  basketName = new Basket();
	
	JButton addButton;
	JButton removeButton;
	JButton calculateButton;
	JButton search;
	static JTextField textBoxToEnterName;
	JTextField textBoxToEnterPrice;
	JTextField textBoxToEnterQuantity;
	JTextArea basketTestArea;

    	public SoldProductGUI(){

    	JLabel productName = new JLabel("  Name:  ");
        JLabel productQuatity = new JLabel("  Quantity:  ");
        JLabel productPrice= new JLabel("  Total Amount:  ");
        JLabel basket = new JLabel("Basket");
        
        textBoxToEnterName = new JTextField(20);
        textBoxToEnterQuantity = new JTextField(20);
        textBoxToEnterPrice = new JTextField(20);
        textBoxToEnterPrice.setEditable(false);
        
        basketTestArea = new JTextArea(10,30);
        basketTestArea.setEditable(false);
        basketTestArea.setText("NAME\tQUANTITY\tPRICE\tTOTAL\nBasket Is Empty");
                
        basketTestArea.setLineWrap(false);
        basketTestArea.setWrapStyleWord(true);
        basketTestArea.setFont(new Font("Tahoma", Font.ITALIC, 12));
        
        JScrollPane qScroller = new JScrollPane(basketTestArea);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        
        
        
        
        
        
  
        
        JPanel panelEast = new JPanel();
        JPanel panelWest = new JPanel();
        JPanel panelcenter = new JPanel();
        panelEast.setLayout(new BoxLayout(panelEast,BoxLayout.Y_AXIS));
        panelcenter.setLayout(new BoxLayout(panelcenter,BoxLayout.Y_AXIS));
        panelWest.setLayout(new BoxLayout(panelWest, BoxLayout.Y_AXIS));
        
       // panelWest.add(productName);
        //panelWest.add(productQuatity);
        //panelWest.add(productPrice);
        panelcenter.add(productName);
        panelcenter.add(textBoxToEnterName);
        panelcenter.add(productQuatity);
        panelcenter.add(textBoxToEnterQuantity);
        panelcenter.add(productPrice);
        panelcenter.add(textBoxToEnterPrice);
        
               
        panelEast.add(basket);
        panelEast.add(qScroller);
        
        
        
        
        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        calculateButton = new JButton("Calculate Price");
        search = new JButton("Search");
        
        addButton.addActionListener(new addButton(textBoxToEnterName,textBoxToEnterQuantity));
        removeButton.addActionListener(new RemoveButton(textBoxToEnterName,textBoxToEnterQuantity));
        calculateButton.addActionListener(new CalculateButton());
        search.addActionListener(new SearchButton());
     
        
        JPanel panelBottom = new JPanel();
        
        panelBottom.add(addButton);
        panelBottom.add(removeButton);
        panelBottom.add(calculateButton);
        panelBottom.add(search);
        
        add(panelEast, BorderLayout.EAST);
        //add(panelWest, BorderLayout.WEST);
        add(panelBottom, BorderLayout.SOUTH);
        add(panelcenter, BorderLayout.CENTER);
        

        setTitle("Sale Products");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    	public class addButton implements ActionListener {
    	
    		JTextField nameInput;
    		JTextField quantityInput;
	   
    		public addButton(JTextField textFieldForName,JTextField textFieldForQuantity){
    			nameInput = textFieldForName;	
    			quantityInput = textFieldForQuantity;
		   
    		}

    		@Override
    		public void actionPerformed(ActionEvent addClicked) {
    			setVisible(true); 
    			
    			if (nameInput.getText().equals("")||quantityInput.getText().equals("")) {
    				JOptionPane.showMessageDialog(null, "Please Insert Fields Of Window");
    				//System.out.println("Please Insert All Fields of Window");
    			} else {  
    				shopBuy.moveToBasket(nameInput.getText(), quantityInput.getText(),basketName);
    							
    			}
    			
    			if (shopBuy.checkIsSmallInWarehouse) {
    				textBoxToEnterName.setText(nameInput.getText());
    			}else {
    	   textBoxToEnterName.setText(""); 
    			}
		   textBoxToEnterQuantity.setText("");
		   	   
		   String basketListByString ="NAME\tQUANTITY\tPRICE\tTOTAL\n";
		   for (int i=0; i < basketName.getBasketList().size(); i++) {
			   Product prod= basketName.getBasketList().get(i);
			   basketListByString = basketListByString + prod.getProductName() + "\t" + prod.getQuantity() + "\t" + prod.getProductPrice()+"\t" +prod.getQuantity()*prod.getProductPrice()+"\n"; 
		   }
		   basketListByString = basketListByString +"\t\t\t--------\n\t\t\t"+shopBuy.calculateAllProductsPriceInBasket(basketName); 
		   basketTestArea.setText(basketListByString);
      }
    			
   }

    	public class RemoveButton implements ActionListener {
    		JTextField nameInput;
    		JTextField quantityInput;
	   
    		public RemoveButton(JTextField textFieldForName,JTextField textFieldForQuantity ){
    			nameInput = textFieldForName;
    			quantityInput = textFieldForQuantity; 
    		}

    		@Override
    		public void actionPerformed(ActionEvent removeClicked) {
    			setVisible(true);  
		        if (nameInput.getText().equals("")||quantityInput.getText().equals("")) {
		        	JOptionPane.showMessageDialog(null, "Please Insert Fields Of Window");
		   			//System.out.println("Please Insert Field Name in Window");
			    } else {  
			    	shopBuy.excludeFromBasket(nameInput.getText(), Double.parseDouble(quantityInput.getText()), basketName);
			    }
            
		       textBoxToEnterName.setText("");
		       textBoxToEnterQuantity.setText("");
		       	       
		       
		       
		       String basketListByString ="NAME\tQUANTITY\tPRICE\tTOTAL\n";
			   for (int i=0; i < basketName.getBasketList().size(); i++) {
				   if (nameInput.getText().equals(basketName.getBasketList().get(i).getProductName())) {
					   Product prodY= basketName.getBasketList().get(i);
					   basketListByString = basketListByString + prodY.getProductName() + "\t" + prodY.getQuantity() + "\t" + prodY.getProductPrice()+"\t" +prodY.getQuantity()*prodY.getProductPrice()+"\n"; 
					   
				   }else {
				   Product prod= basketName.getBasketList().get(i);
				   
				   basketListByString = basketListByString + prod.getProductName() + "\t" + prod.getQuantity() + "\t" + prod.getProductPrice()+"\t" +prod.getQuantity()*prod.getProductPrice()+"\n"; 
			   }
				    
			   }
			   basketListByString = basketListByString +"\t\t\t--------\n\t\t\t"+shopBuy.calculateAllProductsPriceInBasket(basketName);
			   basketTestArea.setText(basketListByString);
		       
		       
		       
      	   }
    	}
    	    	
    	public class CalculateButton implements ActionListener {  
    		@Override
    		public void actionPerformed(ActionEvent CalculateClicked) {
    		   setVisible(true);
    		   if (shopBuy.calculateAllProductsPriceInBasket(basketName)>0){
    			   textBoxToEnterPrice.setText(Double.toString(shopBuy.calculateAllProductsPriceInBasket(basketName)));   
    		   }else {
    			  JOptionPane.showMessageDialog(null, "Basket Is Empty");
            }
    		}
       }
         
    	public class SearchButton implements ActionListener {
    		
    		@Override
    		public void actionPerformed(ActionEvent addToWareHouseClicked) {
    			System.out.println("SearchProducts is clicked");
    			setVisible(true);
    			SearchGUI searchGUI = new SearchGUI();
    			searchGUI.setVisible(true);
    			
    			searchGUI.addWindowListener(new WindowAdapter() {
    				@Override
    				public void windowClosing(WindowEvent e) {
    					
    					searchGUI.setVisible(false);
        				super.windowClosing(e); //  --------------------------
    				}
    			});
    		}
    	}
    	
    
//    	public static void main(String[] args) {
//    	SoldProductGUI soldProductGUI = new SoldProductGUI();
//    	soldProductGUI.setVisible(true);
//        System.out.println(soldProductGUI.addButton.isSelected());
//    }

}