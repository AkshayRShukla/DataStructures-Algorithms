/**
 * CircularLinkedList implementation.
 * @author Akshay Shukla
 * @version 1.0
 */

public class CircularLinkedList<T> implements LinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public CircularLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Return a boolean value representing whether or not
     * the list is empty.
     *
     * Must be O(1)
     *
     * @return True if empty. False otherwise.
     */

    public boolean isEmpty() {
        return size <= 0;
    }

    /**
     * Return the size of the list as an integer.
     *
     * Must be O(1)
     *
     * @return The size of the list.
     */

    public int size() {
        return size;
    }

    /**
     * Add a new node to the front of your linked list
     * that holds the given data. Make sure to update head.
     *
     * Must be O(1)
     *
     * @param t The data that the new node should hold.
     */

    public void addToFront(T element) {
        Node<T> temp = new Node<T>(element, null);
        if (size == 0) {
            head = temp;
            temp.setNext(head);
            tail = head;
        } else {
            temp.setNext(head);
            tail.setNext(temp);
            temp.setNext(head);
            head = temp;
        }
        size++;
    }

    /**
     * Add a new node to the back of your linked list
     * that holds the given data. Make sure to update tail.
     *
     * Must be O(1)
     *
     * @param t The data that the new node should hold.
     */

    public void addToBack(T element) {
        Node<T> temp = new Node<T>(element, null);
        if (size == 0) {
            head = temp;
            temp.setNext(head);
            tail = head;
            size++;
        } else {
            temp.setNext(head);
            tail.setNext(temp);
            tail = temp;
            size++;
        }
    }

    /**
     * Adds the element to the index specified.
     * Adding to indices 0 and size should be O(1), all other adds are O(n)
     *
     * Throw java.lang.IndexOutOfBoundsException if index is negative or index > size.
     *
     * @param index The index where you want the new element.
     * @param data Any object of type T.
     */

    public void addAtIndex(int index, T data) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        } else if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            Node<T> insert = new Node<T>(data);
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            Node<T> temp = current.getNext();
            current.setNext(insert);
            insert.setNext(temp);
            size++;
        }
    }

    /**
     * Removes and returns the element at index.
     * This method should be O(1) for index 0, O(n) in all other cases.
     *
     * Throw java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     *
     * @param index The index of the element
     * @return The object that was formerly at that index.
     */

    public T removeAtIndex(int index) {
        if (index > size() || index < 0 || size == 0) {
            throw new IndexOutOfBoundsException();
        } else if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            Node<T> removal = current.getNext();
            current.setNext(removal.getNext());
            size--;
            return removal.getData();
        }


    }

    /**
     * Remove the front node from the list and return the
     * data from it.
     *
     * Must be O(1)
     *
     * @return The data from the front node or null if empty.
     */

    public T removeFromFront() {
        if (size == 1) {
            T removedData = head.getData();
            clear();
            return removedData;
        } else if (size == 0) {
            return null;
        } else {
            Node<T> temp = head;
            head = head.getNext();
            tail.setNext(head);
            size--;
            return temp.getData();
        }
    }

    /**
     * Remove the back node from the list and return the
     * data from it.
     *
     * Must be O(n)
     *
     * @return The data from the last node or null if empty.
     */

    public T removeFromBack() {
        if (size <= 0) {
            return null;
        } else if (size == 1) {
            T data = head.getData();
            head = null;
            tail = null;
            size = 0;
            return data;
        } else {
            Node<T> current = head;
            for (int i = 0; i < size - 1; i++) {
                current = current.getNext();
            }
            T data = tail.getData();
            current.setNext(head);
            tail = current;
            size--;
            return data;
        }
    }

    /**
     * Returns the element at the given index.
     * This method must be O(1) for index 0 and index (size-1).
     * O(n) is expected for all other indices.
     *
     * Throw java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     *
     *
     * @param index The index of the element
     * @return The object stored at that index.
     */

    public T get(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getData();
    }

    /**
     * Clear the list.
     *
     * Must be O(1)
     */

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }


    public boolean contains(Object obj) {
        Node<T> result = search(obj);
        return (result != null);
    }

    /**
     * Searches list to see if there is a Node containing
     * the object in the parameter. 
     *
     * O(n)
     * 
     * @return the Node that contains the object
     * or if it doesn't exist, returns null. 
     */
    private Node<T> search(Object obj) {
        Node<T> present = head;
        T dataAtCurrent;
        for (int i = 0; i < size(); i++) {
            dataAtCurrent =  present.getData();
            if (obj.equals(dataAtCurrent)) {
                return present;
            }
            present = present.getNext();
        }
        return null;
    }

    public Node<T> getHead() {
        return head;
    }
    public Node<T> getTail() {
        return tail;
    }

    /**
     * Return the linked list represented as an array of objects.
     * An empty list should return an empty array.
     *
     * Must be O(n)
     *
     * @return A copy of the linked list data as an array.
     */

    @SuppressWarnings("unchecked")
    public T[] toList() {
        Object[] arr = new Object[size()];
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            arr[i] = current.getData();
            current = current.getNext();
        }
        return (T[]) arr;
    }

}