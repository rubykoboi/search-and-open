package jar;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class App {

	private JFrame frame;
	private static JTextField search;
	private static JButton	btnSearch;
	private static JPanel boxPanel;
	final static String SEARCH_PATH = "I:\\2022";
//	final static String SEARCH_PATH = "C:";
	final static String FILE_EXTENSION = ".pdf";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
					window.frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		try {
    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	} catch (Throwable ex) {
    		ex.printStackTrace();
    	}
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Search and Open");
		search = new JTextField();
		btnSearch = new JButton("Search");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 280, 80);
		boxPanel = new JPanel();
		boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.X_AXIS));
		boxPanel.add(search);
		boxPanel.add(btnSearch);
		boxPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		frame.add(boxPanel);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchString = search.getText().trim().toUpperCase();
				try (Stream<Path> run = Files.walk(Paths.get(SEARCH_PATH))) {
					List<String> fileList = run.filter(p -> !Files.isDirectory(p)).map(p -> p.toString()).filter(f -> f.endsWith(FILE_EXTENSION)).filter(f -> f.toUpperCase().contains(searchString)).filter(f -> f.length() > searchString.length())
							.collect(Collectors.toList());
					if(fileList.size() == 0) {
						JOptionPane.showMessageDialog(null, "No File Found", "No Results", JOptionPane.ERROR_MESSAGE);
					} else {
						String resultFilePath = fileList.get(0);
						Desktop.getDesktop().open(new File(resultFilePath));
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

}
