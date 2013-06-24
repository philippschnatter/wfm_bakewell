package bakewell.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Customer")
public class Customer implements Serializable {


/**
*
*/
private static final long serialVersionUID = -279323690879135258L;

private Integer id = null;
private String firstName = null;
private String lastName = null;
private String address = null;
private String telNo = null;
private String mailAddress = null;
private String company = null;
private String password = null;

public Customer() {

}

public Customer(String firstName, String lastName, String address, String telNo, String mailAddress, String company, String password)
{
	this.firstName = firstName;
	this.lastName = lastName;
	this.address = address;
	this.telNo = telNo;
	this.mailAddress = mailAddress;
	this.company = company;
	this.password = password;
}

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public String getTelNo() {
	return telNo;
}

public void setTelNo(String telNo) {
	this.telNo = telNo;
}

public String getMailAddress() {
	return mailAddress;
}

public void setMailAddress(String mailAddress) {
	this.mailAddress = mailAddress;
}

public String getCompany() {
	return company;
}

public void setCompany(String company) {
	this.company = company;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}



}