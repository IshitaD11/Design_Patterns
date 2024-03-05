package Design_Patterns.PrototypeAndRegiestry;

public interface Prototype <E> {

    // returns a generic object, so all type of objects which implements Prototype interface can return that type of object
    public E copy();
    
}
