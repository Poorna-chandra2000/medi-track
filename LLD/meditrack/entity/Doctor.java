package meditrack.entity;

import meditrack.utils.Validator;

import meditrack.constants.Specialization;

public class Doctor extends Person {


    private Specialization spec;
    private double fee;

    public Doctor(int id, String name, int a,Specialization s,double fee) {
        super(id, name, a);
        Validator.name(name);
        Validator.age(a);
        spec=s;
        this.fee=fee;
    }

    public double getfee(){
        return fee;
    }

    @Override
    public void display() {
      System.out.println("Doctor "+id+" "+name+" "+spec+" fee"+fee);
    }
    
}
