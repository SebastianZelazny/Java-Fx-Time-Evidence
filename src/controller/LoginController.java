package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dbConnect.DBConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.LoginModel;

public class LoginController {

	private DBConn dbcon;
	LoginModel loginModel;
	public void initialize()
    {
    	dbcon = new DBConn();
    }
    @FXML
    private TextField TFLogin;

    @FXML
    private PasswordField PFPass;

    @FXML
    private Button BtnLogin;

    @FXML
    private Button BtnCheckPass;

    @FXML
    private Label LblError;

    @FXML
    private TextField TFCheckPass;

    @FXML
    private MenuItem MIClose;

    @FXML
    private MenuItem MIHelpDesk;
    @FXML
    void CheckPassPress(MouseEvent event) {
    	PFPass.setVisible(false);
    	TFCheckPass.setDisable(false);
    	TFCheckPass.setVisible(true);
    	TFCheckPass.setText(PFPass.getText());
    }

    @FXML
    void CheckPassRelease(MouseEvent event) {
    	PFPass.setVisible(true);
    	TFCheckPass.setVisible(false);
    	TFCheckPass.setDisable(false);
    }
    public ObservableList<LoginModel> Logins;
    @FXML
    void Sign_in(MouseEvent event)  {
    	Logins = FXCollections.observableArrayList();
    	Connection con = dbcon.connection();
    	ResultSet rs;
    	String login = "";
		try {
			rs = con.createStatement().executeQuery("Select * From logins where Login = '"+TFLogin.getText()+"' and pass = '"+PFPass.getText()+"';");
			/*	while(rs.next())
		    	{
		    		if(rs.getString(2).equals(TFLogin.getText())&&rs.getString(3).equals(PFPass.getText()))
		    		{
		    			System.out.println("Logowanie przebieg³o pomyœlnie");
		    		}
		    		else
		    		{
		    			System.out.println("Podany login lub has³o jest nieprawdidlowe");
		    		}
		    	}*/
				while(rs.next())
				{
					login =rs.getString(2);
				}
				if(!login.equals(""))
				{
					System.out.println("Logowanie pomyœlne");	
				}
				else 
				{
					System.out.println("Logowanie b³êdne");
					LblError.setText("B³êdne has³o lub login!");	
					TFLogin.setText(null);
					PFPass.setText(null);
				}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    }
    @FXML
    void CloseProg(ActionEvent event) {
    	System.exit(1);
    }
    
    @FXML
    void ShowCommu(ActionEvent event) {
    	System.out.println("Info");
    	JOptionPane.showMessageDialog(null, "Helpdesk \nE-mail: zxc@o2.pl \nTelefon: 1233221 ");
    }
    
    

}
