package ShopProgram;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


public class ProjectDataGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	String projectName;
	Buy shopBuy = new Buy();
	Basket  basketName = new Basket();
	JTable table;
	DefaultTableModel model;
	JButton report;
	JButton sendReportByEmail;
	
	
    public ProjectDataGUI(){
    		    		
    	model = new DefaultTableModel();
    	model.addColumn("ID");
    	model.addColumn("Name");
    	model.addColumn("Quantity");
    	model.addColumn("Receipted Date");
    	model.addColumn("Recipient");
    	model.addColumn("Product Price");
    	model.addColumn("Total Price");
    	
    		
    	table = new JTable(model);
    	table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    	
    	table.setSize(50,150);
    	
    	table.setIntercellSpacing(new Dimension(5, 0));
    	table.setCellSelectionEnabled(true);
    	table.setEnabled(false);
    	    	
        JLabel basket = new JLabel("Products List");
        report = new JButton("Report");
        sendReportByEmail = new JButton("Send Report By Email");
               
        JScrollPane qScroller = new JScrollPane(table);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        report.addActionListener(new Report());
        report.setAlignmentX(CENTER_ALIGNMENT);
        
        sendReportByEmail.addActionListener(new SendReportByEmail());
        sendReportByEmail.setAlignmentX(CENTER_ALIGNMENT);
        
        JPanel panelcenter = new JPanel();
        panelcenter.setLayout(new BoxLayout(panelcenter,BoxLayout.Y_AXIS));
       
        JPanel panelBottom = new JPanel();
                
        panelcenter.add(basket);
        panelcenter.add(qScroller);
        panelcenter.add(report);
        panelcenter.add(sendReportByEmail);
               
        add(panelBottom, BorderLayout.SOUTH);
        add(panelcenter, BorderLayout.CENTER);
       
        setTitle("Products In Project");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        //setResizable(false);
        pack();
        setLocationRelativeTo(null);
       
    }
    
    public class Report implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			ExcelFileUpdate update = new ExcelFileUpdate();
			update.exelUpdate(table);
			update.openFile();
		}
	}

    public class SendReportByEmail implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			SendAttachmentInEmail sendEmail = new SendAttachmentInEmail();
			sendEmail.mailing("sargsyanartur5989@gmail.com", "Report", "Report For Your Company", "D:\\Reports\\report2.xlsx");
			
			
		}
	}
    	
    	/*public static void main(String[] args) {
   		ProjectDataGUI projectsGUI = new ProjectDataGUI();
   		projectsGUI.setVisible(true);
        
    	}*/
}
