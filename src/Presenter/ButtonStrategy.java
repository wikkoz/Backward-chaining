package Presenter;

import View.View;
import Interafaces.IApplicationEvent;
import Interafaces.IBackwardChaining;
import Interafaces.ISentence;
import Interafaces.IStrategy;
import Model.Knowledge;
import BackwardChaining.BackwardChaining;

import javax.swing.tree.DefaultMutableTreeNode;

public class ButtonStrategy implements IStrategy {

    private final View view;
    private IBackwardChaining backwardChaining;

    public ButtonStrategy(View view) {
        this.view = view;
        this.backwardChaining = new BackwardChaining();
    }

    @Override
    public void work(IApplicationEvent applicationEvent) {
        String dataString = applicationEvent.getDataString();
        Knowledge knowledge = new Knowledge(dataString);
        ISentence result = backwardChaining.deduce(knowledge);
        addNode(result, null);
    }

    private void addNode(ISentence currentNode, DefaultMutableTreeNode parent) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(currentNode.getSentence());
        view.addChild(parent, node);
        for (ISentence child : currentNode.getAntecedents()) {
            addNode(child, node);
        }
    }
}
