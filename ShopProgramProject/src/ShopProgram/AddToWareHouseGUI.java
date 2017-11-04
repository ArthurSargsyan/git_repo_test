package ShopProgram;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import Hibernate.WareHouse;



public class AddToWareHouseGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
			
	JButton add;
	JTextField textBoxToEnterName;
	JTextField textBoxToEnterPrice;
	JTextField textBoxToEnterQuantity;
	ShopOfProductsGUI shop = new ShopOfProductsGUI();
	DefaultTableModel model;
	JTable table;
	

    public AddToWareHouseGUI(){
    	
    	    	
    	model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("Name");
		model.addColumn("Price");
		model.addColumn("Quantity");
		
		
		table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.setSize(50,150);
		table.setEnabled(false);
		table.setIntercellSpacing(new Dimension(5, 0));
		
	
		WareHouse prod = new WareHouse();
		ArrayList<Product> prodList = prod.chooseAllProductArrayList();
		model.setRowCount(0);
		for (int i=0; i<prodList.size(); i++) {
		model.addRow(new Object[]{i+1,prodList.get(i).getProductName(),Double.toString(prodList.get(i).getProductPrice()),Double.toString(prodList.get(i).getQuantity())});
		}
    	
		
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
        
        
		JScrollPane qScroller = new JScrollPane(table);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
       
        add = new JButton("Add");
        
        add.addActionListener(new AddButton(textBoxToEnterName,textBoxToEnterPrice,textBoxToEnterQuantity));
        JPanel panelBottom = new JPanel();
        
        panelBottom.add(add);
        
        add(panelEast, FlowLayout.LEFT);
        add(panelBottom, BorderLayout.SOUTH);
        add(qScroller, BorderLayout.WEST);
        
        setTitle("Add To Warehouse");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    public class AddButton implements ActionListener {

	   JTextField nameInput;
	   JTextField priceInput;
	   JTextField quantityInput;

	   public AddButton(JTextField textFieldForName,JTextField textFieldForPrice,JTextField textFieldForQuantity){
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
            WareHouse check = new WareHouse();
            int y = check.checkAvailability(nameInput.getText(),Double.parseDouble(priceInput.getText()));
            if (y == 0) {
            	WareHouse addProd= new WareHouse();
                addProd.addProduct(nameInput.getText(),priceInput.getText(),quantityInput.getText());
            } else {
               	WareHouse update = new WareHouse();
            	update.updateProduct(y, quantityInput.getText(),Double.parseDouble(priceInput.getText()));
            }
         	
            WareHouse prod = new WareHouse();
           	ArrayList<Product> prodList = prod.chooseAllProductArrayList();
           	model.setRowCount(0);
       		for (int i=0; i<prodList.size(); i++) {
       		model.addRow(new Object[]{i+1,prodList.get(i).getProductName(),Double.toString(prodList.get(i).getProductPrice()),Double.toString(prodList.get(i).getQuantity())});
       	   }
       }     
            
       textBoxToEnterName.setText(""); 
       textBoxToEnterPrice.setText("");
       textBoxToEnterQuantity.setText("");
      }
	}
  
    /*public static void main(String[] args) {
		AddToWareHouse addToWareHouse = new AddToWareHouse();
		addToWareHouse.setVisible(true);
   	//System.out.println(addToWareHouse.submit.isSelected());
  	}*/
}