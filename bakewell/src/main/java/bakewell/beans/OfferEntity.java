package bakewell.beans;
 
import java.io.Serializable;
import java.util.Date;
import java.util.List;
 
public class OfferEntity implements Serializable {
 
	private static final long serialVersionUID = 1L;
 
	private int id;
	
	private String customerName;
	private String customerAddress;
	private String productSpecification;
	private int productQuantity;
	private String deliveryAddress;
	private Date deliveryDate;
	private String customerMessage;
	private List<IngredientEntity> ingredientList;
	private List<Integer> ingredientQuantities;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public String getProductSpecification() {
		return productSpecification;
	}
	public void setProductSpecification(String productSpecification) {
		this.productSpecification = productSpecification;
	}
	public int getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getCustomerMessage() {
		return customerMessage;
	}
	public void setCustomerMessage(String customerMessage) {
		this.customerMessage = customerMessage;
	}
	public List<IngredientEntity> getIngredientList() {
		return ingredientList;
	}
	public void setIngredientList(List<IngredientEntity> ingredientList) {
		this.ingredientList = ingredientList;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<Integer> getIngredientQuantities() {
		return ingredientQuantities;
	}
	public void setIngredientQuantities(List<Integer> ingredientQuantities) {
		this.ingredientQuantities = ingredientQuantities;
	}
	
	
 
}
