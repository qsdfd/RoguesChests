import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;



import java.awt.FlowLayout;
import java.awt.Button;
import java.awt.Font;
import java.awt.Color;
import java.awt.TextField;
import java.awt.Canvas;
import java.awt.Panel;
import java.awt.Choice;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFormattedTextField;
import javax.swing.JTextPane;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Dimension;
import java.lang.*;
import java.util.Random;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JComboBox;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.JToggleButton;
import javax.swing.ButtonGroup;
import javax.swing.JLayeredPane;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.List;
import java.awt.Scrollbar;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;


public class GUIR extends JFrame{
	private JRadioButton chekHRunes;
	private JRadioButton chekHTabs;
	private JCheckBox chekHouseTele;
	private JRadioButton chekGRunes;
	private JRadioButton chekGTabs;
	private JCheckBox chekGTele;
	
	private int nPpots;
	boolean isDisposed = false;
	private final ButtonGroup runesTabsH = new ButtonGroup();
	private boolean useHTabs = false;
	private boolean useHRunes = false;
	private final ButtonGroup runesTabsG = new ButtonGroup();
	private boolean useGTabs = false;
	private boolean useGRunes = false;
	
	public static void main(String[] args) {
			GUIR window = new GUIR();
			window.setVisible(true);
	}
	
	public GUIR() {

		setBounds(100, 100, 389, 340);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		getContentPane().setLayout(null);

		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("GUI Rogues'ChestThiever by dokato");
		
		JLabel scriptTitle = new JLabel("Rogues' Chest Thiever");
		scriptTitle.setFont(new Font("Calibri", Font.BOLD, 24));
		scriptTitle.setBounds(39, -1, 229, 64);
		getContentPane().add(scriptTitle);
		
		JLabel author = new JLabel("by dokato");
		author.setFont(new Font("Tahoma", Font.BOLD, 13));
		author.setBounds(278, 21, 74, 21);
		getContentPane().add(author);
		
		final JSpinner nPots = new JSpinner();
		nPots.setModel(new SpinnerNumberModel(3, 0, 20, 1));
		nPots.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nPots.setBounds(154, 256, 49, 32);
		getContentPane().add(nPots);
		nPots.setToolTipText("The amount of pots u want to use per trip");
		
		JLabel lblNewJgoodiesLabel = new JLabel("Prayer pots per trip: ");
		lblNewJgoodiesLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblNewJgoodiesLabel.setBounds(22, 250, 258, 44);
		getContentPane().add(lblNewJgoodiesLabel);
		lblNewJgoodiesLabel.setToolTipText("The amount of pots u want to use per trip");
		
		JLabel startAt = new JLabel("Start at chests/bank");
		startAt.setFont(new Font("Tahoma", Font.BOLD, 12));
		startAt.setBounds(89, 41, 144, 36);
		getContentPane().add(startAt);
		
		
		
		chekHRunes = new JRadioButton("Runes (40 mage) [1 law, 1 air, 1 earth]\r\n");
		chekHRunes.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				useHRunes=true;
				useHTabs=false;
				useGRunes=false;
				if(chekGRunes.isSelected()){
					chekGTabs.setSelected(true);
					chekHRunes.setSelected(true);
				}
			}
		});
		chekHRunes.setEnabled(false);
		chekHRunes.setSelected(false);
		runesTabsH.add(chekHRunes);
		chekHRunes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chekHRunes.setBounds(65, 109, 252, 23);
		getContentPane().add(chekHRunes);
		
		chekHTabs = new JRadioButton("Teleport Tabs\r\n");
		chekHTabs.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				useHTabs=true;
				useHRunes=false;
			}
		});
		chekHTabs.setEnabled(false);
		chekHTabs.setSelected(false);
		runesTabsH.add(chekHTabs);
		chekHTabs.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chekHTabs.setBounds(65, 135, 114, 23);
		getContentPane().add(chekHTabs);
		
		chekGRunes = new JRadioButton("Runes (96 mage) [2 law, 8 water]\r\n");
		chekGRunes.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				useGRunes=true;
				useGTabs=false;
				useHRunes=false;
				if(chekHRunes.isSelected()){
					chekHTabs.setSelected(true);
					chekGRunes.setSelected(true);
				}
			}
		});
		chekGRunes.setEnabled(false);
		runesTabsG.add(chekGRunes);
		chekGRunes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chekGRunes.setBounds(65, 194, 217, 23);
		getContentPane().add(chekGRunes);
		
		chekGTabs = new JRadioButton("Teleport Tabs\r\n");
		chekGTabs.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				useGTabs=true;
				useGRunes=false;
			}
		});
		chekGTabs.setEnabled(false);
		runesTabsG.add(chekGTabs);
		chekGTabs.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chekGTabs.setBounds(65, 220, 114, 23);
		getContentPane().add(chekGTabs);
		
		chekHouseTele = new JCheckBox("Use Teleport to House (mounted glory + altar required)");
		chekHouseTele.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(chekHouseTele.isSelected()){
					chekHRunes.setEnabled(true);
					chekHTabs.setEnabled(true);
				}else if(!chekHouseTele.isSelected()){
					chekHRunes.setSelected(false);
					chekHRunes.setEnabled(false);
					chekHTabs.setSelected(false);
					chekHTabs.setEnabled(false);
					useHTabs=false;
					useHRunes=false;
				}
			}
		}); 
		chekHouseTele.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chekHouseTele.setBounds(22, 70, 343, 36);
		getContentPane().add(chekHouseTele);
		
		chekGTele = new JCheckBox("Use Ghorrock Teleport");
		chekGTele.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(chekGTele.isSelected()){
					chekGRunes.setEnabled(true);
					chekGTabs.setEnabled(true);
				}else if(!chekGTele.isSelected()){
					chekGRunes.setSelected(false);
					chekGRunes.setEnabled(false);
					chekGTabs.setSelected(false);
					chekGTabs.setEnabled(false);
					useGTabs=false;
					useGRunes=false;
				}
			}
		});
		chekGTele.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chekGTele.setBounds(22, 168, 160, 23);
		getContentPane().add(chekGTele);
		
		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nPpots=(int) nPots.getValue();
				dispose();
				isDisposed = true;
			}
		});
		startButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		startButton.setBounds(278, 262, 89, 37);
		getContentPane().add(startButton);
		
	}
	
	public boolean getUseHRunes(){
		return useHRunes;
	}
	
	public boolean getUseHTabs(){
		return useHTabs;
	}
	
	public boolean getUseGRunes(){
		return useGRunes;
	}
	
	public boolean getUseGTabs(){
		return useGTabs;
	}
	
	public boolean disposed(){
		return isDisposed;
	}
	
	public int getNpots(){
		return nPpots;
	}
}
