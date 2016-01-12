package Presenter;

import View.View;
import Events.ApplicationEvent;
import Events.ButtonEvent;
import Interafaces.IBackwardChaining;
import Interafaces.ISentence;
import Interafaces.IStrategy;
import Model.Knowledge;
import BackwardChaining.BackwardChaining;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;

public class ButtonStrategy implements IStrategy {

    private final View view;
    private IBackwardChaining backwardChaining = new BackwardChaining();

    public ButtonStrategy(View view) {
        this.view = view;
    }

    @Override
    public void work(ApplicationEvent applicationEvent) {
        String dataString = ((ButtonEvent) applicationEvent).getDataString();
        Knowledge knowledge = new Knowledge(dataString);
        ISentence result = backwardChaining.deduce(knowledge).get();
        addNode(result, null);
    }

    private void addNode(ISentence currentNode, DefaultMutableTreeNode parent) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(currentNode.getSentence().get());
        view.addChild(parent, node);
        List<ISentence> sentenceList = currentNode.getAntecedents().get();
        sentenceList.stream()
                .forEach(s -> addNode(s, node));
    }
}
