import bakewell.db.CustomerDAOTest;
import bakewell.db.IngredientDAOTest;
import bakewell.db.RecipeDAOTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test workflow");
		
		suite.addTestSuite(CustomerDAOTest.class);
		suite.addTestSuite(IngredientDAOTest.class);
		suite.addTestSuite(RecipeDAOTest.class);
		return suite;
	}

}