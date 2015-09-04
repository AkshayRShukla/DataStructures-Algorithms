/**
 * Your stack implementation. Don't add any new public methods.
 *
 * @author Akshay Shukla
 * @version 1.0
 */
public class Stack<T> implements StackInterface<T> {

    private LinkedListInterface<T> stack;

    public Stack() {
        stack = new DoublyLinkedList<T>();
    }

    public void push(T t) {
        stack.addToBack(t);
    }


    public T pop() {
        return stack.removeFromBack();
    }


    public int size() {
        return stack.size();
    }
    public boolean isEmpty() {
        return stack.isEmpty();
    }



}
