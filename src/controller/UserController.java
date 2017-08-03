package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.mysql.fabric.xmlrpc.base.Data;

import dbConnect.DBConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.LoginModel;

public class UserController {
	DBConn conDB;
	LoginController lc = new LoginController();
	
	private int WorkingSchedlueMaterials;
	private int WorkingSchedlueSchooling;
	private int WorkingSchedlueDelegations;
	
    @FXML
    private Menu MRozlicz;
    
    @FXML
    private GridPane GPDolary;
    
    @FXML
    private GridPane GPSettle;

    @FXML
    private ComboBox<String> CBMonth = new ComboBox<>();

    @FXML
    private TextField TFMaterials;

    @FXML
    private TextField TFSchool;

    @FXML
    private TextField TFDelega;

    @FXML
    private Button BtnConvert;
    
    @FXML
    private Label Lbl$Materials;

    @FXML
    private Label Lbl$Schooling;

    @FXML
    private Label Lbl$delegations;

    @FXML
    private Label Lbl$Netto;

    @FXML
    private Label Lbl$Brutto;

    
    public void initialize()
    {
    	GPDolary.setVisible(false);
    	conDB = new DBConn();
    	CBMonth.getItems().addAll("01","02","03","04","05","06","07","08","09","10","11","12");
    	CBMonth.getSelectionModel().selectFirst();
    }
    
    @FXML
    void Convert(MouseEvent event) {
    	PreparedStatement preparedStmt = null;
    	Connection con;
    	con = conDB.connection();
    	ResultSet rs;
    	String SelectID = "Select ID_Emplo,MaterialPrepare,scholing,delegation from employee,logins where login='"+lc.getLogin()+"' and employee.ID_Login = logins.ID_L;";
    	int IndeksEmplo = 0;
    	int countMounth = 0;
    	System.out.println("Login: "+lc.getLogin());
    	
    	try {
			rs = con.createStatement().executeQuery(SelectID);
			
			while(rs.next())
			{
				IndeksEmplo = rs.getInt(1);
				WorkingSchedlueMaterials = rs.getInt(2);
				WorkingSchedlueSchooling = rs.getInt(3);
				WorkingSchedlueDelegations = rs.getInt(4);
				System.out.println(IndeksEmplo);
			
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
 
    	
    	String SelectMonth = "select count(month) as Liczba_miesiecy from evidence where id_emp = "+IndeksEmplo+" and month ="+CBMonth.getValue()+";";
    	try
    	{
    		rs = null;
    		rs = con.createStatement().executeQuery(SelectMonth);
    		while(rs.next())
    		{
    			countMounth = rs.getInt(1);
    			System.out.println(countMounth);
    		}
    	}
    	catch (SQLException e) {
			// TODO: handle exception
		}
    	if(countMounth>0)
    	{
    		System.out.println("Ju¿ rozlicza³eœ siê w tym miesi¹cu jeœli chcesz wprowadziæ poprawki skontaktuj siê z Administratorem");
    	}
    	else
    	{
    	   	String InsertEvidence = "Insert into evidence (ID_Emp,Month,Number_H_Mat,Number_H_Sch,Number_H_Del,Ear$Materials,Ear$Schooling,Ear$Delegations,Ear$AllNetto,Ear$AllBrutto) values (?,?,?,?,?,?,?,?,?,?)";
    		
    		double resultMat = Double.valueOf(TFMaterials.getText())*WorkingSchedlueMaterials;
        	double resultScho = Double.valueOf(TFSchool.getText())*WorkingSchedlueSchooling;
        	double resultDele = Double.valueOf(TFDelega.getText())*WorkingSchedlueDelegations;
        	double netto  = resultDele +resultMat + resultScho;
        	double brutto = (netto * 0.23) + netto;
        	
    		try {
        	 	preparedStmt = con.prepareStatement(InsertEvidence);
        		preparedStmt.setInt(1, IndeksEmplo);
    			preparedStmt.setString(2, String.valueOf(CBMonth.getValue()));
    			preparedStmt.setString(3, TFMaterials.getText());
    	    	preparedStmt.setString(4, TFSchool.getText());
    	    	preparedStmt.setString(5, TFDelega.getText());
    	    	preparedStmt.setDouble(6, resultMat);
    	    	preparedStmt.setDouble(7, resultScho);
    	    	preparedStmt.setDouble(8, resultDele);
    	    	preparedStmt.setDouble(9, netto);
    	    	preparedStmt.setDouble(10, brutto);
    	    	preparedStmt.execute();
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    		System.out.println("delegations: "+WorkingSchedlueDelegations);
    		System.out.println("Materials: "+WorkingSchedlueMaterials);
    		System.out.println("Schooling: "+WorkingSchedlueSchooling);
    		
    		System.out.println("delegations: "+TFDelega.getText());
    		System.out.println("Materials: "+TFMaterials.getText());
    		System.out.println("Schooling: "+TFSchool.getText());
        	Lbl$Materials.setText(String.valueOf(Double.valueOf(TFMaterials.getText())*WorkingSchedlueMaterials));
        	Lbl$Schooling.setText(String.valueOf(Double.valueOf(TFSchool.getText())*WorkingSchedlueSchooling));
        	Lbl$delegations.setText(String.valueOf(Double.valueOf(TFDelega.getText())*WorkingSchedlueDelegations));
        	Lbl$Netto.setText(String.valueOf(netto));
        	Lbl$Brutto.setText(String.valueOf(brutto));
        	
        	GPDolary.setVisible(true);
        	GPSettle.setVisible(false);
        	BtnConvert.setVisible(false);
    	}
    	
    	TFDelega.setText("");
    	TFMaterials.setText("");
    	TFSchool.setText("");
    	CBMonth.getSelectionModel().selectFirst(); 	
    }
    @FXML
    void ShowMenuSettleUp(ActionEvent event) {

    }

}
