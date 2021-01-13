import java.util.*;

public class Tree implements Set {

    private Node root;
    private int size = 0;

    public Node getRoot(){
        return  this.root;
    }

    public Node find(int key) {
        if (root == null) throw new IllegalArgumentException();
        return findNodeR(root, key);
    }

    private Node findNodeR(Node node, int key) {
        if (node == null) return null;
        if (node.key == key) {
            splay(node);
            return node;
        }
        return key < node.key
                ?findNodeR(node.left, key)
                :findNodeR(node.right, key);
    }


    public void insert(int key) {
        Node node = new Node(key);
        if (root == null) {
            root = node;
            size++;
            return;
        }
        Node parent = insertR(root, key);
        if (key < parent.key)
            parent.left = node;
        else
            parent.right = node;
        node.parent = parent;
        splay(node);
        size++;
    }

    private Node insertR(Node node, int key) {
        if (
                (node.left == null && key < node.key) ||
                (node.right == null && key > node.key)
        ) return node;
        return key < node.key
                ?insertR(node.left, key)
                :insertR(node.right, key);
    }

    private void leftChildToParent(Node node, Node parent){
        if (parent.parent != null) {
            if (parent == parent.parent.left)
                parent.parent.left = node;
            else parent.parent.right = node;
        }
        if (node.right != null)
            node.right.parent = parent;

        node.parent = parent.parent;
        parent.parent = node;
        parent.left = node.right;
        node.right = parent;
    }

    private void rightChildToParent(Node node, Node parent){
        if (parent.parent != null) {
            if (parent == parent.parent.left)
                parent.parent.left = node;
            else parent.parent.right = node;
        }
        if (node.left != null)
            node.left.parent = parent;

        node.parent = parent.parent;
        parent.parent = node;
        parent.right = node.left;
        node.left = parent;
    }

    private void splay(Node node) {
        while (node.parent != null) {
            Node parent = node.parent;
            Node grandParent = node.parent.parent;
            if (grandParent == null) { // zig
                if (node == parent.left)
                    leftChildToParent(node, parent);
                else
                    rightChildToParent(node, parent);
            }
            else {
                if (node == parent.left) {

                    if (parent == grandParent.left) { // zig-zig
                        leftChildToParent(parent, grandParent);
                        leftChildToParent(node, parent);
                    } else {                            // zig-zag
                        leftChildToParent(node, node.parent);
                        rightChildToParent(node, node.parent);
                    }
                }
                else {

                    if (parent == grandParent.left) { // zig-zag
                        rightChildToParent(node, node.parent);
                        leftChildToParent(node, node.parent);
                    } else {                            // zig-zig
                        rightChildToParent(parent, grandParent);
                        rightChildToParent(node, parent);
                    }
                }
            }
        }
        root = node;
    }

    private void merge(Node left, Node right) {
        Node max = left;
        while(max.right != null) {
            max = max.right;
        }
        splay(max);
        max.right = right;
        right.parent = max;
        root = max;
    }

    public void remove(Node node) {
        if (find(node.key) == null)
            throw new IllegalArgumentException();
        Node left = node.left;
        Node right = node.right;
        node.right.parent = null;
        node.left.parent = null;
        node.key = null;
        node.parent = null;
        node.left = null;
        node.right = null;
        merge(left, right);
        size--;
    }


    protected Node findWOSplay(int key) {
        if (root == null) throw new IllegalArgumentException();
        return findNodeRWO(root, key);
    }

    private Node findNodeRWO(Node node, int key) {
        if (node == null) return null;
        if (node.key == key) {
            return node;
        }
        return key < node.key
                ?findNodeRWO(node.left, key)
                :findNodeRWO(node.right, key);
    }

    protected void insertWOSplay(int key) {
        Node node = new Node(key);
        if (root == null) {
            root = node;
            return;
        }
        Node parent = insertRWO(root, key);
        if (key < parent.key)
            parent.left = node;
        else
            parent.right = node;
        node.parent = parent;
    }

    private Node insertRWO(Node node, int key) {
        if (
                (node.left == null && key < node.key) ||
                        (node.right == null && key > node.key)
        ) return node;
        return key < node.key
                ?insertRWO(node.left, key)
                :insertRWO(node.right, key);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return Arrays.asList(this.toArray()).contains(o);
    }

    @Override
    public Iterator iterator() {
        return new SplayTreeIterator();
    }

    private class SplayTreeIterator implements Iterator<Integer> {
        private Node next;

        SplayTreeIterator(){
            next = root;
            if(next == null) return;
            while (next.left != null)
                next = next.left;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            Node cur = next;

            if (next.right != null) {
                next = next.right;
                while (next.left != null)
                    next = next.left;
                return cur.key;
            }

            while (true) {
                if(next.parent == null) {
                    next = null;
                    return cur.key;
                }
                if(next.parent.left == next) {
                    next = next.parent;
                    return cur.key;
                }
                next = next.parent;
            }
        }
    }

    @Override
    public Object[] toArray() {
        Iterator iter = this.iterator();
        Object[] arr = new Object[this.size];
        for (int i = 0; i < this.size; i++)
            arr[i] = iter.next();
        return arr;
    }

    @Override
    public boolean add(Object o) {
        this.insert((int) o);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        this.remove(this.find((int) o));
        return true;
    }

    @Override
    public boolean addAll(Collection c) {
        for (Object o : c) {
            this.insert((int) o);
        }
        return true;
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }


    @Override
    public boolean removeAll(Collection c) {
        for (Object o : c) {
            this.remove((int) o);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection c) {
        Iterator iter = this.iterator();
        while (iter.hasNext()) {
            Object a = iter.next();
            if (!c.contains(a)) {
                this.remove(a);
                iter = this.iterator();
            }
        }
        return true;
    }

    @Override
    public boolean containsAll(Collection c) {
        for (Object o : c) {
            if (!this.contains(o))
                return false;
        }
        return true;
    }

    @Override
    public Object[] toArray(Object[] a) {
        if (a.length < this.size)
            return this.toArray();

        Iterator iter = this.iterator();
        int i = 0;
        while (iter.hasNext()) {
            a[i] = iter.next();
            i++;
        }
        if (i != a.length)
            a[i] = 0;
        return a;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        Tree tree = (Tree) obj;
        return Arrays.equals(tree.toArray(), this.toArray());
    }

    @Override
    public int hashCode() {
        return root.key * size;
    }
}
