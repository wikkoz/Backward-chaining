package Presenter;

import Interafaces.IApplicationEvent;
import Events.ButtonEvent;
import Interafaces.IStrategy;
import View.View;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Controller {
    private BlockingQueue<IApplicationEvent> eventQueue;
    private Map<Class<? extends IApplicationEvent>, IStrategy> MapStrategy;

    public Controller(BlockingQueue<IApplicationEvent> eventQueue, View view) {
        this.eventQueue = eventQueue;
        this.MapStrategy = new HashMap<>();
        this.MapStrategy.put(ButtonEvent.class, new ButtonStrategy(view));
    }

    public void work() {
        try {
            while (true) {
                IApplicationEvent applicationEvent = eventQueue.take();
                IStrategy strategy = MapStrategy.get(applicationEvent.getClass());
                strategy.work(applicationEvent);
            }
        } catch (InterruptedException e) {
                e.printStackTrace();
        }
    }

}
