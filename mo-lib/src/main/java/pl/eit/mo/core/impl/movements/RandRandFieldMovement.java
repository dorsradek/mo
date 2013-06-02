package pl.eit.mo.core.impl.movements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.eit.mo.core.HRMatrix;
import pl.eit.mo.core.interfaces.IMovement;
import pl.eit.mo.core.others.DaySchedule;
import pl.eit.mo.core.others.ScheduleField;
import pl.eit.mo.core.others.Taboo;
import pl.eit.mo.core.others.TaskRow;

public class RandRandFieldMovement extends IMovement{
	
	private Random randomGenerator;
	
	public RandRandFieldMovement() {
		randomGenerator = new Random();
		this.name = "RandRandFieldMovement";
	}
	
	/** algorytm przenosi wszystkich parcownikow z losowego pola w losowe miejsce */
	@Override
	public boolean tryExcecute(HRMatrix hrMatrix, int day,
			List<TaskRow> taskRowsData, List<Taboo> taboos) {
		
		// movementTabooValue = "skad; dokad; idPracownika"
		

		DaySchedule currDay = hrMatrix.getDay(day);
		int sourceFieldIndex = randomGenerator.nextInt(currDay.getScheduleFields().size());
		ScheduleField sourceField = currDay.getScheduleFields().get(sourceFieldIndex);
		while(sourceField.getEmployees().size() == 0){
			sourceFieldIndex = randomGenerator.nextInt(currDay.getScheduleFields().size());
			sourceField = currDay.getScheduleFields().get(sourceFieldIndex);
		}
		
		int destFieldIndex = randomGenerator.nextInt(currDay.getScheduleFields().size());
		ScheduleField destField = currDay.getScheduleFields().get(destFieldIndex);
		
		// sprawdzam czy nie wykonalem juz takiego ruchu
		movementTabooValue = "field;"+sourceFieldIndex+";"+destFieldIndex+";"+day;
		Taboo taboo = new Taboo(movementTabooValue);
		if(!taboos.contains(taboo) && sourceFieldIndex != destFieldIndex){
			// wykonuje ruch 
			destField.getEmployees().addAll(sourceField.getEmployees());
			sourceField.setEmployees(new ArrayList<Integer>());
			return true;
		}
		
		return false;
	}

}
