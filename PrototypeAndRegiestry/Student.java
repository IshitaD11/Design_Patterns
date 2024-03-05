package Design_Patterns.PrototypeAndRegiestry;

import java.util.Date;

public class Student implements Prototype<Student>{
    int id;
    String name;
    double PSP;
    int age;
    String batchName;
    Date StartDate;

    Student(){
        
    }

    Student(Student s){
        this.id = s.id;
        this.name = s.name;
        this.PSP = s.PSP;
        this.age = s.age;
    }

    @Override
    public Student copy() {
        // Student s = new Student();
        // s.id = this.id;
        // s.name = this.name;
        // s.PSP = this.PSP;
        // s.age = this.age;

        //using copy constructor
        Student s = new Student(this);
        return s;
    }
}
