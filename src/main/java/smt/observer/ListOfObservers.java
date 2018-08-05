package smt.observer;

import java.util.ArrayList;

public class ListOfObservers extends ArrayList<Observer>{
    public void noticeAll(Message message){
        this.forEach(listener->listener.notice(message));
    }
}
