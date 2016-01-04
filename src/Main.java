import Events.ApplicationEvent;
import Presenter.Controler;

import View.View;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args)
    {
        BlockingQueue<ApplicationEvent> eventQueue = new LinkedBlockingQueue<>();
        View view= new View(eventQueue);
        Controler contr=new Controler(eventQueue,view);
        contr.work();
    }
}
