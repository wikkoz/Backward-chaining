import Events.ApplicationEvent;
import Presenter.Controller;
import View.View;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) {
        BlockingQueue<ApplicationEvent> eventQueue = new LinkedBlockingQueue<>();
        View view = new View(eventQueue);
        Controller controller = new Controller(eventQueue, view);
        controller.work();
    }
}
