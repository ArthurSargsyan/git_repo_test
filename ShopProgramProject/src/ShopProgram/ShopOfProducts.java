package ShopProgram;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import Hibernate.ChooseProduct;


public class ShopOfProducts {
	
	JFrame frame = new JFrame();
	JTable table;
	DefaultTableModel model;
	JLabel Warehouse = new JLabel("Warehouse");
	
	private void shopGUI () {
		
		
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
				
		ChooseProduct prod = new ChooseProduct();
		ArrayList<Product> prodList = prod.chooseAllProductArrayList();
		model.setRowCount(0);
		for (int i=0; i<prodList.size(); i++) {
		model.addRow(new Object[]{i+1,prodList.get(i).getProductName(),Double.toString(prodList.get(i).getProductPrice()),Double.toString(prodList.get(i).getQuantity())});
		}
	    
		JButton addToWareHouse = new JButton("|                  Add To Warehouse                   |");
		JButton saleProducts = new JButton("|                       Sale Products                       |");
		JButton projects = new JButton("|                            Projects                             |");
		JPanel shopPanel = new JPanel();
		JPanel warehousePanel = new JPanel();
		shopPanel.setLayout(new BoxLayout(shopPanel, BoxLayout.Y_AXIS));
		warehousePanel.setLayout(new BoxLayout(warehousePanel, BoxLayout.Y_AXIS));
		
		addToWareHouse.addActionListener(new AddToWareHouseButton());
		saleProducts.addActionListener(new SaleProducts());
		projects.addActionListener(new Projects());
		
		JScrollPane qScroller = new JScrollPane(table);                   
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        warehousePanel.add(Warehouse);
        warehousePanel.add(qScroller);
       
		frame.add(shopPanel, BorderLayout.CENTER);
		frame.add(warehousePanel, BorderLayout.WEST);
		frame.add(Warehouse, BorderLayout.NORTH);
		shopPanel.add(addToWareHouse);
		shopPanel.add(saleProducts);
		shopPanel.add(projects);
		
		frame.setTitle("SHOP OF PRODUCTS");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
		
	public class AddToWareHouseButton implements ActionListener {
		boolean x = false;
		@Override
		public void actionPerformed(ActionEvent addToWareHouseClicked) {
			 
			System.out.println(" AddToWareHouseButton is clicked");
			AddToWareHouse addToWareHouse = new AddToWareHouse();
			frame.setVisible(false);
			addToWareHouse.setVisible(true);
			
								
			addToWareHouse.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					//System.out.println("Return to ShopOfProducts Window");
						addToWareHouse.setVisible(false);
						frame.setVisible(true);
						
						
						ChooseProduct prod = new ChooseProduct();
						ArrayList<Product> prodList = prod.chooseAllProductArrayList();
						model.setRowCount(0);
						for (int i=0; i<prodList.size(); i++) {
						model.addRow(new Object[]{i+1,prodList.get(i).getProductName(),Double.toString(prodList.get(i).getProductPrice()),Double.toString(prodList.get(i).getQuantity())});
						}
						super.windowClosing(e);
				}
			});
		}
	}
	
	public class SaleProducts implements ActionListener {
		
	@Override
	public void actionPerformed(ActionEvent addToWareHouseClicked) {
			System.out.println("SaleProducts is clicked");
			frame.setVisible(false);
			SoldProductGUI soldProductGUI = new SoldProductGUI();
			soldProductGUI.setVisible(true);
			
			soldProductGUI.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					soldProductGUI.setVisible(false);
					frame.setVisible(true);
					
					ChooseProduct prod = new ChooseProduct();
					ArrayList<Product> prodList = prod.chooseAllProductArrayList();
					model.setRowCount(0);
					for (int i=0; i<prodList.size(); i++) {
					model.addRow(new Object[]{i+1,prodList.get(i).getProductName(),Double.toString(prodList.get(i).getProductPrice()),Double.toString(prodList.get(i).getQuantity())});
					}
					super.windowClosing(e); //  --------------------------
				}
			});
		}
	}
	
	
	public class Projects implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent addToWareHouseClicked) {
				System.out.println("Projects is clicked");
				frame.setVisible(false);
				ProjectsGUI projectsGUI = new ProjectsGUI();
		   		projectsGUI.setVisible(true);
				
		   		
		   		
		   		projectsGUI.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						projectsGUI.setVisible(false);
						frame.setVisible(true);
						
						ChooseProduct prod = new ChooseProduct();
						ArrayList<Product> prodList = prod.chooseAllProductArrayList();
						model.setRowCount(0);
						for (int i=0; i<prodList.size(); i++) {
						model.addRow(new Object[]{i+1,prodList.get(i).getProductName(),Double.toString(prodList.get(i).getProductPrice()),Double.toString(prodList.get(i).getQuantity())});
						}
						super.windowClosing(e); //  --------------------------
					}
				});
			}
		}
	
	public static void main(String[] args) {
		ShopOfProducts shopOfProducts = new ShopOfProducts();
		shopOfProducts.shopGUI();
	}
}

