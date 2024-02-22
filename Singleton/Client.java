package ScalerLLD.Design_Patterns.Singleton;

public class Client {
    public static void main(String[] args) {

        // base case: Constructor can make multiple objects
        // DBConnect db = new DBConnect();
        // DBConnect db2 = new DBConnect();
        // System.out.println(db.established);

        // create constructor private. As private constructor is not accessed outside dbconnect. create method getInstance.
        // to access getInstance without creating DBConnect object, make it static
        // DBConnect db = DBConnect.getInstance();
        // DBConnect db2 = DBConnect.getInstance();

        // To resolve creating multiple object, have the class as private attribute inside class dbConnect, 
        // if the attribute already instantiated return the attribute only, otherwise create new instance
        // all ref variable of dbconnect pointing to same address
        // DBConnect db = DBConnect.getInstance();
        // DBConnect db2 = DBConnect.getInstance();

        // made constructor parameterised
        DBConnect db2 = DBConnect.getInstance(1);
        DBConnect db3 = DBConnect.getInstance(1);
    }

}
