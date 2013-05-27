package pl.eit.mo.core.others;

public class Taboo {
	
	/** liczba iteracji pozostalych do 
	 * usuniecia zabronienia*/
	private int numberOfRoundsToRemove;
	
	/** zabronienie */
	private String tabooValue;
	
	/** jakosc zabronienia 
	 * (wartosc funkcji celu)*/
	private double quality;

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Taboo && obj != null){
			if(tabooValue.equals(((Taboo)obj).getTabooValue())){
				return true;
			}
		}
		return false;
	}
	
	public Taboo(String tabooValue) {
		this.tabooValue = tabooValue;
	}

	public double getQuality() {
		return quality;
	}

	public void setQuality(double quality) {
		this.quality = quality;
	}

	public int getNumberOfRoundsToRemove() {
		return numberOfRoundsToRemove;
	}

	public void setNumberOfRoundsToRemove(int numberOfRoundsToRemove) {
		this.numberOfRoundsToRemove = numberOfRoundsToRemove;
	}

	public String getTabooValue() {
		return tabooValue;
	}
	
}
