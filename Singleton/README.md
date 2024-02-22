
# Singleton Design Pattern # 

It allows you to create only 1 object of the class while providing a global access point to this Object. This is useful in the case of controlling access to a shared resource like a database connection or a file. Like a global variable, a singleton pattern lets you access the same object from anywhere in the program but also protects it from being overwritten by other code.

1. Ensure that a class has just a single instance. 
2. Provide a global access point to that instance


## Real Life Example:
The government is an excellent example of the Singleton pattern. A country can have only one official government. Regardless of the personal identities of the individuals who form governments, the title, “The Government of X”, is a global point of access that identifies the group of people in charge.

### Step by Step problem and solutions:
We will use the DBConnection class here as an example for the Singleton Design Pattern. We want only once the DBConnection is established. Once the connection is established, it will return the same instance. Do not make multiple connections/instances.

- Base case: class DBConnect


```java
public class DBConnect{  
    DBConnect(){
        established = 1;
    }  
}
```

```java
main(){  
    DBConnect db = new DBConnect();  
}
```

- Problem 1: We want only one instance of the class. 
Objects get created by the constructor, so make the constructor private. Create a method getInstance inside DBConnect to pass new DBConnect(). make getInstance static to access it from the main method without creating a DBConnect instance. 

```java
public class DBConnect{  
    private DBConnect(){    }  
    public static DBConnect getInstance(){  
        return new DBConnect();  
    }  
}
```

```java
main(){  
    DBConnect db = DBConnect.getInstance();  
}
```

- Problem 2: Still we can create multiple objects by calling DBConnect.getInstance() multiple times.
Use the DBConnect as a private reference variable inside class dbConnect, if the attribute is already instantiated return that object instance only, otherwise create a new instance. Make the DBConnect variable static to access it from the getInstance static method.

```java
public class DBConnect {
    private static DBConnect dbinstance = null;

    private DBConnect(){
    }

    public static DBConnect getInstance(){
        if(dbinstance==null)
            return new DBConnect();
        return dbinstance;
    }
}
```

All ref variables of dbconnect point to the same instance of DBConnect.

```java
DBConnect db = DBConnect.getInstance();
DBConnect db2 = DBConnect.getInstance();
```

- Problem 3: It is not thread-safe. If multiple threads come inside getInstance and check dbinstance variable as null at the same time, all can create different DBConnect objects.

Solve by eager loading of the object. We are creating the object as soon as the class gets loaded.

```java
public class DBConnect {
    private static DBConnect dbinstance = new DBConnect();

    private DBConnect(){
    }

    public static DBConnect getInstance(){
        return dbinstance;
    }
}
```

- Problem 4: It makes the application load time very high, as an Object is getting created which is the Application load time, when classes get loaded. Also, if I want to initialize some attributes by the constructor, it is not possible to pass any parameter to the constructor. 

Using the previous solution of checking if the object is instantiated inside getInstance, make getInstance Synchronised/use locks.

```java
public class DBConnect {
    
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
}
```

```java
main(){
    DBConnect db2 = DBConnect.getInstance(1);
}
```

- Problem 5: After the DBConnection is established/instantiated for the first time, the critical section is not critical anymore, but the Synchronised method will not allow multiple threads, making the program slower. For only purpose of controlling the threads for first-time instance creation, we are making the whole program wait for 1 thread to pass the method at a time.

Use **the double check locks technique**. It solves the issue of later threads running together. 
getInstance is now Synchronized, as well once DBConnection is instantiated, multiple threads can pass through the getInstance method all at once, without any sync issue.

```java
public class DBConnect {
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

}
```


#### Disadvantages:
- Violates the Single Responsibility Principle. The pattern solves two problems at the time.
- The Singleton pattern can mask bad design, for instance, when the components of the program know too much about each other.
- The pattern requires special treatment in a multithreaded environment so that multiple threads won’t create a singleton object several times.
- It may be difficult to unit test the client code of the Singleton because many test frameworks rely on inheritance when producing mock objects. Since the constructor of the singleton class is private and overriding static methods is impossible in most languages, you will need to think of a creative way to mock the singleton. Or just don’t write the tests. Or don’t use the Singleton pattern.
