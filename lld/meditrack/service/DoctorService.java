package meditrack.service;

import meditrack.entity.Doctor;
import meditrack.repository.DataStore;

public class DoctorService {
    
    private DataStore<Doctor> ds=new DataStore<>();

    public void add(Doctor d){
        ds.add(d.getId(), d);
    }

    public Doctor get(int id){
        return ds.get(id);
    }

    public void list(){
        ds.all().stream().forEach(d->d.display());
    }
}
