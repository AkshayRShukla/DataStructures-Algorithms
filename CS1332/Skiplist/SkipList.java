import java.util.HashSet;
import java.util.Set;

public class SkipList<T extends Comparable<? super T>>
    implements SkipListInterface<T> {
    private CoinFlipper coinFlipper;
    private int size;
    private Node<T> head;


    /**
     * Constructs a SkipList object that stores data in ascending order
     * when an item is inserted, the flipper is called until it returns a tails
     * if for an item the flipper returns n heads, the corresponding node has
     * n + 1 levels.
     *
     * @param coinFlipper the source of randomness
     */
    public SkipList(CoinFlipper coinFlipper) {
        this.coinFlipper = coinFlipper;
        head = new Node<T>(null, 0);
        size = 0;
    }

    //@Override
    public T first() {
        if (size == 0) {
            return null;
        }
        Node<T> current = head;
        while (current.getDown() != null) {
            current = current.getDown();
        }
        return current.getNext().getData();
    }



    //@Override
    public T last() {
        if (size == 0) {
            return null;
        }
        Node<T> current = head;
        while (current.getDown() != null) {
            current = current.getDown();
        }
        while (current.getNext() != null) {
            current = current.getNext();
        }
        return current.getData();
    }

    //@Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        boolean tf = false;
        Node<T> current = head;
        while (current != null) {
            while (current.getNext() != null
                    && comparisonHelper(current, data)
                    < 0) {
                current = current.getNext();
            }
            if (current.getNext() != null
                    && (comparisonHelper(current, data)
                            == 0)) {
                tf = true;
            }
            current = current.getDown();
        }
        return tf;

    }
    //@Override
    public void put(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        int levelCounter =  0;

        //get number of levels.
        while (coinFlipper.flipCoin() == CoinFlipper.Coin.HEADS) {
            levelCounter++;
        }

        Node<T> inserted = new Node<T>(data, levelCounter);
        Node<T> previous;
        Node<T> post;

        if (levelCounter > head.getLevel()) {
            // check if level you are at is more than max level
            for (int i = 0; i < (levelCounter - head.getLevel()); i++) {
                T headData = head.getData();
                int levelAbove = head.getLevel() + 1;
                Node<T> above = new Node<T>(headData,
                        levelAbove);
                above.setDown(head);
                head.setUp(above);
                head = above;
            }
        }

        previous = head;
        post = head.getNext();
        while (previous != null) {
            post = previous.getNext();
            //keep moving forward until either
            //at the end of linked list or
            //data inside new Node is less than next Node
            while (post != null
                    && (inserted.getData().compareTo(post.getData())
                            > 0)) {
                previous = post;
                post = post.getNext();
            }
            int oldLevel = previous.getLevel();
            if (levelCounter >= oldLevel) {
                previous.setNext(inserted);
                inserted.setNext(post);
                int levelBelow = previous.getLevel() - 1;
                Node<T> holder = new Node<T>(data, levelBelow);
                holder.setUp(inserted);
                inserted.setDown(holder);
                inserted = holder;
            }
            previous = previous.getDown();
        }
        size++;
    }

    //@Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        Node<T> current = head;
        while (current != null) {
            while ((comparisonHelper(current, data) < 0)
                    && (current.getNext() != null)) {

                current = current.getNext();
            }

            if (current.getNext() != null
                    && (comparisonHelper(current, data)
                            == 0)) {
                return data;
            }
            current = current.getDown();
        }
        return null;
    }

    /**
     * Checks the value of a comparison.
     * @param A is node
     * @param D is data being compared with data in Node
     * @return the last element in the skip list, or null
     */
    private int comparisonHelper(Node<T> a, T d) {
        if (a.getNext() != null) {
            int x = a.getNext().getData().compareTo(d);
            return x;
        } else {
            return 0; //not best solution,
            //but prevents null pointer exception
        }
    }


    //@Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        } else if (!contains(data)) {
            return null;
        }
        Node<T> previous = head;
        Node<T> post = head.getNext();
        Node<T> toReturn = null;

        while (previous != null) {
            post = previous.getNext();
            while (post != null
                    && (data.compareTo(post.getData())
                            > 0)) {
                previous = post;
                post = post.getNext();
            }
            if (previous.getNext() != null
                    && comparisonHelper(previous, data)
                    == 0) {
                toReturn = previous.getNext();
                previous.setNext(previous.getNext().getNext());
            }
            previous = previous.getDown();
        }
        size--;
        return toReturn.getData();
    }


    //@Override
    public int size() {
        return size;
    }

    //@Override
    public void clear() {
        head = new Node<T>(null, 0);
        size = 0;
    }

    //@Override
    public Set<T> dataSet() {
        HashSet<T> hash = new HashSet<T>();
        Node<T> current = head;
        while (current.getDown() != null) {
            current = current.getDown();
        }
        while (current.getNext() != null) {
            hash.add(current.getNext().getData());
            current = current.getNext();
        }
        return hash;
    }
}
