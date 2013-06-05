package bakewell.webservice.ingredient;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import wfm.bean.Address;
import wfm.bean.Customer;

public class IngredientRESTService {
	
	
	
	public Customer validateAddress(String firstName, String lastName,
			String street, String city, String zip, String country,
			String telNo, String mailAddress)
	 throws WebApplicationException
	{				
		for (Address address : this.knownAddresses)
		{
			if (address.getStreet().equals(street) &&
				address.getCity().equals(city) &&
				address.getZip().equals(zip) &&
				address.getCountry().equals(country))
			{
				return new Customer(firstName, lastName, street, city, zip, country, telNo, mailAddress);
			}
		}
		ResponseBuilder builder = Response.status(Status.NOT_FOUND);
		builder.type("text/html");
		builder.entity("<h3>Invalid address! Address could not be found</h3>");

		throw new WebApplicationException(builder.build());
	}

}
