/**
 * 
 */
package bakewell.jsf;

/**
 * @author stefan
 *
 */
public class loginBean {
	private String name;
	private String password;
	
	public String login() {
		if(!name.isEmpty()) return "ok";
		else return "denied";
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public loginBean() {
	}
}
