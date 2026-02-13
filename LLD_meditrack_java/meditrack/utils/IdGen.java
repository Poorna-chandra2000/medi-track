package meditrack.utils;

//singleton
public class IdGen {
    private int id=1;
    private static IdGen instance;
    public IdGen(){}

    public static IdGen getInstance(){

        if(instance!=null){
            return instance;
        }

        instance=new IdGen();
        return instance;
    }

    public synchronized int nextId(){
        return id++;
    }
}


// package meditrack.utils;

// // 1. YOUR STATIC EXPERIMENT CLASS
// class StaticGen {
//     // Shared by all instances of StaticGen
//     static int id = 1; 

//     public StaticGen() {
//         // Constructor is empty as per your request
//     }

//     public int nextId() {
//         // Post-increment: returns CURRENT value, then adds 1
//         return id++; 
//     }
// }

// // 2. YOUR ORIGINAL SINGLETON CLASS
// class IdGen { 
//     private static final IdGen i = new IdGen();
//     private int id = 1; // Instance variable, but only one 'i' exists
    
//     private IdGen() {}

//     public static IdGen getInstance() {
//         return i;
//     }

//     public synchronized int nextId() {
//         return id++;
//     }
// }

// 3. THE TESTER
// public class Main {
//     public static void main(String[] args) {
//         System.out.println("--- StaticGen Test ---");
//         StaticGen g1 = new StaticGen();
//         System.out.println("g1 call 1: " + g1.nextId()); // Prints 1, id becomes 2
//         System.out.println("g1 call 2: " + g1.nextId()); // Prints 2, id becomes 3

//         StaticGen g2 = new StaticGen();
//         System.out.println("g2 call 1: " + g2.nextId()); // Prints 3 (Shared!)
        
//         System.out.println("\n--- Singleton Test ---");
//         // IdGen gen = new IdGen(); // ERROR: Private constructor prevents this
//         IdGen singleton = IdGen.getInstance();
//         System.out.println("Singleton 1: " + singleton.nextId()); // Prints 1
//         System.out.println("Singleton 2: " + singleton.nextId()); // Prints 2
//     }
// }

