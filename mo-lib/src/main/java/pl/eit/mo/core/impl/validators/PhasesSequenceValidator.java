package pl.eit.mo.core.impl.validators;

import java.util.ArrayList;
import java.util.List;

import pl.eit.mo.core.interfaces.IValidator;
import pl.eit.mo.core.others.DaySchedule;
import pl.eit.mo.core.others.ScheduleField;
import pl.eit.mo.core.others.TaskRow;

/** walidator sprawdza czy etapy wystepuja we wlasciwej kolejnosci */

public class PhasesSequenceValidator implements IValidator {

	public List<Integer> excecute(DaySchedule daySchedule,
			List<TaskRow> tasksData) {
		
		List<Integer> faults = new ArrayList<Integer>();
		
		// pobieram status kazdego etapu
		List<PhaseField> phasesStatuses = getPhasesStatuses(daySchedule, tasksData);
		
		// zaznaczam blednie wystepujace etapy
		checkInvalidPhases(phasesStatuses);
		
		// tworze wektor wyjsciowy z blednymi wierszami
		for(int rowIndex=0; rowIndex < tasksData.size(); rowIndex++){
			TaskRow row = tasksData.get(rowIndex);
			for(PhaseField fld : phasesStatuses){
				if(fld.phaseName.equals(row.getPhaseName())
						&& fld.projectName.equals(row.getProjectName())){
					// jesli zadanie nie przeszlo walidacji dodaje wiersz do
					// listy z bledami
					if(!fld.phaseValid){
						faults.add(rowIndex);
					}
				}
				
			}
		}
		
		return faults;
	}

	/** sprawdzam czy etapy poprawnie wystepuja po sobie */
	private void checkInvalidPhases(List<PhaseField> phasesStatuses) {
		int phaseIndex = 1;
		PhaseField prevPhase = phasesStatuses.get(0);
		while(phaseIndex < phasesStatuses.size()){
			PhaseField currPhase = phasesStatuses.get(phaseIndex);
			// jesli etapy naleza do jednego projektu to wtedy
			// sprawdzam czy sa wykonywane szeregowo
			if(currPhase.projectName.equals(prevPhase.projectName)){
				// wszystkie przypadki niewlasciwej kolejnosci
				if(currPhase.phaseStatus > 0 
						&& prevPhase.phaseStatus < 2){
					currPhase.phaseValid = false;
				}
			}
			prevPhase = currPhase;
			phaseIndex++;
		}
	}

	/** pobieram liste udostepniajaca informacje o stanie kazdego etapu 
	 * z kazdego projektu */
	private List<PhaseField> getPhasesStatuses(DaySchedule daySchedule,
			List<TaskRow> tasksData) {
		List<PhaseField> tmpPhaseFields = new ArrayList<PhaseField>();
		
		String prevProjectName = tasksData.get(0).getProjectName();
		String prevPhaseName = tasksData.get(0).getPhaseName();
		int phaseStatus = 0;
		int row = 0;
		while(row < tasksData.size()){
			String currProjectName = tasksData.get(row).getProjectName();
			String currPhaseName = tasksData.get(row).getPhaseName();
			ScheduleField field = daySchedule.getScheduleFields().get(row);
			
			if(prevPhaseName.equals(currPhaseName) 
					&& prevProjectName.equals(currProjectName)){
				if(field.getWorkDone() >= tasksData.get(row).getDurationTime()){
					if(phaseStatus == 0){
						phaseStatus = 2;
					}
				}else if(field.getWorkDone() > 0){
					phaseStatus = 1;
				}
				row++;
				// jesli dojde do konca to musze dodac ta faze
				if(row == tasksData.size()){
					PhaseField phaseField = new PhaseField();
					phaseField.projectName = currProjectName;
					phaseField.phaseName = currPhaseName;
					phaseField.phaseStatus = phaseStatus;
					tmpPhaseFields.add(phaseField);
				}
			}else{
				PhaseField phaseField = new PhaseField();
				phaseField.projectName = prevProjectName;
				phaseField.phaseName = prevPhaseName;
				phaseField.phaseStatus = phaseStatus;
				tmpPhaseFields.add(phaseField);
				// nie zwiekszam zmiennej row -> musze  
				// jeszcze raz przeleciec petla po tym wierszu
				phaseStatus = 0;
			}
			prevProjectName = currProjectName;
			prevPhaseName = currPhaseName;
		}
		
		return tmpPhaseFields;
	}
	
	/** pomocnicza klasa dostarczajaca informacji o statusie kazdej fazy */
	private class PhaseField{
		public String projectName;
		public String phaseName;
		// 0 - etap nierozpoczety, 1 - rozpoczety, 2 - zakonczony 
		public int phaseStatus;
		
		// czy etap zachowuje kolejnosc
		public boolean phaseValid = true;
	}

}
