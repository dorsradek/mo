package pl.eit.mo.core.impl.movements;

import java.util.List;
import java.util.Random;

import pl.eit.mo.core.interfaces.IMovement;
import pl.eit.mo.core.others.DaySchedule;
import pl.eit.mo.core.others.ScheduleField;
import pl.eit.mo.core.others.Taboo;
import pl.eit.mo.core.others.TaskRow;

public class RandRandMovement extends IMovement{
	
	private Random randomGenerator;
	
	public RandRandMovement() {
		randomGenerator = new Random();
		this.name = "RandRandMovement";
	}
	
	/** algorytm przenosi losowego pracownika z losowego pola w losowe miejsce */
	@Override
	public boolean tryExcecute(DaySchedule day, List<TaskRow> taskRowsData,
			List<Taboo> taboos) {
		
		// movementTabooValue = "skad; dokad; idPracownika"
		
		int emplId = -1;
		int sourceFieldIndex = randomGenerator.nextInt(day.getScheduleFields().size());
		ScheduleField sourceField = day.getScheduleFields().get(sourceFieldIndex);
		while(sourceField.getEmployees().size() == 0){
			sourceFieldIndex = randomGenerator.nextInt(day.getScheduleFields().size());
			sourceField = day.getScheduleFields().get(sourceFieldIndex);
			emplId = sourceField.getRandEmployeeId();
		}
		
		int destFieldIndex = randomGenerator.nextInt(day.getScheduleFields().size());
		ScheduleField destField = day.getScheduleFields().get(destFieldIndex);
		while(destField.getEmployees().size() == 0){
			destFieldIndex = randomGenerator.nextInt(day.getScheduleFields().size());
			destField = day.getScheduleFields().get(destFieldIndex);
		}
		
		if(emplId == -1){
			return false;
		}
		
		// sprawdzam czy nie wykonalem juz takiego ruchu
		//movementTabooValue = sourceFieldIndex +";"+ destFieldIndex +";"+ emplId +";";
		movementTabooValue = ""+emplId;
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
