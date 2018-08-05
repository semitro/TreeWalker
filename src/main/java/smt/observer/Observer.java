package smt.observer;

@FunctionalInterface
public interface Observer {
    void notice(Message message);
}
