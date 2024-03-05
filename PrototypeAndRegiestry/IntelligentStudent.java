package Design_Patterns.PrototypeAndRegiestry;

public class IntelligentStudent extends Student{
    int IQ;

    IntelligentStudent(){

    }
    
    IntelligentStudent(IntelligentStudent is){
        super(is);
        this.IQ=is.IQ;
    }
    
    @Override
    public IntelligentStudent copy() {
        // IntelligentStudent is = new IntelligentStudent();
        // is.name = this.name;
        // is.PSP = this.PSP;
        // is.age = this.age;
        // is.id = this.id;
        // is.IQ = this.IQ;

        //using copy constructor
        IntelligentStudent is = new IntelligentStudent(this);
        return is;
    }
}
