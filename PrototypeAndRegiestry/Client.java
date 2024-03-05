package Design_Patterns.PrototypeAndRegiestry;

public class Client {

    public static Student copyObject(Student s){
        return s.copy();
    }
    
    public static void main(String[] args) {
        
        Student s1 = new Student();
        s1.name = "Ishita";
        s1.age = 28;
        s1.PSP = 98;
        s1.id = 2;

         // coping class in Client
        // // shallow copy -- both s1 and s2 point to same address 
        // Student s2=s1; 

        // copy using prototype
        Student s2 = s1.copy();

        // using Registry create copy object
        StudentRegistry studentRegistry = new StudentRegistry();
        studentRegistry.fillRegistry();
        // Student s3 = studentRegistry.get("Apr23Batch").copy();

        // If we want to restrict the registry object directly get a new copy object
        Student s3 = studentRegistry.get("Apr23Batch");

        
        IntelligentStudent is = new IntelligentStudent();
        is.name = "Ankita";
        is.age = 30;
        is.PSP = 100;
        is.id = 1;
        is.IQ = 100;
        IntelligentStudent is1 = is.copy();

        // runtime polymorphism
        copyObject(is1);
        copyObject(s3);
    }
}
