package Presenter;

import BackwardChaining.BackwardChaining;
import Events.ApplicationEvent;
import Events.ButtonEvent;
import Interafaces.IBackwardChaining;
import Interafaces.ISentence;
import Interafaces.IStrategy;
import Model.Knowledge;
import View.View;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Controller {
    private BlockingQueue<ApplicationEvent> eventQueue;
    private Map<Class<? extends ApplicationEvent>, IStrategy> MapStrategy = new HashMap<>();

    public Controller(BlockingQueue<ApplicationEvent> eventQueue, View view) {
        this.eventQueue = eventQueue;
        MapStrategy.put(ButtonEvent.class, new ButtonStrategy(view));
    }

    public void work() {
        try {
            while (true) {
                ApplicationEvent applicationEvent = eventQueue.take();
                IStrategy iStrategy = MapStrategy.get(applicationEvent.getClass());
                iStrategy.work(applicationEvent);
            }
        } catch (InterruptedException e) {
                e.printStackTrace();
        }
    }

}
