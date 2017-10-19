package ShopProgram;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
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


public class SoldProductGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
		
	Buy shopBuy = new Buy();
	Basket  basketName = new Basket();
	
	JButton addButton;
	JButton removeButton;
	JButton calculateButton;
	JButton search;
	static JTextField textBoxToEnterName;
	JTextField textBoxToEnterPrice;
	static JTextField textBoxToEnterQuantity;
	DefaultTableModel model;
	JTable basketTable;

    public SoldProductGUI(){

    		
    	model= new DefaultTableModel();
    	model.addColumn("ID");
    	model.addColumn("Name");
    	model.addColumn("Price");
    	model.addColumn("Quantity");
    	model.addColumn("Total");
    		
    	basketTable = new JTable(model);
    	basketTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    	basketTable.setSize(50, 150);
    	basketTable.setEnabled(false);
    	basketTable.setIntercellSpacing(new Dimension(5,0));
    	
    	JLabel productName = new JLabel("  Name:  ");
        JLabel productQuatity = new JLabel("  Quantity:  ");
        JLabel productPrice= new JLabel("  Total Amount:  ");
        JLabel basket = new JLabel("Basket");
        
        textBoxToEnterName = new JTextField(20);
        textBoxToEnterQuantity = new JTextField(20);
        textBoxToEnterPrice = new JTextField(20);
        textBoxToEnterPrice.setEditable(false);
        
        
        JScrollPane qScroller = new JScrollPane(basketTable);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        JPanel panelEast = new JPanel();
        JPanel panelWest = new JPanel();
        JPanel panelcenter = new JPanel();
        panelEast.setLayout(new BoxLayout(panelEast,BoxLayout.Y_AXIS));
        panelcenter.setLayout(new BoxLayout(panelcenter,BoxLayout.Y_AXIS));
        panelWest.setLayout(new BoxLayout(panelWest, BoxLayout.Y_AXIS));
              
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
        add(panelBottom, BorderLayout.SOUTH);
        add(panelcenter, BorderLayout.CENTER);
       
        setTitle("Sale Products");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }
    	
    //Do Update of Basket and refresh table.
    public void updateBasket() {
    	model.setRowCount(0);
 		int i;
 		double y=0;
 		for (i=0;i<basketName.getBasketList().size(); i++) { 		  
 			   model.addRow(new Object[]{i+1,basketName.getBasketList().get(i).getProductName(),
 					   					Double.toString(basketName.getBasketList().get(i).getProductPrice()),
 					   					Double.toString(basketName.getBasketList().get(i).getQuantity()),
 					   				    Double.toString(customFormat("###.##",basketName.getBasketList().get(i).getProductPrice()*basketName.getBasketList().get(i).getQuantity()))});  
 			   y=y+basketName.getBasketList().get(i).getProductPrice()*basketName.getBasketList().get(i).getQuantity();
 		}
 		 model.addRow(new Object[]{"","","","",""});
 		 model.addRow(new Object[]{"","","","Total Amount",Double.toString(customFormat("###.##",y))});
    }
    	
    //Formating Double by digits after point.
    private double customFormat(String pattern, double value ) {
    	DecimalFormat myFormatter = new DecimalFormat(pattern);
    	String output = myFormatter.format(value);
    	return Double.parseDouble(output);
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
    			} else {  
    				shopBuy.moveToBasket(nameInput.getText(), quantityInput.getText(),basketName);
    			}
    			if (shopBuy.checkIsSmallInWarehouse) {
    				textBoxToEnterName.setText(nameInput.getText());
    			}else {
    				textBoxToEnterName.setText(""); 
    			}
		   textBoxToEnterQuantity.setText("");
		    updateBasket();
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
    		updateBasket();
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
    		SearchGUIwithTable searchGUIwithTable = new SearchGUIwithTable();
    		searchGUIwithTable.setVisible(true);
    		searchGUIwithTable.addWindowListener(new WindowAdapter() {
    			@Override
    			public void windowClosing(WindowEvent e) {
    				searchGUIwithTable.setVisible(false);
    				super.windowClosing(e);
    			}
    		});
    	}
    }
    	
    /*public static void main(String[] args) {
    	SoldProductGUI soldProductGUI = new SoldProductGUI();
    	soldProductGUI.setVisible(true);
        System.out.println(soldProductGUI.addButton.isSelected());
    }*/
}