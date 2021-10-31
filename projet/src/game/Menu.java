package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Automaton.Aef;
import Model.automatons.AutomatonsModel;

public class Menu extends JFrame {

	private JPanel contentPane;

	public Menu() {
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, 1500, 950);
		contentPane = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				Image img = Toolkit.getDefaultToolkit().getImage("src/resources/Menu.jpg");
				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
			}
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					HashMap<String, Aef> automatons = AutomatonsModel.m_automatons;

					Game game = new Game();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnPlay.setBounds(218, 418, 191, 113);
		contentPane.add(btnPlay);

		JButton btnNewButton = new JButton("Options");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Options frame = new Options();
				dispose();
			}
		});
		btnNewButton.setBounds(218, 637, 191, 113);
		contentPane.add(btnNewButton);
	}

}
