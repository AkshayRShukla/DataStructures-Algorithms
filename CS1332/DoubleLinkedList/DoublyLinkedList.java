/**
 * Doubly linked list implementation
 * @author Akshay Shukla
 * @version 1.0
 */
public class DoublyLinkedList<T> implements LinkedListInterface<T> {

    private int size;
    private Node<T> head;
    private Node<T> tail;

    public void addAtIndex(int index, T data) {
        Node<T> current = new Node<T>(data);
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            if (size == 0) {
                head = current;
                tail = current;
            } else {
                current.setNext(head);
                head.setPrevious(current);
                head = current;
            }
        } else if (index == size) {
            if (head == null) {
                head = current;
                tail = head;
                head.setNext(null);
                tail.setPrevious(head);
                tail.setNext(head);
            } else {
                tail.setNext(current);
                current.setPrevious(tail);
                tail = current;
            }
        } else {
            Node<T> indexCursor = head;
            for (int i = 0; i < index - 1; i++) {
                indexCursor = indexCursor.getNext();
            }
            Node<T> post = indexCursor.getNext();
            indexCursor.setNext(current);
            current.setPrevious(indexCursor);
            current.setNext(post);
            post.setPrevious(current);
        }
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getData();
    }

    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            T data = current.getData();
            Node<T> pre = current.getPrevious();
            Node<T> post = current.getNext();
            current = null;
            pre.setNext(post);
            post.setPrevious(pre);
            size--;
            return data;
        }
    }

    public void addToFront(T t) {
        addAtIndex(0, t);
    }

    public void addToBack(T t) {
        addAtIndex(size(), t);
    }

    public T removeFromFront() {
        if (size == 1) {
            T data = head.getData();
            clear();
            return data;
        } else if (size == 0) {
            return null;
        } else {
            T data = head.getData();
            head = head.getNext();
            head.setPrevious(null);
            size--;
            return data;
        }

    }


    public T removeFromBack() {
        if (size == 0) {
            return null;
        } else if (size == 1) {
            T data = head.getData();
            clear();
            return data;
        } else {
            T data = tail.getData();
            Node<T> current = tail.getPrevious();
            tail = null;
            current.setNext(null);
            tail = current;
            size--;
            return data;
        }

    }

    public Object[] toArray() {
        Object[] arr = new Object[size()];
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            arr[i] = current.getData();
            current = current.getNext();
        }
        return arr;
    }

    public boolean isEmpty() {
        return (head == null);
    }

    public int size() {
        return size;
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }


    /**
     * Reference to the head node of the linked list.
     * Normally, you would not do this, but we need it
     * for grading your work.
     *
     * You will get a 0 if you do not implement this method.
     *
     * @return Node representing the head of the linked list
     */
    public Node<T> getHead() {
        return head;
    }

    /**
     * Reference to the tail node of the linked list.
     * Normally, you would not do this, but we need it
     * for grading your work.
     *
     * You will get a 0 if you do not implement this method.
     *
     * @return Node representing the tail of the linked list
     */
    public Node<T> getTail() {
        return tail;
    }
}
