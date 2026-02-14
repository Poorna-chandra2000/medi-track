package meditrack.entity;

import meditrack.utils.Validator;

public class Patient extends Person implements Cloneable {


    private String illness;

    public Patient(int id, String name, int a,String illness) {
        super(id, name, a);
        Validator.name(name);
        Validator.age(a);
        //TODO Auto-generated constructor stub
        this.illness=illness;
    }

    public Patient clone(){
        return new Patient(id, name, age, illness);
    }

    public String toCSV(){
        return id+","+name+","+age+illness;
    }


    public static Patient fromCSV(String[] p){
        return new Patient(Integer.parseInt(p[0]),p[1],Integer.parseInt(p[2]),p[3]);
    } 

    @Override
    public void display() {
        // TODO Auto-generated method stub
       System.out.println("Patient "+id+" "+name+" "+illness);
    }
    
}
