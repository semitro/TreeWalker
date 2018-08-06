package smt.observer;

@FunctionalInterface
public interface Observable {
    void addListener(Observer listener);
}
