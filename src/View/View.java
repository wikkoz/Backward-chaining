package View;

import Events.ApplicationEvent;
import Events.ButtonEvent;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.concurrent.BlockingQueue;

public class View
{

	private JFrame frame;
	private JTree tree;
	private JTextPane textPane;
	private JButton btnNewButton;
    private DefaultMutableTreeNode root;
    DefaultTreeModel treeModel;
	
	/**
	 * Create the application.
	 */
	public View(final BlockingQueue<ApplicationEvent> eventQueue)
	{
		initialize(eventQueue);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final BlockingQueue<ApplicationEvent> eventQueue)
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 819, 545);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		root = new DefaultMutableTreeNode("Root");
		treeModel= new DefaultTreeModel(root);
		tree = new JTree(treeModel);
		tree.setBounds(425, 34, 345, 389);
		frame.getContentPane().add(tree);
		
		textPane = new JTextPane();
		textPane.setBounds(24, 34, 364, 389);
		frame.getContentPane().add(textPane);

		btnNewButton = new JButton("GO");
		btnNewButton.addActionListener(a-> eventQueue.offer(new ButtonEvent(textPane.getText())));

		btnNewButton.setBounds(24, 434, 364, 61);
		frame.getContentPane().add(btnNewButton);
		frame.setVisible(true);
	}

	public DefaultMutableTreeNode addChild(DefaultMutableTreeNode parent, DefaultMutableTreeNode child)
	{
		if (parent == null) {
			root = child;
			treeModel.setRoot(root);
			return root;
		}
		treeModel.insertNodeInto(child,parent, parent.getChildCount());
		return child;
	}

}
