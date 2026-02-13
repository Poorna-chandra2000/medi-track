package meditrack.entity;
import java.util.*;

import meditrack.constants.AppointmentStatus;
import meditrack.interfacepkg.Observer;
import meditrack.interfacepkg.Subject;

public class Appointment extends MedicalEntity implements Subject{
    private Doctor doctor;
    private Patient patient;
    private AppointmentStatus appointmentStatus;
    private List<Observer> observers=new ArrayList<>();

    public Appointment(int id,Doctor d,Patient p){
        this.id=id;
        this.doctor=d;
        this.patient=p;
        this.appointmentStatus=AppointmentStatus.PENDING;
    }

    public Doctor getDoctor(){
        return doctor;
    }


    public void confirm(){
        appointmentStatus=AppointmentStatus.CONFIRMED;
        notifyObservers("Appointment ID:"+id+" Confirmed for Patient"+patient.name+" Consultant Doctor:"+doctor.name);
    }

    @Override
    public void addObserver(Observer o) {
        // TODO Auto-generated method stub
        observers.add(o);
        
    }

    @Override
    public void notifyObservers(String msg) {
         observers.stream().forEach(o->o.update(msg));
    }

    @Override
    public String toString() {
        return "Appointment [ID=" + id + ", Doctor=" + doctor.name + 
            ", Patient=" + patient.name + ", Status=" + appointmentStatus + "]";
    }

    @Override
    public void display() {
       System.out.println(this.toString());
    }

}
