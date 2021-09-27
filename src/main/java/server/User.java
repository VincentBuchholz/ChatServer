package server;

public class User {
    private String name;
    private boolean isOnline;

    public User(String name){
        this.name = name;
        isOnline = false;
    }

    public void setIsOnline(boolean isOnline){
        this.isOnline = isOnline;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public String getName() {
        return name;
    }
}
