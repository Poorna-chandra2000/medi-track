package meditrack.entity;

public class BillingSummary {

    private final int id;
    private final double total;

    public BillingSummary(int id,double total){
        this.id=id;
        this.total=total;
    }

    public int getId(){
        return id;
    }

    public double getTotal(){
        return total;
    }
    
}
