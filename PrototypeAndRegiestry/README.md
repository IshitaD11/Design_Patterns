
# Prototype and Registry Design Pattern # 

Prototype and registry design pattern allows you to make copy of existing objects without making your code dependent on their code. Prototype means model of something. So the model i.e. object of a class is given, we need to create new objects which will be copy of the given object.

How to copy from an object?
- Shallow Copy
    ```java
    Student s1 = new Student();
    Student s2 = s1;
    ```
- Deep Copy
    ```java
    Student s1 = new Student();
    Student s2 = new Student();
    s2.name = s1.name;
    s2.age = s1.age;
    ... so on
    ```

### Problem:
1. Some of the attribute of Student might be private, not visible in outside than the class
2. The client need to know all configuration/attributes of Student to be able to make proper copy, to make sure no attributes are missed.
3. To copy multiple objects need duplicate code lines, not reusable. 
4. Lets say There is class Student and class IntelligentStudent extends Student, now we have a method to create corresponding object. This method get both Student and IntelligentStudent type object.
The below code is breaking Single Reponsibilty principle and Open close principle.

    ```java
    Student createCopyObject(Student s){
        if(s is instance of Student)
            return new Student();
        else if(s is instance of IntelligentStudent)
            return new IntelligentStudent();
    }
    ```

### Solution: Prototype Design Pattern
Prototype Design pattern lets Outsource the creation of copy to the object itself, so that Object has the power to copy itself. Declare a common interface for all object that supports cloning, it will have the single copy()/clone() method. Objective is to clone an object without coupling your code to the class of the object. The copy()/clone() method creates an object of current class, assignes all attributes of this to the new object, and returns the new object. 

### Real Life Example:
In real life, prototypes are used for performing various tests before starting mass production of a product. However, in this case, prototypes don’t participate in any actual production, playing a passive role instead. Sometimes used in games, UI components where lots of copies are required.\

Suppose we have a notebook production, where multiple notebooks having same noOfPages, size, width is there, but we want different covers. Copy the noOfPages, size, width for all the objects, have different covers only.

### Registry:
There might be many combinations of prototypes, and we want to store the setups those combinations. The storing process is called registry. These are objects of 1 class, but different class has different preset attribute values. \

Suppose we have Student class and multiple batches. Each batches have some set of configuration like batchname and batch start date. We can use a separate class called Registry having hashMap to store the configuration of each batch. \
Map will have a key to indentify each setup, and values will be Student. HashMap<String,Student> can be used. \
The Registry class will have two methods, add into registry and get object from registry.


### Code: Prototype

1. There is a interface prototype, which will have the copy method. It returns a generic type, so all classes implementing this interface can return that classes objects. The classes might of might not be related, so keeping Generic type can return all type of objects.

    ```Java
    public interface Prototype <E> {

        // returns a generic object, so all type of objects which implements Prototype interface can return that type of object
        public E copy();
        
    }
    ```

2. class Student implements prototype will implement the copy method. It will create a new object of own class, and assign all attribute to this.attribute

    ```java
    public class Student implements Prototype<Student>{
        int id;
        String name;
        double PSP;
        int age;

        @Override
        public Student copy() {
            Student s = new Student();
            s.id = this.id;
            s.name = this.name;
            s.PSP = this.PSP;
            s.age = this.age;
            return s;
        }
    }```

3. In main class create object by using another object.

    ```Java
        Student s2 = s1.copy();
    ```

### Code: Registry

4. Create class Registry having a HashMap<String,Student>. Also have two methods, add into hashMap and get object from hashmap using the key.

    ```Java
    public class StudentRegistry {
    
        private Map<String,Student> registry = new HashMap<>();

        public void add(String name,Student s){
            registry.put(name,s);
        }

        public Student get(String name){
            return registry.get(name);
        }
    }```

5. Fill the registry object as per requirements.

    ```java
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
    }```

6. In Main create Student object by 
    ```java
    Student s3 = studentRegistry.get("Apr23Batch");```

7. If we want to protect the registry's Student objects we can directly call the copy() while returning in the get method of registry.

    ```java
    public Student get(String name){
        // returns a new copy object
        return registry.get(name).copy();
    }```

8. Use copy constructor

    ```java
    public Student copy() {
        Student s = new Student(this);
        return s;
    }```

9. Runtime Polymorphism:
    We have a method to copy object like below. It will allow both IntelligentStudent and Student object. Based on the object corresponding copy() method will get called

    ```Java
    public static Student copyObject(Student s){
        return s.copy();
    }```


### Applicability
1. Use the Prototype pattern when your code shouldn’t depend on the concrete classes of objects that you need to copy.

This happens a lot when your code works with objects passed to you from 3rd-party code via some interface. The concrete classes of these objects are unknown, and you couldn’t depend on them even if you wanted to.

The Prototype pattern provides the client code with a general interface for working with all objects that support cloning. This interface makes the client code independent from the concrete classes of objects that it clones.

2.  Use the pattern when you want to reduce the number of subclasses that only differ in the way they initialize their respective objects.

Suppose you have a complex class that requires a laborious configuration before it can be used. There are several common ways to configure this class, and this code is scattered through your app. To reduce the duplication, you create several subclasses and put every common configuration code into their constructors. You solved the duplication problem, but now you have lots of dummy subclasses.

The Registry pattern lets you use a set of pre-built objects configured in various ways as prototypes. Instead of instantiating a subclass that matches some configuration, the client can simply look for an appropriate prototype and clone it.


### Cons:

Cloning complex objects that have circular references might be very tricky.