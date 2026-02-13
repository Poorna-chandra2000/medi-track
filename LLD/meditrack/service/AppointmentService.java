package meditrack.service;

import java.util.List;

import meditrack.entity.Appointment;
import meditrack.entity.Doctor;
import meditrack.entity.Patient;
import meditrack.exceptions.AppointmentNotFoundException;
import meditrack.repository.DataStore;

public class AppointmentService {
    private DataStore<Appointment> ds=new DataStore<>();
    public Appointment create(int id,Doctor d,Patient p){
        Appointment a=new Appointment(id, d, p);
        ds.add(id, a);
        return a;
    }

    public Appointment get(int id){
        Appointment a=ds.get(id);
        if(a==null) throw new AppointmentNotFoundException("Not found"+id);
        return a;
    }

    public void list(){
        ds.all().forEach(a->a.display());
    }
}
