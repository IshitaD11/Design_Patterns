package Design_Patterns.PrototypeAndRegiestry;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class StudentRegistry {
    
    private Map<String,Student> registry = new HashMap<>();
    Calendar calendar = Calendar.getInstance();

    public void add(String name,Student s){
        registry.put(name,s);
    }

    public Student get(String name){
        // returns a new copy object
        return registry.get(name).copy();
    }

    public void fillRegistry(){
        Student Apr23 = new Student();
        Apr23.batchName = "April 23 Batch";
        calendar.set(2023, 4, 3 );
        Apr23.StartDate = calendar.getTime();
        this.add("Apr23Batch", Apr23);

        Student Aug23 = new Student();
        Apr23.batchName = "August 23 Batch";
        calendar.set(2023, 8, 10 );
        Apr23.StartDate = calendar.getTime();
        this.add("Aug23Batch", Aug23);
    }
}
