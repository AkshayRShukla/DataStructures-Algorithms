public class PriorityQueue<T extends Comparable<? super T>> implements
    PriorityQueueInterface<T>, Gradable<T> {

    private Heap<T> queue = new Heap<T>();


    public void insert(T item) {
        queue.add(item);
    }


    public T findMin() {
        return queue.peek();
    }


    public T deleteMin() {
        return queue.remove();
    }


    public boolean isEmpty() {
        return queue.isEmpty();
    }


    public void makeEmpty() {
        queue = new Heap<T>();
    }


    public T[] toArray() {
        return queue.toArray();
    }


}
