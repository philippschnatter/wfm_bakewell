package bakewell.webservice;


public class StartServers {
	
	public IngredientServiceServer ingredientServiceServer = null;
	public RecipePriceServiceServer recipePriceServiceServer = null;
	
	
    public static void main(String ... args)
    {
    	new StartServers().start();
    }
	
	public StartServers() {
		this.ingredientServiceServer = new IngredientServiceServer();	
		this.recipePriceServiceServer = new RecipePriceServiceServer();
	}
	
	public void start() {
		this.ingredientServiceServer.start();	
		this.recipePriceServiceServer.start();	
	}
	
	
	public void stop () {
		this.ingredientServiceServer.stop();
		this.recipePriceServiceServer.stop();	
	}
	


}
