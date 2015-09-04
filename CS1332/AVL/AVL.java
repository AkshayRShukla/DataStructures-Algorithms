import java.util.ArrayList;
import java.util.List;

/**
 * My AVL implementation.
 *
 * @author Akshay Shukla
 */
public class AVL<T extends Comparable<T>> implements AVLInterface<T>,
    Gradable<T> {


    // Do not add additional instance variables
    private Node<T> root;
    private int size = 0;

    /**
     * Add the data as a leaf in the BST.  Should traverse the tree to find the
     * appropriate location.
     * Should have a worst case running time of O(logn)
     * If the data is already in the tree, do nothing.
     * @param data the data to be added
     * @throws IllegalArgumentException if {@code data} is null
     */

    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        size++;
        root = add(data, root);
    }

    /**
     * Helper method for the add method.
     * @param data the data to be added/
     * @param current node in the AVL tree.
     */
    private Node<T> add(T data, Node<T> current) {

        if (current == null) {
            current = new Node<T>(data);
        } else if (data.compareTo(current.getData()) < 0) {
            current.setLeft(add(data, current.getLeft()));
            updateHeight(current);
            balanceFactor(current);
            current = rebalanceLeft(current, data);
        } else if (data.compareTo(current.getData()) > 0) {
            current.setRight(add(data, current.getRight()));
            updateHeight(current);
            balanceFactor(current);
            current = rebalanceRight(current, data);

        }
        return current;
    }
    /**
     * Helper method to update Height.
     * @param current node in the AVL tree.
     */
    private void updateHeight(Node<T> current) {
        if (current.getLeft() != null && current.getRight() != null) {
            current.setHeight(Math.max(height(current.getLeft()),
                    height(current.getRight())) + 1);
        } else if (current.getLeft() != null) {
            current.setHeight(height(current.getLeft()) + 1);
        } else if (current.getRight() != null) {
            current.setHeight(height(current.getRight()) + 1);
        } else if (current.getLeft() == null
                && current.getRight() == null) {
            current.setHeight(0);
        }
    }
    /**
     * Helper method to update the balance factor.
     * @param current node in the AVL tree.
     */
    private void balanceFactor(Node<T> current) {
        if (current.getLeft() != null && current.getRight() != null) {
            current.setBalanceFactor(current.getLeft().getHeight()
                    - current.getRight().getHeight());
        } else if (current.getLeft() != null) {
            current.setBalanceFactor(current.getLeft().getHeight() - 1);
        } else if (current.getRight() != null) {
            current.setBalanceFactor(-1 - current.getRight().getHeight());
        } else {
            current.setBalanceFactor(0);
        }

    }


    /**
     * Helper method to re-balance.
     * @param data being inspected.
     * @param current node in the AVL tree.
     * @return current node
     */

    private Node<T> rebalanceLeft(Node<T> current, T data) {

        if (height(current.getLeft()) - height(current.getRight()) == 2) {
            if (data.compareTo(current.getLeft().getData()) < 0) {
                current = rotateWithLeftChild(current);
            } else {
                current = doubleWithLeftChild(current);
            }
        }
        return current;
    }
    /**
     * Helper method to re-balance.
     * @param data being inspected.
     * @param current node in the AVL tree.
     * @return current node
     */
    private Node<T> rebalanceRight(Node<T> current, T data) {
        if (height(current.getRight()) - height(current.getLeft()) == 2) {
            if (data.compareTo(current.getRight().getData()) > 0) {
                current = rotateWithRightChild(current);
            } else {
                current = doubleWithRightChild(current);
            }
        }
        return current;
    }
    /**
     * Helper method to calculate height
     * @param current node in the AVL tree.
     * @return height of the current node.
     */
    private int height(Node<T> current) {
        return current == null ? -1 : current.getHeight();
    }

    /**
     * Helper method to re-balance.
     * @param current node in the AVL tree.
     * @return current node
     */
    protected Node<T> rotateWithLeftChild(Node<T> current) {
        Node<T> child = current.getLeft();

        current.setLeft(child.getRight());
        child.setRight(current);

        updateHeight(current);
        balanceFactor(current);
        updateHeight(child);
        balanceFactor(child);

        return (child);
    }
    /**
     * Helper method to re-balance.
     * @param current node in the AVL tree.
     * @return current node
     */
    protected Node<T> rotateWithRightChild(Node<T> current) {
        Node<T> child = current.getRight();

        current.setRight(child.getLeft());
        child.setLeft(current);

        current.setHeight(Math.max(height(current.getLeft()),
                height(current.getRight())) + 1);
        child.setHeight(Math.max(height(child.getRight()),
                current.getHeight()) + 1);

        updateHeight(current);
        balanceFactor(current);
        updateHeight(child);
        balanceFactor(child);

        return (child);
    }
    /**
     * Helper method to re-balance.
     * @param current node in the AVL tree.
     * @return current node
     */
    protected Node<T> doubleWithRightChild(Node<T> current) {
        current.setRight(rotateWithLeftChild(current.getRight()));
        return rotateWithRightChild(current);
    }

    /**
     * Helper method to re-balance.
     * @param current node in the AVL tree.
     * @return current node
     */
    protected Node<T> doubleWithLeftChild(Node<T> current) {
        current.setLeft(rotateWithRightChild(current.getLeft()));
        return rotateWithLeftChild(current);
    }



    //@Override
    /**
     * Remove from the tree. Nothing is done if data is not found.
     * @param data the item to remove.
     * @return data being removed.
     */

    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        Node<T> toReturn = new Node<T>(null);
        root = remove(root, data, toReturn);
        return toReturn.getData();
    }

    /**
     * Helper method for the remove method.
     * @param curr node in the AVL tree.
     * @param data being inspected.
     * @param node being returned (ie deleted).
     * @return current node
     */

    private Node<T> remove(Node<T> curr, T data, Node<T> toReturn) {
        if (curr == null) {
            return null;
        }
        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(remove(curr.getLeft(), data, toReturn));
            updateHeight(curr);
            balanceFactor(curr);
            rebalanceLeft(curr, data);
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(remove(curr.getRight(), data, toReturn));
            updateHeight(curr);
            balanceFactor(curr);
            curr =  rebalanceRight(curr, data);
        } else {
            size--;
            toReturn.setData(curr.getData());

            if (curr.getLeft() != null && curr.getRight() != null) {
                curr.setData(twoChildren(curr));
            } else if (curr.getLeft() == null) {
                curr = curr.getRight();
                if (height(curr) == 2) {
                    updateHeight(curr);
                    balanceFactor(curr);
                    rebalanceLeft(curr, data);
                }

            } else if (curr.getRight() == null) {
                curr = curr.getLeft();
                if (height(curr) == 2) {
                    updateHeight(curr);
                    balanceFactor(curr);
                    rebalanceRight(curr, data);
                } else {
                    if (height(curr) == 2) {
                        updateHeight(curr);
                        balanceFactor(curr);
                        rebalanceRight(curr, data);
                    }
                }

            }

        }
        return curr;
    }
    /**
     * Helper method to find the successor.
     * @param curr node in the AVL tree.
     * @return data of successor.
     */
    private T twoChildren(Node<T> curr) {
        Node<T> succ = curr.getRight();
        Node<T> succParent = null;
        while (succ.getLeft() != null) {
            succParent = succ;
            succ = succ.getLeft();
        }
        T ret = succ.getData();
        if (succParent == null) {
            curr.setRight(succ.getRight());
        } else {
            succParent.setLeft(succ.getRight());
        }
        return ret;
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use .equals or == ?).
     * Should have a worst case running time of O(logn)
     * @param data
     * @return the data in the tree equal to the parameter
     * @throws IllegalArgumentException if {@code data} is null
     */

    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        if (!contains(data)) {
            return null;
        }
        if ((root == null) && (data.compareTo(root.getData()) == 0)) {
            return null;
        }
        return contains(root, data).getData();
    }

    /**
     * Returns whether or not the parameter is contained within the tree.
     * Should have a worst case running time of O(logn)
     * @param data
     * @return whether or not the parameter is contained within the tree
     * @throws IllegalArgumentException if {@code data} is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        return contains(root, data) != null;
    }
    /**
     * Helper method for the contains method.
     * @param current node
     * @param data
     * @return Node that contains the data.
     */
    private Node<T> contains(Node<T> current, T data) {
        Node<T> tf = null;
        while ((current != null) && tf == null) {
            if (data.compareTo(current.getData()) < 0) {
                current = current.getLeft();
                tf = contains(current, data);
            } else if (data.compareTo(current.getData()) > 0) {
                current = current.getRight();
                tf = contains(current, data);
            } else {
                tf = current;
            }
        }
        return tf;
    }

    /**
     * Checks if the tree is empty or not.
     * Should run in O(1)
     * @return true if this tree is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Should run in O(1).
     * @return the number of elements in the tree
     */
    public int size() {
        return size;
    }
    /**
     * Should run in O(n).
     * @return a preorder traversal of the tree
     */
    public List<T> preorder() {
        ArrayList<T> preorderList = new ArrayList<T>();
        preorderHelper(root, preorderList);
        return preorderList;
    }

    /**
     * Helper method for the pre order traversal.
     * @return a pre order traversal list of the tree.
     */

    private List<T> preorderHelper(Node<T> current, List<T> preorderList) {
        if (current != null) {
            preorderList.add(current.getData());
            preorderHelper(current.getLeft(), preorderList);
            preorderHelper(current.getRight(), preorderList);
        }
        return preorderList;
    }
    /**
     * Should run in O(n).
     * @return a postorder traversal of the tree
     */
    public List<T> postorder() {
        ArrayList<T> postorderList = new ArrayList<T>();
        postorderHelper(root, postorderList);
        return postorderList;
    }

    /**
     * Helper method for the post order traversal.
     * @param current node
     * @param list
     * @return a post order traversal list of the tree.
     */

    private List<T> postorderHelper(Node<T> current,
            ArrayList<T> postorderList) {
        if (current != null) {
            postorderHelper(current.getLeft(), postorderList);
            postorderHelper(current.getRight(), postorderList);
            postorderList.add(current.getData());
        }
        return postorderList;
    }

    /**
     * Should run in O(n)
     * @return an inorder traversal of the tree
     */
    public List<T> inorder() {
        ArrayList<T> inorderList = new ArrayList<T>();
        inorderHelper(root, inorderList);
        return inorderList;
    }

    /**
     * Helper method for the in order traversal.
     * @return a in order traversal list of the tree.
     */

    private List<T> inorderHelper(Node<T> current, List<T> inorderList) {
        if (current != null) {
            inorderHelper(current.getLeft(), inorderList);
            inorderList.add(current.getData());
            inorderHelper(current.getRight(), inorderList);
        }
        return inorderList;
    }

    /**
     * Should run in O(n)
     * @return a level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> levelorderList = new ArrayList<T>();
        if (root != null) {
            java.util.LinkedList<Node<T>> queue =
                    new java.util.LinkedList<Node<T>>();
            queue.push(root);
            Node<T> next;
            while (!queue.isEmpty()) {
                next = queue.removeFirst();
                levelorderList.add(next.getData());
                if (next.getLeft() != null) {
                    queue.add(next.getLeft());
                }
                if (next.getRight() != null) {
                    queue.add(next.getRight());
                }
            }
        }
        return levelorderList;
    }

    /**
     * Clear the tree.  Should be O(1)
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Calculate and return the height of the root of the tree. A node's height
     * is defined as max(left.height, right.height) + 1. A leaf node has a
     * height of 0. An empty tree should return -1.
     * Should be calculated in O(1)
     * @return the height of the root of the tree
     */
    public int height() {
        if (size == 0) {
            return -1;

        }
        return Math.abs(root.getHeight());
    }

    /**
     * Method to return the root of the avl tree.
     * @return the root.
     */
    public Node<T> getRoot() {
        return root;
    }
}
