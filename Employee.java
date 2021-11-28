package Employee;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.concurrent.ExecutionException;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;

public class Employee {

	private JFrame frame;
	private JTextField textEmpID;
	private JTable table;
	private JTextField textNlNumber;
	private JTextField textVorname;
	private JTextField textNachname;
	private JTextField textGeschlecht;
	private JTextField textGeburtsdatum;
	private JTextField textAlter;
	private JTextField textLohn;

	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	DefaultTableModel model = new DefaultTableModel();

	/**
	 * Launch the application.
	 */
	public void updateTable() {
		conn = EmployeeData.ConnectDB();

		if (conn != null) {
			String sql = "Select EmpID, NlNumber, Vorname, Nachname, Geschlecht, Geburstdatum, Alter, Lohn";

			try {
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[8];

				while (rs.next()) {
					columnData[0] = rs.getString("EmpID");
					columnData[1] = rs.getString("NlNumber");
					columnData[2] = rs.getString("Vorname");
					columnData[3] = rs.getString("Nachname");
					columnData[4] = rs.getString("Geschlecht");
					columnData[5] = rs.getString("Geburtsdatum");
					columnData[6] = rs.getString("Alter");
					columnData[7] = rs.getString("Lohn");
					model.addRow(columnData);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Employee window = new Employee();
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
	public Employee() {
		initialize();

		conn = EmployeeData.ConnectDB();
		Object col[] = { "EmpID", "NlNumber", "Vorname", "Nachname", "Geschlecht", "Geburstdatum", "Alter", "Lohn" };
		model.setColumnIdentifiers(col);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1062, 698);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblEmpID = new JLabel("EmpID");
		lblEmpID.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblEmpID.setBounds(23, 74, 123, 23);
		frame.getContentPane().add(lblEmpID);

		textEmpID = new JTextField();
		textEmpID.setFont(new Font("Tahoma", Font.BOLD, 18));
		textEmpID.setBounds(169, 72, 166, 33);
		frame.getContentPane().add(textEmpID);
		textEmpID.setColumns(10);

		JButton btnAdd = new JButton("Hinzuf\u00FCgen");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String sql = "INSERT INTO employee VALUES(?,?,?,?,?,?,?,?)";

				try {
					pst = conn.prepareStatement(sql);
					pst.setString(1, textEmpID.getText());
					pst.setString(2, textNlNumber.getText());
					pst.setString(3, textVorname.getText());
					pst.setString(4, textNachname.getText());
					pst.setString(5, textGeschlecht.getText());
					pst.setString(6, textGeburtsdatum.getText());
					pst.setString(7, textAlter.getText());
					pst.setString(8, textLohn.getText());
					pst.execute();
					rs.close();
					pst.close();
				} catch (Exception ev) {
					JOptionPane.showMessageDialog(null, "Systemupdate beendet");
				}
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[] { textEmpID.getText(), textNlNumber.getText(), textVorname.getText(),
						textNachname.getText(), textGeschlecht.getText(), textGeburtsdatum.getText(),
						textAlter.getText(), textLohn.getText(), });
				if (table.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "Membership Update bestätigt", "Employee Database System",
							JOptionPane.OK_OPTION);
				}
			}
		});
		
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnAdd.setBounds(23, 577, 139, 44);
		frame.getContentPane().add(btnAdd);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(383, 74, 635, 322);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "EmpID", "NlNumber", "Vorname",
				"Nachname", "Geschlecht", "Geburtsdatum", "Geschlecht", "Lohn" }));
		scrollPane.setViewportView(table);

		JLabel lblNlNumber = new JLabel("NI Number");
		lblNlNumber.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNlNumber.setBounds(23, 134, 123, 23);
		frame.getContentPane().add(lblNlNumber);

		textNlNumber = new JTextField();
		textNlNumber.setFont(new Font("Tahoma", Font.BOLD, 18));
		textNlNumber.setColumns(10);
		textNlNumber.setBounds(169, 132, 166, 33);
		frame.getContentPane().add(textNlNumber);

		JLabel lblVorname = new JLabel("Vorname");
		lblVorname.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblVorname.setBounds(23, 194, 123, 23);
		frame.getContentPane().add(lblVorname);

		textVorname = new JTextField();
		textVorname.setFont(new Font("Tahoma", Font.BOLD, 18));
		textVorname.setColumns(10);
		textVorname.setBounds(169, 192, 166, 33);
		frame.getContentPane().add(textVorname);

		JLabel lblNewLabel_3 = new JLabel("Nachname");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_3.setBounds(23, 254, 123, 23);
		frame.getContentPane().add(lblNewLabel_3);

		textNachname = new JTextField();
		textNachname.setFont(new Font("Tahoma", Font.BOLD, 18));
		textNachname.setColumns(10);
		textNachname.setBounds(169, 252, 166, 33);
		frame.getContentPane().add(textNachname);

		JLabel lblGeschlecht = new JLabel("Geschlecht");
		lblGeschlecht.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblGeschlecht.setBounds(23, 314, 123, 23);
		frame.getContentPane().add(lblGeschlecht);

		textGeschlecht = new JTextField();
		textGeschlecht.setFont(new Font("Tahoma", Font.BOLD, 18));
		textGeschlecht.setColumns(10);
		textGeschlecht.setBounds(169, 312, 166, 33);
		frame.getContentPane().add(textGeschlecht);

		JLabel lblGeburtsdatum = new JLabel("Geburtsdatum");
		lblGeburtsdatum.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblGeburtsdatum.setBounds(23, 374, 139, 23);
		frame.getContentPane().add(lblGeburtsdatum);

		textGeburtsdatum = new JTextField();
		textGeburtsdatum.setFont(new Font("Tahoma", Font.BOLD, 18));
		textGeburtsdatum.setColumns(10);
		textGeburtsdatum.setBounds(169, 372, 166, 33);
		frame.getContentPane().add(textGeburtsdatum);

		JLabel lblAlter = new JLabel("Alter");
		lblAlter.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAlter.setBounds(23, 434, 123, 23);
		frame.getContentPane().add(lblAlter);

		textAlter = new JTextField();
		textAlter.setFont(new Font("Tahoma", Font.BOLD, 18));
		textAlter.setColumns(10);
		textAlter.setBounds(169, 432, 166, 33);
		frame.getContentPane().add(textAlter);

		JLabel lblLohn = new JLabel("Lohn");
		lblLohn.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblLohn.setBounds(23, 494, 123, 23);
		frame.getContentPane().add(lblLohn);

		textLohn = new JTextField();
		textLohn.setFont(new Font("Tahoma", Font.BOLD, 18));
		textLohn.setColumns(10);
		textLohn.setBounds(169, 492, 166, 33);
		frame.getContentPane().add(textLohn);

		JButton btnDrucken = new JButton("Drucken");
		btnDrucken.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MessageFormat header = new MessageFormat("Druck wird ausgeführt");
				MessageFormat footer = new MessageFormat("Seite {0, number, integer}");
				try {
					table.print();
				} catch (java.awt.print.PrinterException ev) {
					System.err.format("Kein Drucker gefungen", ev.getMessage());
				}
			}
		});
		
		btnDrucken.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnDrucken.setBounds(188, 577, 139, 44);
		frame.getContentPane().add(btnDrucken);

		JButton btnReset = new JButton("Zur\u00FCcksetzen");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				textEmpID.setText(null);
				textNlNumber.setText(null);
				textVorname.setText(null);
				textNachname.setText(null);
				textGeschlecht.setText(null);
				textGeburtsdatum.setText(null);
				textAlter.setText(null);
				textLohn.setText(null);
			}
		});
		
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnReset.setBounds(359, 577, 158, 44);
		frame.getContentPane().add(btnReset);

		JButton btnClose = new JButton("Schlie\u00DFen");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				frame = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(frame, "Wollen Sie die Anwendung wirklich beenden?",
						"Employee Database System", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		});
		
		btnClose.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnClose.setBounds(551, 577, 139, 44);
		frame.getContentPane().add(btnClose);

		JLabel lblNewLabel = new JLabel("Employee Database Management System");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(265, 11, 538, 50);
		frame.getContentPane().add(lblNewLabel);

		JButton btnListe = new JButton("Liste");
		btnListe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedata", "root",
							"password");
					String sql = "SELECT * FROM employee";
					PreparedStatement pst = conn.prepareStatement(sql);
					ResultSet rs = pst.executeQuery();
					while (rs.next()) {
						model.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
								rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), });
					}
					table.setModel(model);
					conn.close(); // ?
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		
		btnListe.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnListe.setBounds(661, 426, 139, 44);
		frame.getContentPane().add(btnListe);
	}
}