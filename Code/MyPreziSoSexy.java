import java.util.*;
import javafx.scene.layout.Pane;
import javafx.scene.control.SplitPane;

public class MyPreziSoSexy {
    private LinkedList<MyNode> myNodes = new LinkedList<MyNode>();
    private MyNode currMyNode;

    private Pane pane;

    public MyPreziSoSexy(Pane p) {
        this.pane = p;
        myNodes.push(new MyNode(pane));
        currMyNode = myNodes.getFirst();
        currMyNode.prev = -1;
        currMyNode.curr = 0;
    }

    public void addChildMyNode() {
        myNodes.push(new MyNode(new Pane()));

        currMyNode.next[currMyNode.nextLen] = myNodes.size() - 1;// set currMyNode's next Mynode
        currMyNode.nextLen++;

        myNodes.getLast().curr = myNodes.size() - 1;// set nextMyNode's curr
        myNodes.getLast().prev = currMyNode.curr;// set nextMyNode's prev Mynode

        currMyNode = myNodes.getLast();
    }

    public void addParentMyNode() {
        myNodes.push(new MyNode(new Pane()));
        MyNode p = myNodes.get(currMyNode.prev);

        for (int i = 0; i < p.nextLen; i++) {
            if (p.next[i] == currMyNode.curr) {
                p.next[i] = myNodes.size() - 1;
                break;
            }
        }
        currMyNode.prev = myNodes.size() - 1;

        myNodes.getLast().curr = myNodes.size() - 1;
        myNodes.getLast().prev = p.curr;
        myNodes.getLast().next[0] = currMyNode.curr;

        currMyNode = myNodes.getLast();
    }

    public void addMyNode() {
        myNodes.push(new MyNode(new Pane()));
        MyNode p = myNodes.get(currMyNode.prev);

        p.next[currMyNode.nextLen] = myNodes.size() - 1;// set currMyNode's prevMyNode's next Mynode
        p.nextLen++;

        myNodes.getLast().curr = myNodes.size() - 1;// set prevMyNode's nextMyNode's curr
        myNodes.getLast().prev = p.curr;// set prevMyNode's nextMyNode's prev Mynode

        currMyNode = myNodes.getLast();

    }

    public void gotoMyNode(int n) {

    }

    public boolean isHead() {
        if (currMyNode.prev < 0)
            return true;
        return false;
    }

    public MyNode currentMyNode() {
        return currMyNode;
    }

}