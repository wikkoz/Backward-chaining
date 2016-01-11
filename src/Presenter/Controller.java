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
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Controller {
    private BlockingQueue<ApplicationEvent> eventQueue;
    private View view;
    private Map<Class<? extends ApplicationEvent>, AppStrategy> MapStrategy;
    private IBackwardChaining backwardChaining = new BackwardChaining();

    public Controller(BlockingQueue<ApplicationEvent> eventQueue, View view) {
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

    interface AppStrategy {
        void work(ApplicationEvent applicationEvent);
    }

    class ButtonStrategy implements AppStrategy {

        @Override
        public void work(ApplicationEvent applicationEvent) {
            String dataString = ((ButtonEvent) applicationEvent).getDataString();
            Knowledge knowledge = new Knowledge(dataString);
            ISentence result = backwardChaining.deduce(knowledge);
            addNode(result, null);
        }

        private void addNode(ISentence currentNode, DefaultMutableTreeNode parent) {
            String sentence = currentNode.getSentence();
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(sentence);
            view.addChild(parent, node);
            currentNode.getAntecedents().stream()
                        .forEach(s -> addNode(s, node));
        }
    }

}
