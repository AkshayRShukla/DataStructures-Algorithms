import java.util.ArrayList;
import java.util.List;

public class BST<T extends Comparable<T>> implements BSTInterface<T> {
    private Node<T> root;
    private int size;

    public void add(T data) {
        root = addHelper(root, data);
        size++;
    }

    /**
     * Helper method for the add method.
     * @param current node and data the data to be added
     *@return Node being added.
     */

    private Node<T> addHelper(Node<T> current, T data) {
        if (current == null) {
            current = new Node<T>(data);
        } else if (data.compareTo(current.getData()) < 0) {
            current.setLeft(addHelper(current.getLeft(), data));
        } else {
            current.setRight(addHelper(current.getRight(), data));
        }
        return current;
    }


    public T remove(T data) {
        if (root == null) {
            return null;
        }
        if (!contains(data)) {
            return null;
        }
        if (data.compareTo(root.getData()) == 0) {
            T temp = root.getData();
            Node<T> temporary = new Node<T>(temp);
            Node<T> leftChild = root.getLeft();
            Node<T> rightChild = root.getRight();
            if (leftChild == null && rightChild == null) {
                root = null;
            } else if (leftChild == null) {
                root = rightChild;
                rightChild = null;
            } else if (rightChild == null) {
                root = leftChild;
                leftChild = null;
            } else {
                Node<T> leftChildParent = leftChild;
                while (leftChild.getRight() != null) {
                    leftChildParent = leftChild;
                    leftChild = leftChild.getRight();
                }
                root.setData(leftChild.getData());
                leftChild = null;
                leftChildParent.setRight(null);
            }
            size--;
            return temporary.getData();
        } else {
            Node<T> temporary = removeHelper(root, data);
            size--;
            return temporary.getData();
        }
    }
    /**
     * Helper method for the remove method.
     * @param current Node and data
     * @return the Node with the data removed from the tree.
     * Return null if no such data exists
     */

    private Node<T> removeHelper(Node<T> current, T data) {
        if (data.compareTo(current.getData()) == 0) {
            Node<T> leftChild = current.getLeft();
            Node<T> rightChild = current.getRight();
            T temp = current.getData();
            Node<T> temporary = new Node<T>(temp);
            if (leftChild == null && rightChild == null) {
                current = null;
                root = null;
            } else if (leftChild == null) {
                current = rightChild;
                rightChild = null;
            } else if (rightChild == null) {
                current = leftChild;
                leftChild = null;
            }  else {
                Node<T> leftChildParent = leftChild;
                while (leftChild.getRight() != null) {
                    leftChildParent = leftChild;
                    leftChild = leftChild.getRight();
                }
                current.setData(leftChild.getData());
                leftChild = null;
                leftChildParent.setRight(null);
            }
            return temporary;

        } else if (data.compareTo(current.getData()) < 0) {
            Node<T> deleted = removeHelper(root.getLeft(), data);
            return deleted;
        } else {
            Node<T> deleted = removeHelper(root.getRight(), data);
            return deleted;
        }
    }


    public T get(T data) {
        if (contains(data)) {
            return data;
        } else {
            return null;
        }
    }


    public boolean contains(T data) {
        return contains(root, data);
    }

    /**
     * Helper function for the contains method.
     * @param current Node and data that is being checked.
     * @return whether or not the parameter is contained within the tree
     */

    private boolean contains(Node<T> current, T data) {
        boolean tf = false;
        while ((current != null) && !tf) {
            T currentNodeData = current.getData();
            if (data.compareTo(currentNodeData) < 0) {
                current = current.getLeft();
            } else if (data.compareTo(currentNodeData) > 0) {
                current = current.getRight();
            } else {
                tf = true;
                break;
            }
            tf = contains(current, data);
        }
        return tf;
    }


    public int size() {
        return size;
    }


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

    public List<T> postorder() {
        ArrayList<T> postorderList = new ArrayList<T>();
        postorderHelper(root, postorderList);
        return postorderList;
    }

    /**
     * Helper method for the post order traversal.
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



    public void clear() {
        root = null;
        size = 0;
    }

    public int height() {
        if (root == null) {
            return -1;
        }
        Node<T> currentRight = root;
        Node<T> currentLeft = root;
        int heightOfRight = 0;
        int heightOfLeft = 0;
        while (currentRight.getRight() != null) {
            ++heightOfRight;
            currentRight = currentRight.getRight();
        }
        while (currentLeft.getLeft() != null) {
            ++heightOfLeft;
            currentLeft = currentLeft.getLeft();
        }

        if (heightOfLeft < heightOfRight) {
            return heightOfRight;
        } else {
            return heightOfLeft;
        }

    }

    public Node<T> getRoot() {
        return root;

    }
}