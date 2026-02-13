package meditrack.interfacepkg;

public interface Subject {
     void addObserver(Observer o);
    void notifyObservers(String msg);
}
