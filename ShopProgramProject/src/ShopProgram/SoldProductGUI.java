package ShopProgram;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.Date;

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

import Hibernate.ChooseProduct;
import Hibernate.RemoveFromWareHouse;
import Hibernate.UpdateProject;


public class SoldProductGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	boolean showing = true;
	Buy shopBuy = new Buy();
	Basket  basketName = new Basket();
	
	JButton addButton;
	JButton removeButton;
	JButton commit;
	JButton search;
	static JTextField textBoxToEnterName;
	JTextField textBoxToEnterProjectName;
	JTextField textBoxToEnterPersonName;
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
    	
    	JLabel productName = new JLabel("  Product Name:  ");
        JLabel productQuatity = new JLabel("  Product Quantity:  ");
        JLabel projectName= new JLabel("  Project Name:  ");
        JLabel personName= new JLabel("  Person Name:  ");
        JLabel basket = new JLabel("List Of Products Using In The Specified Project ");
        
        textBoxToEnterName = new JTextField(20);
        textBoxToEnterQuantity = new JTextField(20);
        textBoxToEnterProjectName = new JTextField(20);
        textBoxToEnterProjectName.setEditable(true);
        textBoxToEnterPersonName = new JTextField(20);
        
        
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
        panelcenter.add(projectName);
        panelcenter.add(textBoxToEnterProjectName);
        panelcenter.add(personName);
        panelcenter.add(textBoxToEnterPersonName);
        panelEast.add(basket);
        panelEast.add(qScroller);
        
        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        commit = new JButton("Confirm");
        search = new JButton("Search");
        
        addButton.addActionListener(new addButton(textBoxToEnterName,textBoxToEnterQuantity));
        removeButton.addActionListener(new RemoveButton(textBoxToEnterName,textBoxToEnterQuantity));
        commit.addActionListener(new ConfirmButton());
        search.addActionListener(new SearchButton());
            
        JPanel panelBottom = new JPanel();
        
        panelBottom.add(addButton);
        panelBottom.add(removeButton);
        panelBottom.add(commit);
        panelBottom.add(search);
        
        add(panelEast, BorderLayout.EAST);
        add(panelBottom, BorderLayout.SOUTH);
        add(panelcenter, BorderLayout.CENTER);
       
        setTitle("Sale Products");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        updateBasket();
        
        basketTable.addMouseListener(new java.awt.event.MouseAdapter() {
    	    @Override
    	    public void mouseClicked(java.awt.event.MouseEvent evt) {
    	        int row = basketTable.rowAtPoint(evt.getPoint());
    	       String selectedData="";
    	       selectedData = (String) basketTable.getValueAt(row, 1);
    	      
    	       SoldProductGUI.textBoxToEnterName.setText(selectedData);
    	       SoldProductGUI.textBoxToEnterQuantity.setText("");
    	  }
    	});
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
    				JOptionPane.showMessageDialog(null, "Please Fill in Product Name And Product Quantity Fields");
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
    			JOptionPane.showMessageDialog(null, "Please Fill in Product Name And Product Quantity Fields");
    		} else {  
    			shopBuy.excludeFromBasket(nameInput.getText(), Double.parseDouble(quantityInput.getText()), basketName);
    		}
    		textBoxToEnterName.setText("");
    		textBoxToEnterQuantity.setText("");
    		updateBasket();
    	}
    }
    	    	
    public class ConfirmButton implements ActionListener {  
    	@Override
    	public void actionPerformed(ActionEvent CalculateClicked) {
    		setVisible(true);
    		if (textBoxToEnterProjectName.getText().equals("")||textBoxToEnterPersonName.getText().equals("")){
    			JOptionPane.showMessageDialog(null, "Please fill in Project Name and Person Name Fileds");	   
    		}else {
    			Project project = new Project();
    			project.setProjectName(textBoxToEnterProjectName.getText());
    			project.setPersonName(textBoxToEnterPersonName.getText());
    			project.setDate(new Date());
    			
    			for (int i=0;i<basketName.getBasketList().size(); i++) {
    				ChoosenProduct choosenProd = new ChoosenProduct();
    				choosenProd.setProductName(basketName.getBasketList().get(i).getProductName());
    				choosenProd.setProductPrice((basketName.getBasketList().get(i).getProductPrice()));
    				choosenProd.setQuantity((basketName.getBasketList().get(i).getQuantity()));
    				
    				project.getProductList().add(choosenProd);
    			}
    			UpdateProject updateProj = new UpdateProject();
				updateProj.addProject(project);
				textBoxToEnterProjectName.setText("");
				textBoxToEnterPersonName.setText("");
				model.setRowCount(0);
								
				for (int k=0;k<basketName.basketList.size();k++) {
					ChooseProduct chooseP = new ChooseProduct();
					int choosenid=chooseP.chooseProductId(basketName.basketList.get(k).getProductName());
								
					RemoveFromWareHouse  remove = new RemoveFromWareHouse();
					remove.removeFromWareHouse(choosenid, Double.toHexString(basketName.basketList.get(k).getQuantity()));
				}
				basketName.basketList.clear();
				updateBasket();
			}
    	}
   }
         
    public class SearchButton implements ActionListener {
    	
    	@Override
    	public void actionPerformed(ActionEvent searchClicked) {
    		System.out.println("SearchProducts is clicked");
    		if (showing) {
    			showing = false;
    			System.out.println(showing);
    			SearchGUIwithTable searchGUIwithTable = new SearchGUIwithTable();
        		searchGUIwithTable.setVisible(true);	
        		
        		searchGUIwithTable.addWindowListener(new WindowAdapter() {
        			@Override
        			public void windowClosing(WindowEvent e) {
        				searchGUIwithTable.setVisible(false);
        				showing = true;
        				super.windowClosing(e);
        			}
        		});
    		} 
    	}
    }
    	
    /*public static void main(String[] args) {
    	SoldProductGUI soldProductGUI = new SoldProductGUI();
    	soldProductGUI.setVisible(true);
        System.out.println(soldProductGUI.addButton.isSelected());
    }*/
}