package meditrack.service;

import java.util.List;

import meditrack.entity.Patient;
import meditrack.repository.DataStore;
import meditrack.utils.CSVUtils;

public class PatientService {
    private DataStore<Patient> ps=new DataStore<>();

     public void add(Patient d){
        ps.add(d.getId(), d);
    }

    public Patient get(int id){
        return ps.get(id);
    }

    public void list(){
        ps.all().stream().forEach(d->d.display());
    }

    public void saveCSV(String file){
        List<String> lines=ps.all().stream().map(p->p.toCSV()).toList();
        CSVUtils.writeLines(file, lines);
    }

    public void loadCSV(String file){
        CSVUtils.read(file).forEach(a->add(Patient.fromCSV(a)));
    }
}
