package ShopProgram;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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



public class SearchGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	Buy shopBuy = new Buy();
	Basket  basketName = new Basket();
	JButton searchButton;
	JTextField textBoxToEnterSearchName;
	JTable table;
	DefaultTableModel model;
	MouseListener m1;
	
    public MouseListener getM1() {
		return m1;
	}

	public void setM1(MouseListener m1) {
		this.m1 = m1;
	}

	public SearchGUI(){
    		    		
    		model = new DefaultTableModel();
    		model.addColumn("ID");
    		model.addColumn("Name");
    		model.addColumn("Price");
    		model.addColumn("Quantity");
    		    		
    		table = new JTable(model);
    		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    		
    		table.setSize(50,150);
    		
    		table.setIntercellSpacing(new Dimension(5, 0));
    		table.setCellSelectionEnabled(true);
    		
    		JLabel productName = new JLabel("Search");
            JLabel basket = new JLabel("Searched List");
            
            textBoxToEnterSearchName = new JTextField(20);
                        
            JScrollPane qScroller = new JScrollPane(table);
            qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            
            JPanel panelcenter = new JPanel();
            panelcenter.setLayout(new BoxLayout(panelcenter,BoxLayout.Y_AXIS));
           
            searchButton = new JButton("Search");
                        
            searchButton.addActionListener(new SearchButton(textBoxToEnterSearchName));
                       
            JPanel panelBottom = new JPanel();
                    
            panelcenter.add(productName);
            panelcenter.add(textBoxToEnterSearchName);
            panelcenter.add(basket);
            panelcenter.add(qScroller);
            panelBottom.add(searchButton);
                       
            add(panelBottom, BorderLayout.SOUTH);
            add(panelcenter, BorderLayout.CENTER);
           
            setTitle("Search Products");
            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            //setResizable(false);
            pack();
            setLocationRelativeTo(null);
    		    	
             m1 = new java.awt.event.MouseAdapter() {
        	    @Override
        	    public void mouseClicked(java.awt.event.MouseEvent evt) {
        	        int row = table.rowAtPoint(evt.getPoint());
        	       String selectedData="";
        	       selectedData = (String) table.getValueAt(row, 1);
        	       
        	        SoldProductGUI.textBoxToEnterName.setText(selectedData);
        	        SoldProductGUI.textBoxToEnterQuantity.setText("");
        	        System.out.println("Selected: " + selectedData);
        	    		
       	    	 addWindowListener(new WindowAdapter() {
       	    		 @Override
       	    		 public void windowClosing(WindowEvent e) {
       	    			 setVisible(false);
       	    			 super.windowClosing(e); //  --------------------------
       	    		 }
       	    	 });
        	    
        	    }
        	};
        	table.addMouseListener(m1);
	}

    public class SearchButton implements ActionListener {
    	
    		JTextField searchInput;
    			   
    		public SearchButton(JTextField textFieldForName){
    			searchInput = textFieldForName;	
    		}

    		@Override
    		public void actionPerformed(ActionEvent searchClicked) {
    			setVisible(true); 
    			model.setRowCount(0);
    			if (searchInput.getText().equals("")) {
    				JOptionPane.showMessageDialog(null, "Search Field Is Empty");
    			} else {
    				
    				WareHouse prod = new WareHouse();
    	    		ArrayList<Product> prodList = prod.ProductListFilteringByString(searchInput.getText());
    	    		model.setRowCount(0);
    	    		for (int i=0; i<prodList.size(); i++) {
    	    		model.addRow(new Object[]{i+1,prodList.get(i).getProductName(),Double.toString(prodList.get(i).getProductPrice()),Double.toString(prodList.get(i).getQuantity())});
    	    	
    	    		}	
    			}
    		}
    	}
       
    	
    	/*public static void main(String[] args) {
   		SearchGUIwithTable searchGUIwithTable = new SearchGUIwithTable();
   		searchGUIwithTable.setVisible(true);
        System.out.println(searchGUIwithTable.searchButton.isSelected());
    	}*/
    }
