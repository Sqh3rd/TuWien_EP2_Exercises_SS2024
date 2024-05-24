package Tests.Tree;

import Collections.Tree.BinaryTree;
import org.junit.jupiter.api.Test;

import static Tests.TestUtils.assertThat;

public class BinaryTreeTest {
    @Test
    void addElements() {
        BinaryTree<Integer> tree = standardTestTree();
        assertThat(tree.toString()).isEqualTo("0, 2, 3, 4, 5, 7, 18, 20");
        assertThat(tree.traversePreorder().toString()).isEqualTo("5, 3, 0, 2, 4, 7, 20, 18");
    }

    @Test
    void removeElements_onlyNode() {
        BinaryTree<Integer> tree = new BinaryTree<>(Integer::compareTo, 0);
        tree.remove(0);
        assertThat(tree.toString()).isEqualTo("");
        assertThat(tree.traversePreorder().toString()).isEqualTo("");
    }

    @Test
    void removeElements_endNode() {
        BinaryTree<Integer> tree = standardTestTree();
        tree.remove(0);
        assertThat(tree.toString()).isEqualTo("2, 3, 4, 5, 7, 18, 20");
        assertThat(tree.traversePreorder().toString()).isEqualTo("5, 3, 2, 4, 7, 20, 18");
    }

    @Test
    void removeElements_nodeWithLeftChild() {
        BinaryTree<Integer> tree = standardTestTree();
        tree.remove(20);
        assertThat(tree.toString()).isEqualTo("0, 2, 3, 4, 5, 7, 18");
        assertThat(tree.traversePreorder().toString()).isEqualTo("5, 3, 0, 2, 4, 7, 18");
    }

    @Test
    void removeElements_nodeWithRightChild() {
        BinaryTree<Integer> tree = standardTestTree();
        tree.remove(7);
        assertThat(tree.toString()).isEqualTo("0, 2, 3, 4, 5, 18, 20");
        assertThat(tree.traversePreorder().toString()).isEqualTo("5, 3, 0, 2, 4, 18, 20");
    }

    @Test
    void removeElements_nodeWithBothChildren() {
        BinaryTree<Integer> tree = standardTestTree();
        tree.remove(5);
        assertThat(tree.toString()).isEqualTo("0, 2, 3, 4, 7, 18, 20");
        assertThat(tree.traversePreorder().toString()).isEqualTo("4, 3, 0, 2, 7, 20, 18");
    }

    private BinaryTree<Integer> standardTestTree() {
        BinaryTree<Integer> tree = new BinaryTree<Integer>(Integer::compare);
        tree.add(5);
        tree.add(3);
        tree.add(7);
        tree.add(4);
        tree.add(20);
        tree.add(18);
        tree.add(0);
        tree.add(2);
        return tree;
    }
}
