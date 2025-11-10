package util;

public class IdGenerator {
    private int currentId =1;
    public int nextId() {
        return currentId++;
    }
}
