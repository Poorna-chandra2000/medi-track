package meditrack;

import java.util.Scanner;

import meditrack.constants.Specialization;
import meditrack.entity.Appointment;
import meditrack.entity.Bill;
import meditrack.entity.BillingSummary;
import meditrack.entity.Doctor;
import meditrack.entity.Patient;
import meditrack.factory.BillFactory;
import meditrack.payment.PaymentStrategy;
import meditrack.payment.UpiPayment;
import meditrack.repository.DataStore;
import meditrack.service.AppointmentService;
import meditrack.service.DoctorService;
import meditrack.service.PatientService;
import meditrack.utils.ConsoleNotifier;
import meditrack.utils.IdGen;

public class Main {


    public static void main(String[] args) {
        
        DoctorService ds=new DoctorService();
        PatientService ps=new PatientService();
        AppointmentService as=new AppointmentService();
        

        //load id generated once
        IdGen gen=IdGen.getInstance();


        Scanner s=new Scanner(System.in);
       
        for(;;){
            System.out.println("""
                    1.Add Doc
                    2.Add patient
                    3.Book Appointment
                    4.List Appointment
                    5.Generate Bill
                    6.Save CSV
                    7.Exit
                    """);

            int c=s.nextInt();
            try {
                switch (c) {
                    case 1 -> {
                        ds.add(new Doctor(gen.nextId(),"Doc"+gen.nextId(),40,Specialization.CARDIOLOGY, 800));
                        ds.list();
                    }
                    
                    case 2 -> {ps.add(new Patient(gen.nextId(),"Patient"+gen.nextId(), 30, "Fever"));
                        ps.list();
                    }

                    case 3 -> {
                              System.out.println("DocID:");
                              int did=s.nextInt();
                              System.out.println("PatientID:");
                              int pid=s.nextInt();
                              Appointment a=as.create(gen.nextId(), ds.get(did), ps.get(pid));
                              a.addObserver(new ConsoleNotifier());}
                    
                    case 4 -> as.list();

                    case 5 -> {
                        System.out.println("ApptID:");
                        int aid=s.nextInt();
                        Appointment a=as.get(aid);

                        Bill b=BillFactory.Create(a.getDoctor().getfee());
                        double total=b.calculateTotal();

                        PaymentStrategy pay=new UpiPayment();
                        pay.pay(total);
                        a.confirm();
                        BillingSummary billingSummary=new BillingSummary(aid, total);
                        System.out.println("BillSummary "+billingSummary.getTotal());
                    }

                    case 6 -> ps.saveCSV("patient.csv");

                    case 7 -> System.exit(0);

            
                }
            } catch (Exception e) {
                System.out.println("Error: "+e.getMessage());
            }
        }
    }
   


}
