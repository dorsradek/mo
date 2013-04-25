package pl.eit.mo.dto.core;

public class RepairAlgorithmConfiguration
{
	/** nazwa algorytmu naprawy (unikalna) */
	private String name;

	/** czy algorytm naprawy korzysta z mechanizmu zabronien */
	private boolean isUseTaboos;

	/** czy jesli nie potrafi naprawic to ma pominac zabronienia */
	private boolean skipTaboosIfCantRepair;

	/** liczba prob naprawy */
	private int numberOfRepairsProbes;

}
