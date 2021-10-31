package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Model.Constants_model;
import view.Constants;

public class Options extends JFrame {

	private JPanel contentPane;

	private String[] m_automatonsNames;

	private LinkedList<JComboBox<String>> m_comboBoxes;

	public Options() {
		OptionsModel optionsModel = new OptionsModel();
		m_comboBoxes = new LinkedList<JComboBox<String>>();
		m_automatonsNames = optionsModel.getNames();

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, 1500, 950);

		contentPane = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				Image img = Toolkit.getDefaultToolkit().getImage("src/resources/Options.jpg");
				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
			}
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblEntities = createNewLabel("Entities", Color.black, Color.white, Constants.FIRSTCOL_X_MENU,
				Constants.COL_Y_MENU);

		contentPane.add(lblEntities);
		JLabel lblAu = createNewLabel("Associated Automaton", Color.black, Color.white, Constants.LASTCOL_X_MENU,
				Constants.COL_Y_MENU);
		contentPane.add(lblAu);

		Constants_model constants = new Constants_model();

		for (int i = 0; i < m_automatonsNames.length; i++) {
			JLabel lbl = createNewLabel(m_automatonsNames[i], Color.white, Color.black, Constants.FIRSTCOL_X_MENU,
					constants.HUNDRED_PX_OFFSET * (i + 2));
			contentPane.add(lbl);
			JComboBox<String> comboBox = createNewComboBox(Color.white, Constants.LASTCOL_X_MENU,
					constants.HUNDRED_PX_OFFSET * (i + 2), i);
			m_comboBoxes.add(comboBox);
			contentPane.add(comboBox);
		}

		JButton btnNewButton = new JButton("Save Changes");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				LinkedList<String> index = new LinkedList<>();
				for (JComboBox<String> comboBox : m_comboBoxes) {
					index.add((String) comboBox.getSelectedItem());
				}

				optionsModel.setAutomatons(index);
			}
		});
		btnNewButton.setBounds(500, 655, 198, 70);
		contentPane.add(btnNewButton);

		JButton btnReturnToMain = new JButton("Return To Main Page");
		btnReturnToMain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Menu frame = new Menu();

				dispose();
			}
		});

		btnReturnToMain.setBounds(800, 655, 198, 70);
		contentPane.add(btnReturnToMain);
	}

	private JLabel createNewLabel(String title, Color background, Color foreground, int x, int y) {
		JLabel lbl = new JLabel(title);
		lbl.setBounds(x, y, Constants.WIDTH_COMPONENT, Constants.HEIGHT_COMPONENT);

		lbl.setOpaque(true);
		lbl.setBackground(background);
		lbl.setForeground(foreground);
		return lbl;
	}

	private JComboBox<String> createNewComboBox(Color background, int x, int y, int index) {
		JComboBox<String> comboBox = new JComboBox<String>(m_automatonsNames);
		comboBox.setBounds(x, y, Constants.WIDTH_COMPONENT, Constants.HEIGHT_COMPONENT);
		comboBox.setBackground(background);
		comboBox.setSelectedIndex(index);

		return comboBox;
	}

}
