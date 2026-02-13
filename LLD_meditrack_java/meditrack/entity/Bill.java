package meditrack.entity;

import meditrack.constants.Constants;
import meditrack.interfacepkg.Payable;

public class Bill implements Payable{

    private double base;//this is base amount consulting fee

    public Bill(double base){
        this.base=base;
    }

    @Override
    public double calculateTotal() {
       return base+base*Constants.TAX_RATE;
    }
    
}
