package pl.eit.mo.core.impl.movements;

import java.util.List;
import java.util.Random;

import pl.eit.mo.core.HRMatrix;
import pl.eit.mo.core.interfaces.IMovement;
import pl.eit.mo.core.others.DaySchedule;
import pl.eit.mo.core.others.ScheduleField;
import pl.eit.mo.core.others.Taboo;
import pl.eit.mo.core.others.TaskRow;

public class RandRandEmployeeMovement extends IMovement{
	
	private Random randomGenerator;
	
	public RandRandEmployeeMovement() {
		randomGenerator = new Random();
		this.name = "RandRandMovement";
	}
	
	/** algorytm przenosi losowego pracownika z losowego pola w losowe miejsce */
	@Override
	public boolean tryExcecute(HRMatrix hrMatrix, int day,
			List<TaskRow> taskRowsData, List<Taboo> taboos) {
		
		// movementTabooValue = "skad; dokad; idPracownika"
		
		int emplId = -1;
		DaySchedule currDay = hrMatrix.getDay(day);
		int sourceFieldIndex = randomGenerator.nextInt(currDay.getScheduleFields().size());
		ScheduleField sourceField = currDay.getScheduleFields().get(sourceFieldIndex);
		while(sourceField.getEmployees().size() == 0){
			sourceFieldIndex = randomGenerator.nextInt(currDay.getScheduleFields().size());
			sourceField = currDay.getScheduleFields().get(sourceFieldIndex);
			emplId = sourceField.getRandEmployeeId();
		}
		
		int destFieldIndex = randomGenerator.nextInt(currDay.getScheduleFields().size());
		ScheduleField destField = currDay.getScheduleFields().get(destFieldIndex);
		
		if(emplId == -1){
			return false;
		}
		
		// sprawdzam czy nie wykonalem juz takiego ruchu
		//movementTabooValue = sourceFieldIndex +";"+ destFieldIndex +";"+ emplId +";"+day;
		movementTabooValue = "empl;"+emplId+";"+day;
		Taboo taboo = new Taboo(movementTabooValue);
		if(!taboos.contains(taboo) && sourceFieldIndex != destFieldIndex){
			// wykonuje ruch 
			destField.getEmployees().add(emplId);
			sourceField.removeEmployee(emplId);
			return true;
		}
		
		return false;
	}

}
