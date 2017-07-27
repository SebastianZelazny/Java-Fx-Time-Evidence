package controller;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import dbConnect.DBConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private Label LblError;
    
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
    	
    }

    @FXML
    void ShowMenuAddUser(ActionEvent event) {
    	GridPane.setVisible(true);
    	BtnAddUser.setVisible(true);
    	TVemploo.setVisible(false);
    	BtnDeleteUser.setVisible(false);
    }
    
    @FXML
    void ShowMenuDeleteuser(ActionEvent event) throws SQLException {
    	GridPane.setVisible(false);
    	BtnAddUser.setVisible(false);
    	
    	
    	TVemploo.setVisible(true);
    	BtnDeleteUser.setVisible(true);
    	
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
    	
    }
    
}
