package ShopProgram;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


public class AddToWareHouse extends JFrame {
	
	JButton submit;
	JTextArea wareHouseTable = new JTextArea(10,30);
	JTextField textBoxToEnterName;
	JTextField textBoxToEnterPrice;
	JTextField textBoxToEnterQuantity;
	ShopOfProducts shop = new ShopOfProducts();

    public AddToWareHouse(){

    	JLabel productName = new JLabel("  Name:  ");
        JLabel productPrice = new JLabel("  Price:  ");
        JLabel productQuatity = new JLabel("  Quantity:  ");
        
        textBoxToEnterName = new JTextField(20);
        textBoxToEnterPrice = new JTextField(20);
        textBoxToEnterQuantity = new JTextField(20);
        
        
        JPanel panelEast = new JPanel();
        JPanel panelWest = new JPanel();
        panelEast.setLayout(new BoxLayout(panelEast,BoxLayout.Y_AXIS));
        panelWest.setLayout(new BoxLayout(panelWest,BoxLayout.Y_AXIS));
    
        
        panelEast.add(productName);
        panelEast.add(textBoxToEnterName);
        panelEast.add(productPrice);
        panelEast.add(textBoxToEnterPrice);
        panelEast.add(productQuatity);
        panelEast.add(textBoxToEnterQuantity);
        
        
        
        wareHouseTable.setLineWrap(false);
		wareHouseTable.setWrapStyleWord(true);
		wareHouseTable.setFont(new Font("Tahoma", Font.ITALIC, 12));
		JScrollPane qScroller = new JScrollPane(wareHouseTable);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        wareHouseTable.setEditable(false);
        
        
		wareHouseTable.setText("                WAREHOUSE\nNAME\t\tPRICE\tQUANTITY\n"+shop.updateWarehouseInTable());
		
		
		
        
        submit = new JButton("Add");
        
        submit.addActionListener(new SubmitButton(textBoxToEnterName,textBoxToEnterPrice,textBoxToEnterQuantity));
        JPanel panelBottom = new JPanel();
        
        panelBottom.add(submit);
        
        add(panelEast, BorderLayout.EAST);
        add(panelBottom, BorderLayout.SOUTH);
        add(qScroller, BorderLayout.WEST);
        

        setTitle("Add To Warehouse");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    public class SubmitButton implements ActionListener {

	   JTextField nameInput;
	   JTextField priceInput;
	   JTextField quantityInput;

	   public SubmitButton(JTextField textFieldForName,JTextField textFieldForPrice,JTextField textFieldForQuantity){
		   nameInput = textFieldForName;	
		   priceInput = textFieldForPrice;
		   quantityInput = textFieldForQuantity;
       }

	   @Override
       public void actionPerformed(ActionEvent submitClicked) {
       setVisible(true); 
       
       if (nameInput.getText().equals("")||priceInput.getText().equals("")||quantityInput.getText().equals("")) {
    	   JOptionPane.showMessageDialog(null, "Please Insert Fields Of Window");
    	   System.out.println("Please Insert All Fields of Window");
			
	} else {
       
            CheckAvailability check = new CheckAvailability();
            int y = check.checkAvailability(nameInput.getText(),Double.parseDouble(priceInput.getText()));
            if (y == 0) {
            	AddToTable addProd= new AddToTable();
                addProd.addProduct(nameInput.getText(),priceInput.getText(),quantityInput.getText());
                
            } else {
            	      	
            	
            	UpdateProduct update = new UpdateProduct();
            	update.updateProduct(y, quantityInput.getText(),Double.parseDouble(priceInput.getText()));
            	
            }
       textBoxToEnterName.setText(""); 
       textBoxToEnterPrice.setText("");
       textBoxToEnterQuantity.setText("");
                     
       	}
       wareHouseTable.setText("                WAREHOUSE\nNAME\t\tPRICE\tQUANTITY\n"+shop.updateWarehouseInTable());
	   }
	  	   
   }

//    public static void main(String[] args) {
//    	AddToWareHouse addToWareHouse = new AddToWareHouse();
//    	addToWareHouse.setVisible(true);
//        System.out.println(addToWareHouse.submit.isSelected());
//    }

}