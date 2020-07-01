import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Implementation of a BST based String Set.
 * @author
 */
public class BSTStringSet implements SortedStringSet, Iterable<String> {
    /** Creates a new empty set. */
    public BSTStringSet() {
        _root = null;
    }

    @Override
    public void put(String s) {
        _root = putHelper(s, _root);
    }

    public Node putHelper(String s, Node n) {
        if (n == null) {
            return new Node(s);
        }
        if (s.compareTo(n.s) < 0) {
            n.left = putHelper(s, n.left);
        }
        if (s.compareTo(n.s) > 0) {
            n.right = putHelper(s, n.right);
        }
        return n;

    }

    @Override
    public boolean contains(String s) {
        return containsHelper(s, _root);
    }

    public boolean containsHelper(String s, Node n) {
        if (n == null) {
            return false;
        }
        if (s.compareTo(n.s) < 0) {
            return containsHelper(s, n.left);
        }
        if (s.compareTo(n.s) > 0) {
            return containsHelper(s, n.right);
        }
        return true;
    }

    @Override
    public List<String> asList() {

        ArrayList<String> list = new ArrayList<>();
        for (String name : this) {
            list.add(name);
        }

        return list;
    }

    @Override
    public Iterator<String> iterator() {
        return new BSTIterator(_root);
    }

    @Override
    public Iterator<String> iterator(String low, String high) {

        return new Bounded(low, high);
    }

    /** Represents a single Node of the tree. */
    private static class Node {
        /** String stored in this Node. */
        private String s;
        /** Left child of this Node. */
        private Node left;
        /** Right child of this Node. */
        private Node right;

        /** Creates a Node containing SP. */
        Node(String sp) {
            s = sp;
        }
    }

    /** An iterator over BSTs. */
    private static class BSTIterator implements Iterator<String> {
        /** Stack of nodes to be delivered.  The values to be delivered
         *  are (a) the label of the top of the stack, then (b)
         *  the labels of the right child of the top of the stack inorder,
         *  then (c) the nodes in the rest of the stack (i.e., the result
         *  of recursively applying this rule to the result of popping
         *  the stack. */
        private Stack<Node> _toDo = new Stack<>();

        /** A new iterator over the labels in NODE. */
        BSTIterator(Node node) {
            addTree(node);
        }

        @Override
        public boolean hasNext() {
            return !_toDo.empty();
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Node node = _toDo.pop();
            addTree(node.right);
            return node.s;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /** Add the relevant subtrees of the tree rooted at NODE. */
        private void addTree(Node node) {
            while (node != null) {
                _toDo.push(node);
                node = node.left;
            }
        }
    }

    private class Bounded implements Iterator<String> {

        private Stack<Node> todo;
        private Node node;
        private String _high;
        private String _low;

        public Bounded(String low, String high){
            _low = low;
            _high = high;
            todo = new Stack<Node>();
            addTree(_root);


        }
        @Override
        public boolean hasNext() {
            return !todo.empty();
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Node node = todo.pop();
            addTree(node.right);
            return node.s;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private void addTree(Node node) {
            if (node != null) {
                if (node.s.compareTo(_low) < 0) {
                    addTree(node.right);
                }
                else if (node.s.compareTo(_high) >= 0) {
                    addTree(node.left);
                }
                else {
                    todo.push(node);
                    addTree(node.left);
                }

            }

        }
    }

    private Node _root;
}




