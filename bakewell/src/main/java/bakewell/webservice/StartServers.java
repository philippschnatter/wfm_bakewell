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
		System.out.println("Web Service Ingedient GDA Caclulation started!");
		this.recipePriceServiceServer.start();
		System.out.println("Web Service Recipe Price Caclulation started!");

	}
	
	
	public void stop () {
		this.ingredientServiceServer.stop();
		this.recipePriceServiceServer.stop();	
	}
	


}
