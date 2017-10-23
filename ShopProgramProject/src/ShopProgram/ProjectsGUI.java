package ShopProgram;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import Hibernate.ReadDataFromProjects;

public class ProjectsGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	boolean showing = true;
	
	Buy shopBuy = new Buy();
	Basket  basketName = new Basket();
	JTable table;
	DefaultTableModel model;
		
    public ProjectsGUI(){
    		    		
    	model = new DefaultTableModel();
    	model.addColumn("ID");
    	model.addColumn("Project Name");
    	model.addColumn("Receipted Date");
    	model.addColumn("Recipient");
    		
    		
    	table = new JTable(model);
    	table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    		
    	table.setSize(50,150);
    		
    	table.setIntercellSpacing(new Dimension(5, 0));
    	table.setCellSelectionEnabled(true);
    	  
    	    
    	JLabel basket = new JLabel("Projets List");
            
    	       
        JScrollPane qScroller = new JScrollPane(table);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            
        JPanel panelcenter = new JPanel();
        panelcenter.setLayout(new BoxLayout(panelcenter,BoxLayout.Y_AXIS));
        
          
        JPanel panelBottom = new JPanel();
                  
        panelcenter.add(basket);
        panelcenter.add(qScroller);
                   
        add(panelBottom, BorderLayout.SOUTH);
        add(panelcenter, BorderLayout.CENTER);
           
        setTitle("Projetcs");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        //setResizable(false);
        pack();
        setLocationRelativeTo(null);
    	   
 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		ReadDataFromProjects readProjectsData = new ReadDataFromProjects();
		ArrayList<Project> readePprojectList= readProjectsData.chooseAllProjectsArrayList();
			
    	model.setRowCount(0);
    	for (int i=0; i<readePprojectList.size(); i++) {
    	model.addRow(new Object[]{i+1,readePprojectList.get(i).getProjectName(),readePprojectList.get(i).getDate(),readePprojectList.get(i).getPersonName()});
    	}
    	    
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    	
    	table.addMouseListener(new java.awt.event.MouseAdapter() {
    	    @Override
    	    public void mouseClicked(java.awt.event.MouseEvent evt) {
    	    	if (showing) {
    	    		showing = false;
    	       int row = table.rowAtPoint(evt.getPoint());
    	       String selectedProjectName="";
    	        selectedProjectName = (String) table.getValueAt(row, 1);
    	     
    	        ProjectDataGUI projectDataGUI = new  ProjectDataGUI();
    	        projectDataGUI.setVisible(true);
    	                	    	        
   	    	 ReadDataFromProjects readProductsList = new ReadDataFromProjects();
   	    	 ArrayList<ProjectData>  choosenList= readProductsList.projectDataFilteringByProjectName(selectedProjectName);
   	    	    			     
   	    	projectDataGUI.model.setRowCount(0);
   	    	double totalPrice=0;
   	    			for (int i=0; i<choosenList.size(); i++) {
   	    				projectDataGUI.model.addRow(new Object[]{i+1,choosenList.get(i).getProductName(),choosenList.get(i).getQuantity(),choosenList.get(i).getDate(),choosenList.get(i).getPersonName(),choosenList.get(i).getProductPrice(),Double.toString(choosenList.get(i).getQuantity()*choosenList.get(i).getProductPrice())});
   	    				totalPrice = totalPrice + choosenList.get(i).getQuantity()*choosenList.get(i).getProductPrice();
   	    	 }
   	    	projectDataGUI.model.addRow(new Object[]{"","","","","","",""});
	    	projectDataGUI.model.addRow(new Object[]{"","","","","","Total Price",Double.toString(totalPrice)});
    	    
   	        		
   	    	projectDataGUI.addWindowListener(new WindowAdapter() {
   	    		 @Override
   	    		 public void windowClosing(WindowEvent e) {
   	    			showing = true;
   	    			projectDataGUI.setVisible(false);
   	    			 setVisible(true);
   	    			 super.windowClosing(e); //  --------------------------
   	    		 }
   	    	 });
    	    
    	    }
    	    }
    	});
 
 }
	
	///////////////////////////////////////////////////////////////////////////   	
    	/*public static void main(String[] args) {
   		ProjectsGUI projectsGUI = new ProjectsGUI();
   		projectsGUI.setVisible(true);
        //System.out.println(projectsGUI.viewButton.isSelected());
    	}*/
}
