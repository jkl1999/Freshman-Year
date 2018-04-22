import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

import Apps.EpisodeList;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Scrollbar;
import java.awt.Color;

public class GUI {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.BLACK);
		frame.getContentPane().setForeground(Color.BLACK);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textField = new JTextField();
		textField.setBackground(Color.GRAY);
		textField.setColumns(10);
		JLabel series = new JLabel("Enter Series Below");
		series.setForeground(Color.WHITE);
		JLabel lblRandomUrl = new JLabel("Random URL: ");
		lblRandomUrl.setForeground(Color.WHITE);
		JButton btnShuffle = new JButton("Shuffle");
		textField_1 = new JTextField();
		textField_1.setBackground(Color.LIGHT_GRAY);
		textField_1.setColumns(10);
		btnShuffle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> episodes = new ArrayList<String>();
				
				String title = textField.getText();
				
				try {
					episodes = EpisodeList.makeList(title);
				} catch (FailingHttpStatusCodeException | IOException e1) {
					// TODO Auto-generated catch block
					textField_1.setText("Click Reset, Check Spelling, or Try a Different Show, Hit Shuffle");
					
				}
				
				int randIndex = 0 + (int)(Math.random() * ((episodes.size()) + 1));
				
				if(!textField_1.getText().equals("Check Spelling, or Try a Different Show")) {
				textField_1.setText(episodes.get(randIndex).substring(0,55)); //change this to update clickHEre with url info
				}
				
			}
		});
		
		JLabel shuffle = new JLabel("Netflix");
		shuffle.setForeground(Color.RED);
		shuffle.setFont(new Font("Arial", Font.BOLD, 37));
		
		JButton Url = new JButton("Click Here to Start Episode");
		Url.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			URI rand = null;
			try {
				rand = new URI(textField_1.getText());
			} catch (URISyntaxException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
				
			try {
				Desktop.getDesktop().browse(rand);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
				
				
			}
		});
		
		JLabel lblNewLabel = new JLabel("Shuffle");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 37));
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(null);
				
			}
		});
		
		
		
		
		
		
		
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblRandomUrl)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(Url)
							.addGap(68))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addContainerGap(101, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnShuffle)
								.addComponent(shuffle))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnReset))
							.addGap(45))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(164)
							.addComponent(series))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(155)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(31)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(shuffle, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(series)
					.addPreferredGap(ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnShuffle)
						.addComponent(btnReset))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(Url)
						.addComponent(lblRandomUrl))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(7))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
