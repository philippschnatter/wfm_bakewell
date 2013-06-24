package bakewell.db;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bakewell.beans.GDAReference;

import junit.framework.TestCase;

public class GDAReferenceDAOTest extends TestCase {

	GDAReferenceDAO cDAO = null;
	GDAReference testGDAReference = null;
	GDAReference failGDAReference = null;
	ArrayList<GDAReference> result = null;
	
	@Before
	protected void setUp() {
		cDAO = new GDAReferenceDAO("jdbc:h2:file:src/test/resources/db/wfDBTest", "sa", "");
		testGDAReference = new GDAReference("coal", 10.0);
		failGDAReference = new GDAReference(); 
		result = new ArrayList<GDAReference>();
		
	}
	
	@After
	protected void tearDown() {
		cDAO = null;
		testGDAReference = null;
		failGDAReference = null;
		result = null;
	}
	
	@Test
	public void testGDAReferenceDAOInsert_ShouldWork() {
		GDAReference a = cDAO.insertGDAReference(testGDAReference);
		result = cDAO.selectGDAReference(testGDAReference);
		assertTrue(result.get(0).getName().equals(testGDAReference.getName()));
		assertTrue(result.get(0).getAmount().equals(testGDAReference.getAmount()));
		
		assertTrue(result.get(0).getName().equals(a.getName()));
		assertTrue(result.get(0).getAmount().equals(a.getAmount()));
		assert(a.getId() != null);
		
		cDAO.deleteGDAReference(result.get(0));
	}
	
	@Test
	public void testGDAReferenceDAOInsert_ShouldFail() {
		GDAReference a = null;
		a = cDAO.insertGDAReference(failGDAReference);
		result = cDAO.selectGDAReference(failGDAReference);
		assert(result.size() == 0);
		assert(a == null);
	}
	
	@Test
	public void testGDAReferenceDAODelete_ShouldWork() {
		ArrayList<GDAReference> a = new ArrayList<GDAReference>();
		cDAO.insertGDAReference(testGDAReference);
		result = cDAO.selectGDAReference(testGDAReference);
		assert(result.size() == 1);
		a = cDAO.deleteGDAReference(testGDAReference);
		result = cDAO.selectGDAReference(testGDAReference);
		assert(result.size() == 0);
		
		assertTrue(a.get(0).getName().equals(testGDAReference.getName()));
		assertTrue(a.get(0).getAmount().equals(testGDAReference.getAmount()));
		assert(a.get(0).getId() != null);
	}
	
	@Test
	public void testGDAReferenceDAODelete_ShouldFail() {
		ArrayList<GDAReference> a = new ArrayList<GDAReference>();
		cDAO.insertGDAReference(testGDAReference);
		a = cDAO.deleteGDAReference(failGDAReference);
		result = cDAO.selectGDAReference(testGDAReference);
		assert(result.size() == 1);
		assert(a.isEmpty());
		cDAO.deleteGDAReference(testGDAReference);
	}
	
	@Test
	public void testGDAReferenceDAOSelect_ShouldWork() {
		cDAO.insertGDAReference(testGDAReference);
		cDAO.insertGDAReference(testGDAReference);
		
		GDAReference searchGDAReferenceName = new GDAReference("Test1", null);
		GDAReference searchGDAReferenceAmount = new GDAReference(null, 10.0);
		
		result = cDAO.selectGDAReference(searchGDAReferenceName);
		assert(result.size() == 2);
		assert(result.get(0).getName().equals("Test1"));
		
		result = cDAO.selectGDAReference(searchGDAReferenceAmount);
		assert(result.size() == 2);
		assert(result.get(0).getAmount().equals(10.0));
		
		cDAO.deleteGDAReference(testGDAReference);
	}
	
	@Test
	public void testGDAReferenceDAOSelect_ShouldFail() {
		cDAO.insertGDAReference(testGDAReference);
		GDAReference searchGDAReferenceName = new GDAReference("Test1", null);
		GDAReference searchGDAReferenceAmount = new GDAReference(null, 10.0);
		
		result = cDAO.selectGDAReference(searchGDAReferenceName);
		assert(result.size() == 0);
		
		result = cDAO.selectGDAReference(searchGDAReferenceAmount);
		assert(result.size() == 0);
		
		cDAO.deleteGDAReference(testGDAReference);	
	}
	
	@Test
	public void testGDAReferenceDAOUpdate_ShouldWork() {
		ArrayList<GDAReference> a = new ArrayList<GDAReference>();
		cDAO.insertGDAReference(testGDAReference);
		GDAReference updateGDAReference = new GDAReference("Test1", 10.0);
		a = cDAO.UpdateGDAReference(updateGDAReference, testGDAReference);
		
		result = cDAO.selectGDAReference(testGDAReference);
		assert(result.size() == 0);
		
		result = cDAO.selectGDAReference(updateGDAReference);
		assert(result.size() == 1);
		
		assertTrue(a.get(0).getName().equals(updateGDAReference.getName()));
		assertTrue(a.get(0).getAmount().equals(updateGDAReference.getAmount()));
		assert(a.get(0).getId() != null);
		
		cDAO.deleteGDAReference(updateGDAReference);
	}
	
	@Test
	public void testGDAReferenceDAOUpdate_ShouldFail() {
		ArrayList<GDAReference> a = new ArrayList<GDAReference>();
		cDAO.insertGDAReference(testGDAReference);
		GDAReference updateGDAReference = new GDAReference("Test2", 11.0);
		cDAO.insertGDAReference(updateGDAReference);
		result = cDAO.selectGDAReference(testGDAReference);
		GDAReference updateGDAReference2 = new GDAReference("Test2", 11.0);
		updateGDAReference2.setId(result.get(0).getId());
		a = cDAO.UpdateGDAReference(updateGDAReference2, updateGDAReference);
		assert(a.isEmpty());
		
		cDAO.selectGDAReference(testGDAReference);
		assert(result.size() == 1);
		cDAO.selectGDAReference(updateGDAReference);
		assert(result.size() == 1);
		cDAO.selectGDAReference(updateGDAReference2);
		assert(result.size() == 0);
		cDAO.deleteGDAReference(testGDAReference);
		cDAO.deleteGDAReference(updateGDAReference);
	}
}
