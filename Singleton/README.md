
**# Singleton Design Pattern**

It allows you to create only 1 object of the class, while providing global access point to this Object. This is useful in the case of controlling access to a shared resource like database connection or a file. Like global variable, singleton pattern lets you access the same object from anywhere in the program, but also protects from being overwritten by other code.

1. Ensure that a class has just a single instance. 
2. Provide a global access point to that instance


**Real Life Example:
The government is an excellent example of the Singleton pattern. A country can have only one official government. Regardless of the personal identities of the individuals who form governments, the title, “The Government of X”, is a global point of access that identifies the group of people in charge.

**Step by Step problem and solutions:
We will use DBConnection class here as example for Singleton Design Pattern. We want only once the DBConnection to get established. Once connection established, it will return the same instance. Do not make multiple connections/instances.

- Base case: class DBConnect

`public class DBConnect {
    DBConnect(){
        established = 1;
    }
}`

`main(){
    DBConnect db = new DBConnect();
}`

- Problem 1: We want only 1 instance of the class. 
Objects gets created by the constructor, so make the constructor private. Create a method getInstance inside DBConnect to pass new DBConnect(). make getInstance static to access it from main method without creating DBConnect instance. 

`public class DBConnect {
    private DBConnect(){
        
    }
    public static DBConnect getInstance(){
        return new DBConnect();
    }
}`

main(){
    DBConnect db = DBConnect.getInstance();
}

- Problem 2: Still we can create multiple object by calling DBConnect.getInstance() multiple times.
Use the DBConnect as private reference variable inside class dbConnect, if the attribute already instantiated return that object instance only, otherwise create new instance. Make DBConnect variable static to access it from getInstance static method.

`public class DBConnect {
    private static DBConnect dbinstance = null;

    private DBConnect(){
    }

    public static DBConnect getInstance(){
        if(dbinstance==null)
            return new DBConnect();
        return dbinstance;
    }
}`

All ref variable of dbconnect pointing to same instance of DBConnect.

`DBConnect db = DBConnect.getInstance();
DBConnect db2 = DBConnect.getInstance();`

- Problem 3: It is not thread safe. If multiple treads comes inside getInstance and check dbinstance variable as null at the same time, all can create different DBConnect objects.

Solve by eager loading of obect. Creating object as soon as class gets loaded.

`public class DBConnect {
    private static DBConnect dbinstance = new DBConnect();

    private DBConnect(){
    }

    public static DBConnect getInstance(){
        return dbinstance;
    }
}`

- Problem 4: It makes the application loadtime very high, as Object is getting created which Application loadtime, when classes gets loaded. Also, if I want to initialize some attributes by the constructor, it is not possible to pass parameter to constructor. 

Using the previous solution of checking if object is instantiated inside getInstance, make getInstance Synchronised/use locks.

`public class DBConnect {
    
    private int established;
    private static DBConnect dbinstance = null;

    private DBConnect(int connect){
        established = connect;
    }

    public static synchronized DBConnect getInstance(int connect){
        if(dbinstance==null)
            return new DBConnect(connect);
        return dbinstance;
    }
}`

`main(){
    DBConnect db2 = DBConnect.getInstance(1);
}`

- Problem 5: After the DBConnection is established/instantiated for the first time, the critical section is not critical anymore, but Synchronised method will not allow multiple threads, making the program slower. For only purpose of controlling the threads for first time instance creation, we are making the whole program wait for 1 thread to pass the method at a time.

Use double check locks technique. It solves the issue of later threads running together. 
getInstance is now Synchronized, as well all once DBConnection intanciated, multiple threads can pass through getInstance method all at once, without any sync issue.

`public class DBConnect {
    private int established;
    private static DBConnect dbinstance = null;
    private static Lock lock = new ReentrantLock();

    private DBConnect(int connect){
        this.established = connect;
    }

    // double check locks
    public static synchronized DBConnect getInstance(int connect){
        
        if(dbinstance==null){
            lock.lock();
            if(dbinstance==null){
                dbinstance = new DBConnect(connect);
            }
            lock.unlock();
        }
        return dbinstance;
    }

}`
