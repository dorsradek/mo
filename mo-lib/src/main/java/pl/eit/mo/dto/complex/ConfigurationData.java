package pl.eit.mo.dto.complex;

import java.util.List;

import pl.eit.mo.dto.core.MovementConfiguration;
import pl.eit.mo.dto.core.RepairAlgorithmConfiguration;

public class ConfigurationData
{
	/** okres rozliczeniowy */
	private int periodInDays;

	/** liczba cykli programu po okresie rozliczeniowym */
	private int numberOfCycles;

	/** zakres dni na ktorych dziala kazdy z ruchow w jednej iteracji */
	private int movementRangeInDays;

	/**
	 * czy podejmowane ruchy respektuja tablice zabronien wygenerowana przez
	 * algorytm naprawy
	 */
	private boolean isMovementsLookOnRepairsTaboos;

	/**
	 * wpolczynnik okresla na jak dlugo dany ruch pozostaje na liscie Taboo.
	 * calkowita liczba iteracji zalezy rowniez od tego jak bardzo polepszyla
	 * sie funkcja celu
	 */
	private float movementTabooRatio;

	/**
	 * wpolczynnik okresla na jak dlugo dany ruch NAPRAWY pozostaje na liscie
	 * Taboo. calkowita liczba iteracji zalezy rowniez od tego jak bardzo
	 * polepszyla sie funkcja celu
	 */
	private float reapirTabooRatio;

	/** konfiguracja ruchow */
	private List<MovementConfiguration> movementsConf;

	/** konfiguracja algorytmow naprawy */
	private List<RepairAlgorithmConfiguration> repairsConf;

}
