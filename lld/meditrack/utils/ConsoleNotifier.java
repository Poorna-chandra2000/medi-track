package meditrack.utils;

import meditrack.interfacepkg.Observer;

public class ConsoleNotifier implements Observer {

    @Override
    public void update(String msg) {
        // TODO Auto-generated method stub
        System.out.println("You got a notification😊"+msg);
    }
    
}
