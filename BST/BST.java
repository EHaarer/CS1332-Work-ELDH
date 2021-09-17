import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a BST.
 *
 * @author Ethan Haarer
 * @version 1.0
 * @userid ehaarer3
 * @GTID 903586678
 *
 * Collaborators: None
 *
 * Resources: only class resources
 */
public class BST<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to BST");
        }
        for (T num : data) {
            if (num == null) {
                throw new IllegalArgumentException("Cannot add null data to BST");
            }
            add(num);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * This method must be implemented recursively.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to BST");
        }
        if (root == null) {
            root = new BSTNode<T>(data);
            size++;
        } else {
            addHelper(data, root);
        }
    }

    /**
     * Recursively adds data to the BST, helper function to add().
     *
     * @param data the data added to the BST.
     * @param node the node to compare the current value to.
     */
    private void addHelper(T data, BSTNode<T> node) {
        BSTNode<T> temp = new BSTNode<>(data);
        if (temp.getData().compareTo(node.getData()) < 0) {
            if (node.getLeft() != null) {
                addHelper(data, node.getLeft());
            } else {
                node.setLeft(temp);
                size++;
            }
        } else if (temp.getData().compareTo(node.getData()) > 0) {
            if (node.getRight() != null) {
                addHelper(data, node.getRight());
            } else {
                node.setRight(temp);
                size++;
            }
        }
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data. You MUST use recursion to find and remove the
     * predecessor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     * 
     * This method must be implemented recursively.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null element from BST");
        }
        BSTNode<T> dummy = new BSTNode<T>(null);
        root = getToNode(data, root, dummy);
        return dummy.getData();
    }

    /**
     * This method helps remove() and recursively finds and replaces a node to be removed.
     *
     * @param data the data of the node to be removed
     * @param node the current node that is being assessed
     * @param dummy BSTNode holding the data of the node being removed
     * @return the node that is removed
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    private BSTNode<T> getToNode(T data, BSTNode<T> node, BSTNode<T> dummy) {
        if (node == null) {
            throw new NoSuchElementException("Element does not exist in BST");
        }
        if (((T) node.getData()).compareTo(data) > 0) {
            node.setLeft(getToNode(data, node.getLeft(), dummy));
        } else if (((T) node.getData()).compareTo(data) < 0) {
            node.setRight(getToNode(data, node.getRight(), dummy));
        } else {
            dummy.setData(node.getData());
            size--;
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            } else if (node.getLeft() != null) {
                return node.getLeft();
            } else if (node.getRight() != null) {
                return node.getRight();
            } else {
                BSTNode<T> temp = new BSTNode<>(null);
                node.setRight(findPredecessor(node.getLeft(), temp));
                node.setData(temp.getData());
            }
        }
        return node;
    }


    /**
     * Recursively finds the predecessor to a given node
     * @param node right side of node that successor is needed for.
     * @param dummy the data to be held to be removed from BST.
     * @return Successor node desired
     */
    private BSTNode<T> findPredecessor(BSTNode<T> node, BSTNode<T> dummy) {
        if (node.getRight() != null) {
            node.setLeft(findPredecessor(node.getLeft(), dummy));
        }
        dummy.setData(node.getData());
        //node.setLeft(null);
        return node.getLeft();
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     * 
     * This method must be implemented recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot search for null element in BST");
        }
        return getterHelper(data, root);
    }

    /**
     * A recursive function to
     *
     * @param data the data to be returned from the tree
     * @param node the point in the tree to start at
     * @return the data, T, of the gotten node holding the requested data
     */
    private T getterHelper(T data, BSTNode<T> node) {
        T nodeData = (T) node.getData();
        if (node.getLeft() == null && node.getRight() == null && !(nodeData.equals(data))) {
            throw new NoSuchElementException("Element does not exist in tree");
        }
        if (data.compareTo(nodeData) < 0) {
            return getterHelper(data, node.getLeft());
        }
        if (data.compareTo(nodeData) > 0) {
            return getterHelper(data, node.getRight());
        }
        return (T) node.getData();
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     * 
     * This method must be implemented recursively.
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot search for null element in BST");
        }
        return containsHelper(data, root);
    }

    /**
     * Recursively looks through the BST to find if it contains the passed data value.
     *
     * @param data the data to search for in the tree.
     * @param node a given node in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     */
    private boolean containsHelper(T data, BSTNode<T> node) {
        BSTNode<T> temp = new BSTNode<>(data);
        if (node == null) {
            return false;
        }
        if (temp.getData().compareTo(node.getData()) == 0) {
            return true;
        } else if (temp.getData().compareTo(node.getData()) > 0) {
            return containsHelper(data, node.getRight());
        } else {
            return containsHelper(data, node.getLeft());
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * Must be O(n).
     *
     * This method must be implemented recursively.
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> preTree = new ArrayList<>();
        this.preorderHelper(root, preTree);
        return preTree;
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * Must be O(n).
     *
     * This method must be implemented recursively.
      * @param node the current node being looked at
     * @param preTree List
     */
    public void preorderHelper(BSTNode<T> node, List<T> preTree) {
        if (node == null) {
            return;
        } else {
            preTree.add((T) node.getData());
            if (node.getLeft() != null) {
                preorderHelper(node.getLeft(), preTree);
            }
            if (node.getRight()  != null) {
                preorderHelper(node.getRight(), preTree);
            }
        }
    }

    /**
     * Generate a in-order traversal of the tree.
     *
     * Must be O(n).
     * 
     * This method must be implemented recursively.
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> inTree = new ArrayList<>();
        this.inorderHelper(root, inTree);
        return inTree;
    }

    /**
     * Generate a inorder traversal of the tree.
     *
     * This method is recursive, and helps the postorder() method.
     * @param node current node being assessed
     * @param inTree list of all nodes in tree passed so far
     */
    public void inorderHelper(BSTNode<T> node, List<T> inTree) {
        if (node == null) {
            return;
        } else {
            if (node.getLeft() != null) {
                inorderHelper(node.getLeft(), inTree);
            }
            inTree.add((T) node.getData());
            if (node.getRight()  != null) {
                inorderHelper(node.getRight(), inTree);
            }
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * Must be O(n).
     *
     * This method must be implemented recursively.
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> inTree = new ArrayList<>();
        this.postorderHelper(root, inTree);
        return inTree;
    }

    /**
     * Generate a post - order traversal of the tree.
     *
     * This method is recursive, and helps the postorder() method.
     * @param node current node being assessed
     * @param inTree Tree returned in post order
     */
    public void postorderHelper(BSTNode<T> node, List<T> inTree) {
        if (node == null) {
            return;
        } else {
            if (node.getLeft() != null) {
                postorderHelper(node.getLeft(), inTree);
            }
            if (node.getRight()  != null) {
                postorderHelper(node.getRight(), inTree);
            }
            inTree.add(node.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
        List<T> outList = new ArrayList<T>();
        BSTNode<T> curr = null;
        if (root != null) {
            queue.add(root);
        }
        while (!queue.isEmpty()) {
            curr = queue.remove();
            outList.add(curr.getData());
            if (curr.getLeft() != null || curr.getRight() != null) {
                if (curr.getLeft() != null) {
                    queue.add(curr.getLeft());
                }
                if (curr.getRight() != null) {
                    queue.add(curr.getRight());
                }
            }
        }
        return outList;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child should be -1.
     *
     * Must be O(n).
     * 
     * This method must be implemented recursively.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightCounter(root);
    }

    /**
     * Recursively counts the height of a given node by counting the height
     * of the lower left and right sides.
     * @param node Node whose height is being counted.
     * @return the largest int generated by either the left or right node, the height of the root.
     */
    private int heightCounter(BSTNode<T> node) {
        if (node == null) {
            return -1;
        }
        int left = heightCounter(node.getLeft());
        int right = heightCounter(node.getRight());
        if (left > right) {
            return left + 1;
        } else {
            return right + 1;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * This method checks whether a binary tree meets the criteria for being
     * a binary search tree.
     *
     * This method is a static method that takes in a BSTNode called
     * {@code treeRoot}, which is the root of the tree that you should check.
     *
     * You may assume that the tree passed in is a proper binary tree; that is,
     * there are no loops in the tree, the parent-child relationship is
     * correct, that there are no duplicates, and that every parent has at
     * most 2 children. So, what you will have to check is that the order
     * property of a BST is still satisfied.
     *
     * Should run in O(n). However, you should stop the check as soon as you
     * find evidence that the tree is not a BST rather than checking the rest
     * of the tree.
     * 
     * This method must be implemented recursively.
     *
     * @param <T> the generic typing
     * @param treeRoot the root of the binary tree to check
     * @return true if the binary tree is a BST, false otherwise
     */
    public static <T extends Comparable<? super T>> boolean isBST(BSTNode<T> treeRoot) {
        if (treeRoot.getLeft() != null && treeRoot.getRight() == null) {
            if (treeRoot.getLeft().getData().compareTo(treeRoot.getData()) < 0) {
                return isBST(treeRoot.getLeft());
            } else {
                return false;
            }
        } else if (treeRoot.getLeft() == null && treeRoot.getRight() != null) {
            if (treeRoot.getRight().getData().compareTo(treeRoot.getData()) > 0) {
                return isBST(treeRoot.getRight());
            } else {
                return false;
            }
        } else if (treeRoot.getLeft() != null && treeRoot.getRight() != null) {
            if (treeRoot.getRight().getData().compareTo(treeRoot.getData()) > 0
                    && treeRoot.getLeft().getData().compareTo(treeRoot.getData()) < 0) {
                return isBST(treeRoot.getLeft()) && isBST(treeRoot.getRight());
            } else {
                return false;
            }
        } else if (treeRoot == null) {
            return true;
        }
        return true;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
