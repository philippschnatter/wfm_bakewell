package bakewell.webservice.ingredient;

public enum GdaTable {
	

	// empfohlene Tageszufuhr fuer maenner. source: http://de.wikipedia.org/wiki/Guideline_Daily_Amount
	RECGDA_ENERGY (2500.0),	// in kcal per day
	RECGDA_PROTEIN (55.0),	// in gram per day
	RECGDA_CARBON (300.0),	// in gram per day
	RECGDA_FAT (95.0),		// in gram per day
	RECGDA_FIBER (24.0),	// in gram per day
	RECGDA_SODIUM (2.4);	// in gram per day
	
	private final double ingramperday;
	
	// constructor
	GdaTable(double ingramperday) {
		this.ingramperday = ingramperday;
	}
	
	public double getGramPerDay() {
		return ingramperday;
	}
	
	

}
