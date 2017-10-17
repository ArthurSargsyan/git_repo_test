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
import Hibernate.ChooseProduct;

public class SearchGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	Buy shopBuy = new Buy();
	Basket  basketName = new Basket();
	
	JButton searchButton;
	JButton addButton;
	JTextField textBoxToEnterSearchName;
	JTextArea basketTestArea;

    	public SearchGUI(){

    	JLabel productName = new JLabel("Search");
        JLabel basket = new JLabel("Searched List");
        
        textBoxToEnterSearchName = new JTextField(20);
        
        basketTestArea = new JTextArea(10,35);
        basketTestArea.setEditable(false);
        basketTestArea.setText("No Result");
                
        basketTestArea.setLineWrap(false);
        basketTestArea.setWrapStyleWord(true);
        basketTestArea.setFont(new Font("Tahoma", Font.ITALIC, 12));
        
        JScrollPane qScroller = new JScrollPane(basketTestArea);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        JPanel panelcenter = new JPanel();
        panelcenter.setLayout(new BoxLayout(panelcenter,BoxLayout.Y_AXIS));
       
        searchButton = new JButton("Search");
        addButton = new JButton("Add");
        
        searchButton.addActionListener(new SearchButton(textBoxToEnterSearchName));
        addButton.addActionListener(new AddButton(textBoxToEnterSearchName));
       
        JPanel panelBottom = new JPanel();
                
        panelcenter.add(productName);
        panelcenter.add(textBoxToEnterSearchName);
        panelcenter.add(basket);
        panelcenter.add(qScroller);
        panelBottom.add(searchButton);
        panelBottom.add(addButton);
       
        add(panelBottom, BorderLayout.SOUTH);
        add(panelcenter, BorderLayout.CENTER);
       
        setTitle("Search Products");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
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
    			System.out.println(basketTestArea.getSelectedText());
    			if (searchInput.getText().equals("")) {
    				JOptionPane.showMessageDialog(null, "Search Field Is Empty");
    				//System.out.println("Please Insert All Fields of Window");
    			} else { 
    	   String searchedListByString ="Name\t\tPRICE\tQUANTITY\n";
		   ChooseProduct choosenProduct = new ChooseProduct();
		   searchedListByString = searchedListByString + choosenProduct.chooseProductListFilteringByString(searchInput.getText());
		   basketTestArea.setText(searchedListByString);
    			}
    		}
    	}
        	
    	public class AddButton implements ActionListener {
        	
    		JTextField searchInput;
    		public AddButton(JTextField textFieldForSearch){
    			searchInput = textFieldForSearch;	
    		}

    		@Override
    		public void actionPerformed(ActionEvent searchClicked) {
    			setVisible(true); 
    			SoldProductGUI.textBoxToEnterName.setText(basketTestArea.getSelectedText());
    			System.out.println(basketTestArea.getSelectedText());
    		}
    			
    	}
    	
    	/*public static void main(String[] args) {
   		SearchGUI searchGUI = new SearchGUI();
   		searchGUI.setVisible(true);
        System.out.println(searchGUI.searchButton.isSelected());
    	}*/
}
