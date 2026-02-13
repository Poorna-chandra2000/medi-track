package meditrack.entity;

public abstract class MedicalEntity {
    protected int id;
    public int getId(){
        return id;
    }
    
    public abstract void display();
}
