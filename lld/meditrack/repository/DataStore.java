package meditrack.repository;

import java.util.*;

//use generics to store any type
//repository like
public class DataStore<T> {

    private Map<Integer,T> map=new HashMap<>();
    public void add(int id,T t){
        map.put(id, t);
    } 

    public T get(int id){
        return map.get(id);
    }

    public Collection<T> all(){
        return map.values();
    }

    public boolean exists(int id){
        return map.containsKey(id);
    }
    
}
