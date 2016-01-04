package Presenter;

import BackwardChaining.BackwardChaining;
import Events.ApplicationEvent;
import Events.ButtonEvent;
import Interafaces.IBackwardChaining;
import Interafaces.ISentence;
import Model.Knowledge;
import View.View;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Controller {
    private BlockingQueue<ApplicationEvent> eventQueue;
    private View view;
    private Map<Class<? extends ApplicationEvent>, AppStrategy> MapStrategy;
    private IBackwardChaining backwardChaining = new BackwardChaining();

    public Controller(BlockingQueue<ApplicationEvent> eventQueue, View view) {
        super();
        this.eventQueue = eventQueue;
        this.view = view;
        MapStrategy = new HashMap<>();
        MapStrategy.put(ButtonEvent.class, new ButtonStrategy());
    }

    public void work() {
        while (true) {
            try {
                ApplicationEvent applicationEvent = eventQueue.take();
                AppStrategy appStrategy = MapStrategy.get(applicationEvent.getClass());
                appStrategy.work(applicationEvent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    abstract class AppStrategy {
        abstract void work(ApplicationEvent applicationEvent);
    }

    class ButtonStrategy extends AppStrategy {

        @Override
        void work(ApplicationEvent applicationEvent) {
            System.out.println(((ButtonEvent) applicationEvent).getDataString());
            String dataString = ((ButtonEvent) applicationEvent).getDataString();
            Knowledge knowledge = new Knowledge(dataString);
            ISentence result = backwardChaining.deduce(knowledge);
            buildJtree(result);
        }

        private void buildJtree(ISentence results) {
            addNode(results, null);
        }

        private void addNode(ISentence currentNode, DefaultMutableTreeNode parent) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(currentNode.getSentence());
            view.addChild(parent, node);
            for (ISentence child : currentNode.getAntecedents()) {
                addNode(child, node);
            }
        }
    }

}
