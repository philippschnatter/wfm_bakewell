package bakewell.webservice;


public class StartServers {
	
	public IngredientServiceServer ingredientServiceServer = null;
	
	
    public static void main(String ... args)
    {
    	new StartServers().start();
    }
	
	public StartServers() {
		this.ingredientServiceServer = new IngredientServiceServer();		
	}
	
	public void start() {
		this.ingredientServiceServer.start();	
	}
	
	
	public void stop () {
		this.ingredientServiceServer.stop();	
	}
	


}
