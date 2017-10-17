package ShopProgram;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import Hibernate.ChooseProduct;

public class ShopOfProducts {
	
	
	JTextArea wareHouseTable = new JTextArea(10,40);
	JFrame frame = new JFrame();
	
	private void shopGUI () {
		
		
		JButton addToWareHouse = new JButton("|                  Add To Warehouse                  |");
		JButton saleProducts = new JButton("|                       Sale Products                       |");
		JPanel shopPanel = new JPanel();
		shopPanel.setLayout(new BoxLayout(shopPanel, BoxLayout.Y_AXIS));
		
		addToWareHouse.addActionListener(new AddToWareHouseButton());
		saleProducts.addActionListener(new SaleProducts());
		       
		wareHouseTable.setLineWrap(false);
		wareHouseTable.setWrapStyleWord(true);
		wareHouseTable.setFont(new Font("Tahoma", Font.ITALIC, 12));
		JScrollPane qScroller = new JScrollPane(wareHouseTable);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        wareHouseTable.setEditable(false);
		wareHouseTable.setText("                WAREHOUSE\nNAME\t\tPRICE\tQUANTITY\n"+updateWarehouseInTable());
		
		frame.add(shopPanel, BorderLayout.CENTER);
		frame.add(qScroller, BorderLayout.WEST);
		shopPanel.add(addToWareHouse);
		shopPanel.add(saleProducts);
		frame.setTitle("SHOP OF PRODUCTS");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public String updateWarehouseInTable() {
		ChooseProduct warehouseProduct = new ChooseProduct();
		return warehouseProduct.chooseAllProductListForTextArea();
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
						wareHouseTable.setText("                WAREHOUSE\nNAME\t\tQUANTITY\tPRICE\n"+updateWarehouseInTable());
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
					wareHouseTable.setText("                WAREHOUSE\nNAME\t\tPRICE\tQUANTITY\n"+updateWarehouseInTable());
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

