package pl.eit.mo.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.eit.mo.core.interfaces.IRepairAlgorithm;
import pl.eit.mo.core.interfaces.IValidator;
import pl.eit.mo.core.others.DaySchedule;

public class HRUtil {

	/** naprawiam macierz od podanego dnia korzystajac z podanego 
	 * algorytmu naprawy z uwzglednieniem walidatorow */
	public static boolean repairMatrix(int startDay, int endDay, HRMatrix tmpHrMatrix,
			IRepairAlgorithm repairAlgorithm, List<IValidator> validators) {
		
		for(int day=startDay; day <endDay; day++){
			tmpHrMatrix.recalculateDay(day);
			DaySchedule daySchedule = tmpHrMatrix.getDay(day);
			
			// musze zwalidowac i jesli jest zle to uruchomic 
			// algorytm naprawy na tym dniu
			boolean isValid = false;
			int probeNumber = 0;
			while(!isValid && probeNumber < repairAlgorithm.getMaxNumberOfDayRepairsProbes()){
				probeNumber++;
				List<Integer> faults = new ArrayList<Integer>();
				for(IValidator validator : validators){
					List<Integer> tmpFaults = validator.excecute(daySchedule, tmpHrMatrix.getRows());
					// scalam listy (bez duplikatow)
					Set setboth = new HashSet(faults);
					setboth.addAll(tmpFaults);
					faults.clear();
					faults.addAll(setboth);
				}
				// sprawdzam czy musze cos naprawiac
				if(faults.size() != 0){
					repairAlgorithm.excecute(daySchedule.getScheduleFields(), faults);
					tmpHrMatrix.recalculateDay(day);
				}else{
					isValid = true;
				}
			}
			// nie udalo sie naprawic tego dnia koncze algorytm generowania macierzy
			if(isValid == false){
				return false;
			}
			
		}
		return true;
	}
}
