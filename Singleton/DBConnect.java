package Design_Patterns.Singleton;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// only 1 object of this class is allowed
public class DBConnect {
    
    private int established = 0;   
    // private static DBConnect dbinstance = new DBConnect();
    private static DBConnect dbinstance = null;
    private static Lock lock = new ReentrantLock();

    private DBConnect(int connect){
        this.established = connect;
    }

    // public static synchronized DBConnect getInstance(int connect){
    //     if(dbinstance==null)
    //         dbinstance = new DBConnect(connect);
    //     return dbinstance;
    // }

    // double check locks
    public static DBConnect getInstance(int connect){
        
        if(dbinstance==null){
            lock.lock();
            if(dbinstance==null){
                dbinstance = new DBConnect(connect);
            }
            lock.unlock();
        }
        return dbinstance;
    }

    public boolean isConnected(){
        return established==1;
    }

}
