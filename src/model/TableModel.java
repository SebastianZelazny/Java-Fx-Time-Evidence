package model;

public class TableModel {
	
	private int ID_E;
	private int ID_L;
	private String Name;
	private String Surname;
	private String RateMat;
	private String RateSchooling;
	private String RateDelegate;
	
	public int getID_E() {
		return ID_E;
	}
	public void setID_E(int iD_E) {
		ID_E = iD_E;
	}
	public int getID_L() {
		return ID_L;
	}
	public void setID_L(int iD_L) {
		ID_L = iD_L;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getSurname() {
		return Surname;
	}
	public void setSurname(String surname) {
		Surname = surname;
	}
	public String getRateMat() {
		return RateMat;
	}
	public void setRateMat(String rateMat) {
		RateMat = rateMat;
	}
	public String getRateSchooling() {
		return RateSchooling;
	}
	public void setRateSchooling(String rateSchooling) {
		RateSchooling = rateSchooling;
	}
	public String getRateDelegate() {
		return RateDelegate;
	}
	public void setRateDelegate(String rateDelegate) {
		RateDelegate = rateDelegate;
	}
	public TableModel() {
		// TODO Auto-generated constructor stub
	}
	public TableModel(int ID_E, int ID_L,String Name,String Surname,String RateMat,String RateSchooling,String RateDelegate) {
		this.ID_E = ID_E;
		this.ID_L = ID_L;
		this.Name = Name;
		this.Surname = Surname;
		this.RateMat = RateMat;
		this.RateSchooling = RateSchooling;
		this.RateDelegate = RateDelegate;
	}
	

}
