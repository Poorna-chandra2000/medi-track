package meditrack.entity;

public abstract class Person extends MedicalEntity {

    protected String name;
    protected int age;

    public Person(int id,String name,int a) {
        this.id=id;
        this.name=name;
        this.age=a;
    }

    public String getName(){
        return name;
    }
}
