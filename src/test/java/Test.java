import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    Tree tree;
    Tree setTree() {
        Tree tree = new Tree();
        tree.insertWOSplay(21);
        tree.insertWOSplay(7);
        tree.insertWOSplay(32);
        tree.insertWOSplay(5);
        tree.insertWOSplay(14);
        tree.insertWOSplay(27);
        tree.insertWOSplay(37);
        tree.insertWOSplay(25);
        return tree;
    }

    @Test
    void zip(){
        tree = setTree();
        assertEquals(tree.find(32).left,tree.findWOSplay(21));
        assertEquals(tree.findWOSplay(32).right, tree.findWOSplay(37));
        assertEquals(tree.findWOSplay(21).right, tree.findWOSplay(27));
    }
    @Test
    void zipZip(){
        tree = setTree();
        assertEquals(tree.find(37).left, tree.findWOSplay(32));
        assertEquals(tree.findWOSplay(21).right, tree.findWOSplay(27));
        assertEquals(tree.findWOSplay(32).left, tree.findWOSplay(21));
    }
    @Test
    void zipZap(){
        tree = setTree();
        assertEquals(tree.find(27).right, tree.findWOSplay(32));
        assertEquals(tree.find(27).left, tree.findWOSplay(21));
        assertEquals(tree.findWOSplay(21).right, tree.findWOSplay(25));
    }
    @Test
    void remove() {
        tree = setTree();
        tree.remove(tree.findWOSplay(14));
        assertNull(tree.find(14));
        assertEquals(tree.findWOSplay(7), tree.root);
        assertEquals(tree.findWOSplay(7).left, tree.findWOSplay(5));
        assertEquals(tree.findWOSplay(7).right, tree.findWOSplay(21));
    }
    @Test
    void insert(){
        tree = setTree();
        tree.insert(17);
        assertEquals(tree.findWOSplay(17), tree.root);
        assertEquals(tree.findWOSplay(17).left, tree.findWOSplay(14));
        assertEquals(tree.findWOSplay(17).right, tree.findWOSplay(21));
    }
}
