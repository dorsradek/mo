package pl.eit.mo.core.others;

public class Taboo {
	
	/** liczba iteracji pozostalych do 
	 * usuniecia zabronienia*/
	private int numberOfRoundsToRemove;
	
	/** zabronienie */
	private String taboo;
	
	/** jakosc zabronienia 
	 * (wartosc funkcji celu)*/
	private double quality;

	public String getTaboo() {
		return taboo;
	}

	public void setTaboo(String taboo) {
		this.taboo = taboo;
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
	
	
}
