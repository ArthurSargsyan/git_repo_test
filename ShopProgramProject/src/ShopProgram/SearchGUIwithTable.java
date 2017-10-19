package ShopProgram;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import Hibernate.ChooseProduct;

public class SearchGUIwithTable extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	Buy shopBuy = new Buy();
	Basket  basketName = new Basket();
	JButton searchButton;
	JTextField textBoxToEnterSearchName;
	JTable table;
	DefaultTableModel model;
	
    	public SearchGUIwithTable(){
    		    		
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
    	    ListSelectionModel cellSelectionModel = table.getSelectionModel();
    	    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    	    cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
    	    public void valueChanged(ListSelectionEvent e) {
    	        String selectedData="";

    	        int[] selectedRow = table.getSelectedRows();
    	        

    	        for (int i = 0; i < selectedRow.length; i++) {
    	         
    	            selectedData = (String) table.getValueAt(selectedRow[i], 1/*selectedColumns[1]*/);
    	        }
    	        	        
    	        SoldProductGUI.textBoxToEnterName.setText(selectedData);
    	        SoldProductGUI.textBoxToEnterQuantity.setText("");
    	        System.out.println("Selected: " + selectedData);
    	      }

    	    });
    	    
    	   
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
    				
    				ChooseProduct prod = new ChooseProduct();
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
