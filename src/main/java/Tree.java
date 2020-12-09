public class Tree {
    //использовать деерво как реализацию set\map
    public Node root;

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
            return;
        }
        Node parent = insertR(root, key);
        if (key < parent.key)
            parent.left = node;
        else
            parent.right = node;
        node.parent = parent;
        splay(node);
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

    public void leftChildToParent(Node node, Node parent){
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

    public void rightChildToParent(Node node, Node parent){
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

    public void splay(Node node) {
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
    }


    public Node findWOSplay(int key) {
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

    public void insertWOSplay(int key) {
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
}
