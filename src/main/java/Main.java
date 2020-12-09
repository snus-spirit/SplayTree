public class Main {

    public static void main(String[] args) {
        Tree tree = new Tree();
        tree.insertWOSplay(21);
        tree.insertWOSplay(7);
        tree.insertWOSplay(32);
        tree.insertWOSplay(5);
        tree.insertWOSplay(14);
        tree.insertWOSplay(27);
        tree.insertWOSplay(37);
        tree.insertWOSplay(25);
       //System.out.println(tree.find(37));
        tree.remove(new  Node(100));
    }
}
