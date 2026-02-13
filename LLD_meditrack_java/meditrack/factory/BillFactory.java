package meditrack.factory;

import meditrack.entity.Bill;

public class BillFactory {
    public static Bill Create(double fee){
        return new Bill(fee);
    }
}
