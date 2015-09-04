public class Heap<T extends Comparable<? super T>> implements HeapInterface<T>,
    Gradable<T> {

    private int size;
    @SuppressWarnings("unchecked")
    private T[] array = (T[]) new Comparable[10];


    @SuppressWarnings("unchecked")
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size >= array.length - 1) {
            T[] arrayTemp = (T[]) new Comparable[array.length * 2];
            for (int i = 1; i < size; i++) {
                arrayTemp[i] = array[i];
            }
            array = arrayTemp;
        }

        size++;
        int index = size;
        array[index] = item;

        addHelper(size);
    }

    /**
     * shifts the added item to its correct place
     * in the minimum binary heap tree.
     * @param index of item being added
     */

    protected void addHelper(int index) {


        if ((index > 1)
                && (array[(int) index / 2].compareTo(array[index]) > 0)) {
            T tmp = array[index];
            array[index] = array[(int) (index / 2)];
            array[(int) (index / 2)] = tmp;
            index = (int) index / 2;
            addHelper(index);
        }

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }

        return array[1];
    }

    public T remove() {
        T result = peek();
        array[1] = array[size];
        array[size] = null;
        size--;

        removeHelper(1);

        return result;
    }


    protected void removeHelper(int index) {

        if (index * 2 <= size) {
            int smallerChild = index * 2;

            if ((index * 2 + 1 <= size)
                    && array[2 * index].compareTo(array[index * 2 + 1]) > 0) {
                smallerChild = index * 2 + 1;
            }
            if (array[index].compareTo(array[smallerChild]) > 0) {
                T temp = array[index];
                array[index] = array[smallerChild];
                array[smallerChild] = temp;
            }
            index = smallerChild;
            removeHelper(index);
        }
    }

    public int size() {
        return size;
    }

    public T[] toArray() {
        return array;

    }
}
