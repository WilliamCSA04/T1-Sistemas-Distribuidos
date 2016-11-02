package Client;

import java.util.Date;
import java.util.UUID;

public class Client {

    private volatile boolean status;
    private int userID = 0;
    private String userName;

    public Client() {
        status = false;
    }

    public int registerPlayer(String name) {
        if (this.status) {
            return -1;
        }
        this.userName = name;
        this.userID = geraID();
        this.status = true;

        return userID;
    }

    private int geraID() {
        Date date = new Date();
        UUID myID;
        do {
            myID = UUID.randomUUID();
        } while (myID.hashCode() == -1 || myID.hashCode() == -2);

        return myID.hashCode();
    }
    
    public String getUserName(){
        return userName;
    }

}
