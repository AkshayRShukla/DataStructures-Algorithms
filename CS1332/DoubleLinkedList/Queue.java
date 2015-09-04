/**
 * Your queue implementation. Don't add any new public methods.
 *
 * @author Akshay Shukla
 * @version 1.0
 */
public class Queue<T> implements QueueInterface<T> {

    private LinkedListInterface<T> queue;

    public Queue() {
        queue = new DoublyLinkedList<T>();
    }


    public void enqueue(T t) {
        queue.addToFront(t);
    }

    public T dequeue() {
        return queue.removeFromBack();
    }

    public int size() {
        return queue.size();
    }


    public boolean isEmpty() {
        return queue.isEmpty();
    }

}
