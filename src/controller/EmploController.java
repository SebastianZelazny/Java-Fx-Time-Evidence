package controller;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.swing.JOptionPane;

import dbConnect.DBConn;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.TableModel;

public class EmploController {
	
	private DBConn connDB;
	
    @FXML
    private MenuItem MIAddUser;

    @FXML
    private MenuItem MIDeleteUser;

    @FXML
    private MenuItem MIUpdateUser;

    @FXML
    private MenuItem MIRefresTable;
    
    @FXML
    private MenuItem MIInfo;

    @FXML
    private GridPane GridPane;

    @FXML
    private TextField TFLogin;

    @FXML
    private TextField TFPassword;

    @FXML
    private TextField TFName;

    @FXML
    private TextField TFSurname;

    @FXML
    private TextField TFRateMate;

    @FXML
    private TextField TFRateSchool;

    @FXML
    private TextField TFRateDeleg;
    ObservableList<TableModel> Data;
    
    @FXML
    private ComboBox<String> CBRole = new ComboBox<>();
    
    @FXML
    private Button BtnAddUser;

    @FXML
    private Button BtnDeleteUser;
    
    @FXML
    private Button BtnUpdateUser;
    
    @FXML
    private Label LblError;
    
    @FXML
    private Label LblLogin;

    @FXML
    private Label LblPass;

    @FXML
    private Label LblRole;
    
    @FXML
    private TableView<TableModel> TVemploo;
    @FXML
    private TableColumn<TableModel, Integer> TCID_E;

    @FXML
    private TableColumn<TableModel, Integer> TCID_L;

    @FXML
    private TableColumn<TableModel, String> TCFName;

    @FXML
    private TableColumn<TableModel, String> TCLName;

    @FXML
    private TableColumn<TableModel, String> TCRMaterial;

    @FXML
    private TableColumn<TableModel, String> TCRSchooling;

    @FXML
    private TableColumn<TableModel, String> TCRDelegation;
    
    public void initialize()
    {
    	connDB = new DBConn();
    	CBRole.getItems().addAll("emp","user");
    	CBRole.setVisibleRowCount(2);
    	CBRole.getSelectionModel().selectLast();
    	MIRefresTable.setVisible(false);
    	
    }

    @FXML
    void ShowMenuAddUser(ActionEvent event) {
    	LblLogin.setVisible(true);
        LblPass.setVisible(true);
        LblRole.setVisible(true);
        
        TFLogin.setVisible(true);
        TFPassword.setVisible(true);
        CBRole.setVisible(true);
        
    	GridPane.setVisible(true);
    	BtnAddUser.setVisible(true);
    	TVemploo.setVisible(false);
    	BtnDeleteUser.setVisible(false);
    	MIRefresTable.setVisible(false);
    	BtnUpdateUser.setVisible(false);
    	GridPane.setLayoutY(91);
    	
    	TFName.setText("");
    	TFSurname.setText("");
    	TFRateMate.setText("");
    	TFRateSchool.setText("");
    	TFRateDeleg.setText("");
    	
    }
    
    @FXML
    void ShowMenuDeleteuser(ActionEvent event) throws SQLException {
    	MIRefresTable.setVisible(true);
    	GridPane.setVisible(false);
    	BtnAddUser.setVisible(false);
    	TVemploo.setVisible(true);
    	BtnDeleteUser.setVisible(true);
    	BtnUpdateUser.setVisible(false);
    	ShowEmplo();
    	TFName.setText("");
    	TFSurname.setText("");
    	TFRateMate.setText("");
    	TFRateSchool.setText("");
    	TFRateDeleg.setText("");
    } 
    
    @FXML
    void ShowMenuUpdateUser(ActionEvent event) throws SQLException {
    	ShowEmplo();
    	TVemploo.setVisible(true);
    	GridPane.setVisible(true);
    	GridPane.setLayoutY(210);
   
    	BtnDeleteUser.setVisible(false);
    	BtnAddUser.setVisible(false);
    	BtnDeleteUser.setVisible(false);
    	BtnUpdateUser.setVisible(true);
    	
    	LblLogin.setVisible(false);
        LblPass.setVisible(false);
        LblRole.setVisible(false);
        
        TFLogin.setVisible(false);
        TFPassword.setVisible(false);
        CBRole.setVisible(false);
        
        BtnUpdateUser.setLayoutY(465);
        
        MIRefresTable.setVisible(true);
        
        TFName.setText("");
    	TFSurname.setText("");
    	TFRateMate.setText("");
    	TFRateSchool.setText("");
    	TFRateDeleg.setText("");
    }
    
    @FXML
    void ShowInfo(ActionEvent event) {
    	JOptionPane.showMessageDialog(null, "Helpdesk \nE-mail: zxc@o2.pl \nTelefon: 1233221 ");
    }
    @FXML
    void Close(ActionEvent event) {
    	System.exit(1);
    }
    
    @FXML
    void FillInTF(MouseEvent event) {
    	TFName.setText(TVemploo.getSelectionModel().getSelectedItem().getName());
    	TFSurname.setText(TVemploo.getSelectionModel().getSelectedItem().getSurname());
    	TFRateMate.setText(TVemploo.getSelectionModel().getSelectedItem().getRateMat());
    	TFRateSchool.setText(TVemploo.getSelectionModel().getSelectedItem().getRateSchooling());
    	TFRateDeleg.setText(TVemploo.getSelectionModel().getSelectedItem().getRateDelegate());
    }
    
    @FXML
    void RefreshTableEmploo(ActionEvent event) throws SQLException {
    	ShowEmplo();
    	TFName.setText("");
    	TFSurname.setText("");
    	TFRateMate.setText("");
    	TFRateSchool.setText("");
    	TFRateDeleg.setText("");
    }
    
    @FXML
    void AddUser(MouseEvent event) {
    	if(TFLogin.getText().equals("") || TFPassword.getText().equals("")||TFName.getText().equals("")||TFSurname.getText().equals("")||TFRateDeleg.getText().equals("")
    	   ||TFRateMate.getText().equals("")||TFRateSchool.getText().equals("")||CBRole.getValue().equals(null))
    	{
    		LblError.setText("Proszê uzupe³niæ wszystkie pola");
    	}
    	else
    	{
    		try
    		{
		    	PreparedStatement preparedstmt = null;
		    	Connection con = null;
		    	con = connDB.connection();
		    	String InsertLogins = "Insert into Logins (login,pass,role) values (?,?,?);";
		    	preparedstmt = con.prepareStatement(InsertLogins);
		    	preparedstmt.setString(1, TFLogin.getText());
		    	preparedstmt.setString(2, TFPassword.getText());
		    	preparedstmt.setString(3, CBRole.getValue());
		    	preparedstmt.execute();
		    	
		    	ResultSet rs ;
		    	String GetIndex = "Select * from logins where login= '"+TFLogin.getText()+"';";
		    	String index =null;
		    	rs = con.createStatement().executeQuery(GetIndex);
		    	while (rs.next())
		    		index = rs.getString(1);
		    	
		    	System.out.println(index);
		    	
		    	preparedstmt = null;
		    	
		    	String AddUser = "Insert Into employee (ID_Login,FirstName,LastName,MaterialPrepare,scholing,delegation) value(?,?,?,?,?,?);";
		    	preparedstmt = con.prepareStatement(AddUser);
		    	preparedstmt.setString(1, index);
		    	preparedstmt.setString(2, TFName.getText());
		    	preparedstmt.setString(3, TFSurname.getText());
		    	preparedstmt.setString(4, TFRateMate.getText());
		    	preparedstmt.setString(5, TFRateSchool.getText());
		    	preparedstmt.setString(6, TFRateDeleg.getText());
		    	preparedstmt.execute();
		    	
		    	TFName.setText("");
		    	TFLogin.setText("");
		    	TFPassword.setText("");
		    	TFSurname.setText("");
		    	TFRateMate.setText("");
		    	TFRateSchool.setText("");
		    	TFRateDeleg.setText("");
		    	LblError.setText("");
    		}
    		catch (SQLException e) {
				if(e instanceof SQLIntegrityConstraintViolationException)
				{
					
					LblError.setText("Podany uzytkownik juz istnieje");
					TFName.setText("");
			    	TFLogin.setText("");
			    	TFPassword.setText("");
			    	TFSurname.setText("");
			    	TFRateMate.setText("");
			    	TFRateSchool.setText("");
			    	TFRateDeleg.setText("");
			    	
				}
				else
				{
					TFName.setText("");
			    	TFLogin.setText("");
			    	TFPassword.setText("");
			    	TFSurname.setText("");
			    	TFRateMate.setText("");
			    	TFRateSchool.setText("");
			    	TFRateDeleg.setText("");
			    	LblError.setText("");
					e.printStackTrace();
				}
			}
    	}
    }
    @FXML
    void DeleteUser(MouseEvent event) {
    	
    	PreparedStatement preparedStmt =null;
    	Connection con = connDB.connection();
    	
    	try {
    		int indexDel = TVemploo.getSelectionModel().getSelectedItem().getID_E();
    		String DeleteUser = "delete from employee where ID_Emplo = "+indexDel;
			preparedStmt = con.prepareStatement(DeleteUser);
			preparedStmt.execute();
			LblError.setVisible(false);
			LblError.setText("");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (NullPointerException e) {
			LblError.setVisible(true);
			LblError.setText("Proszê wybraæ wiersz do usuniêcia");
		}
    	
    }
    
    @FXML
    void UpdateUser(MouseEvent event) {
    	PreparedStatement preparedStmt =null;
    	Connection con = connDB.connection();
    	
    	try {
    		String UpdateUser = "Update employee set FirstName = ?, LastName = ?, MaterialPrepare = ?, scholing = ?, delegation = ? where ID_Emplo = ?";
    		int indexUpdate = TVemploo.getSelectionModel().getSelectedItem().getID_E();
			preparedStmt = con.prepareStatement(UpdateUser);
			preparedStmt.setString(1, TFName.getText());
			preparedStmt.setString(2, TFSurname.getText());
			preparedStmt.setString(3, TFRateMate.getText());
			preparedStmt.setString(4, TFRateSchool.getText());
			preparedStmt.setString(5, TFRateDeleg.getText());
			preparedStmt.setInt(6, indexUpdate);
			preparedStmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    void ShowEmplo() throws SQLException
    {
    	Data = FXCollections.observableArrayList();
    	ResultSet rs;
    	Connection con = connDB.connection();
    	String ShowEmploo = "Select * from employee;";
    	rs = con.createStatement().executeQuery(ShowEmploo);
    	while(rs.next())
    	{
    		Data.add(new TableModel(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
    	}
    	System.out.println(Data);
    	TCID_E.setCellValueFactory(new PropertyValueFactory<TableModel,Integer>("ID_E"));
    	TCID_L.setCellValueFactory(new PropertyValueFactory<TableModel,Integer>("ID_L"));
    	TCFName.setCellValueFactory(new PropertyValueFactory<TableModel,String>("Name"));
    	TCLName.setCellValueFactory(new PropertyValueFactory<TableModel,String>("Surname"));
    	TCRMaterial.setCellValueFactory(new PropertyValueFactory<TableModel,String>("RateMat"));
    	TCRSchooling.setCellValueFactory(new PropertyValueFactory<TableModel,String>("RateSchooling"));
    	TCRDelegation.setCellValueFactory(new PropertyValueFactory<TableModel,String>("RateDelegate"));
    	
    	TVemploo.setItems(null);
    	TVemploo.setItems(Data);
    }
    
}
