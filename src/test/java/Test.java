import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    Tree tree;

    private Tree setTree() {
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
    void zig(){
        tree = setTree();
        tree.find(32);
        assertEquals(tree.getRoot().key,32);
        assertEquals(tree.findWOSplay(21).right.key, 27);
        assertEquals(tree.findWOSplay(21).parent.key, 32);

        tree = setTree();
        tree.find(7);
        assertEquals(tree.getRoot().key,7);
        assertEquals(tree.findWOSplay(21).left.key, 14);
        assertEquals(tree.findWOSplay(21).parent.key, 7);

    }
    @Test
    void zigZig(){
        tree = setTree();
        tree.find(37);
        assertEquals(tree.getRoot().key, 37);
        assertEquals(tree.findWOSplay(37).left.key, 32);
        assertEquals(tree.findWOSplay(21).right.key, 27);
        assertEquals(tree.findWOSplay(32).left.key, 21);
    }
    @Test
    void zigZag(){
        tree = setTree();
        tree.find(27);
        assertEquals(tree.getRoot().key, 27);
        assertEquals(tree.findWOSplay(27).right.key, 32);
        assertEquals(tree.findWOSplay(27).left.key, 21);
        assertEquals(tree.findWOSplay(21).right.key, 25);
    }
    @Test
    void remove() {
        tree = setTree();
        tree.remove(tree.find(14));
        assertNull(tree.find(14));
        assertEquals(tree.findWOSplay(7), tree.getRoot());
        assertEquals(tree.findWOSplay(7).left.key, 5);
        assertEquals(tree.findWOSplay(7).right.key, 21);
    }
    @Test
    void insert(){
        tree = setTree();
        tree.insert(17);
        assertEquals(tree.findWOSplay(17), tree.getRoot());
        assertEquals(tree.findWOSplay(17).left.key, 14);
        assertEquals(tree.findWOSplay(17).right.key, 21);
    }
    @Test
    void setMethods(){
        Tree tree = new Tree();
        tree.insert(14);
        tree.insert(27);
        tree.insert(37);
        tree.insert(25);

        assertFalse(tree.isEmpty());
        assertEquals(tree.size(), 4);

        assertFalse(tree.contains(11));
        assertTrue(tree.contains(14));

        Iterator iter = tree.iterator();
        for (int i = 0; i < tree.size(); i++) iter.next();
        assertFalse(iter.hasNext());

        assertTrue(tree.containsAll(Arrays.asList(tree.toArray())));

        tree.clear();
        assertNull(tree.getRoot());
        assertEquals(tree.size(), 0);
    }
}
