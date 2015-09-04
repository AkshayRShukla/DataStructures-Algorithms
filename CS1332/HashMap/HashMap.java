import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class HashMap<K, V> implements HashMapInterface<K, V>, Gradable<K, V> {

    private MapEntry<K, V>[] table;
    private int size = 0;

    public HashMap() {
        table = new MapEntry[STARTING_SIZE];
    }

    @Override
    public V add(K key, V value) {
        if ((key == null) || (value == null)) {
            throw new IllegalArgumentException();
        }
        MapEntry<K, V> toAdd = new MapEntry(key, value);
        size++;
        resize();
        size--;
        int i = arrayIndex(key);
        while (table[i] != null) {
            if (table[i].isRemoved()) {
                table[i] = toAdd;
                table[i].setRemoved(false);
                size++;
                return null;
            }
            if ((table[i].getKey()).equals(toAdd.getKey())
                    && !table[i].isRemoved()) {
                V toReturn = tableValue(i);
                table[i].setRemoved(false);
                table[i].setValue(value);
                return toReturn;
            }
            i = (i + 1) % table.length;
        }
        table[i] = toAdd;
        size++;
        return null;
    }
    /*
     * Resizes the array if the load factor exceeds the
     * specified limit.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        double load =  (double) size() /  (double) toArray().length;
        MapEntry<K, V>[] temp = table;
        if (MAX_LOAD_FACTOR <= load) {
            table = new MapEntry[temp.length * 2];
            for (int i = 0; i < temp.length - 1; i++) {
                if (temp[i] != null && !temp[i].isRemoved()) {
                    size--;
                    add(temp[i].getKey(), temp[i].getValue());
                }
            }
        }
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (size == 0) {
            return null;
        }
        int i = arrayIndex(key);
        int hold = i;
        while (table[i] != null) {
            if (!equalKeys(i, key)) {
                i = (i + 1) % table.length;
            } else if ((equalKeys(i, key))) {
                V toReturn = tableValue(i);
                table[i].setRemoved(true);
                size--;
                return toReturn;
            }
            if (i == hold) {
                return null;
            }
        }
        return null;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (contains(key)) {
            int i = arrayIndex(key);
            while (table[i] != null) {
                if (!equalKeys(i, key)) {
                    i = (i + 1) % table.length;
                } else if (equalKeys(i, key)) {
                    return tableValue(i);
                }
                if (table[i].isRemoved()) {
                    i = (i + 1) % table.length;
                }
            }
        }
        return null;
    }

    @Override
    public boolean contains(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (size == 0) {
            return false;
        }
        int i = arrayIndex(key);
        int hold = i;
        while ((table[i] != null)) {
            if (!equalKeys(i, key)) {
                i = (i + 1) % table.length;
                if (hold == i) {
                    return false;
                }
            } else {
                return (!table[i].isRemoved());
            }
        }
        return false;
    }

    @Override
    public void clear() {
        table = new MapEntry[STARTING_SIZE];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public MapEntry<K, V>[] toArray() {
        return table;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<K>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                set.add(table[i].getKey());
            }
        }
        return set;
    }


    @Override
    public List<V> values() {
        List<V> list = new ArrayList<V>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                list.add(tableValue(i));
            }
        }
        return list;
    }
    /**
     * Checks if two keys are equal.
     * @param index of where the key is in the array.
     * @param key
     * @return true if the keys are equal, otherwise false.
     */
    private boolean equalKeys(int index, K key) {
        return (table[index].getKey()).equals(key);
    }
    /**
     * Finds the index in the array of a particular key.
     * @param key
     * @return index of the key.
     */
    private int arrayIndex(K key) {
        int arrayIndex = Math.abs(key.hashCode() % table.length);
        return arrayIndex;
    }
    /**
     * Finds the value in the array of a particular index.
     * @param index
     * @return Value associated with the index.
     */
    private V tableValue(int index) {
        return table[index].getValue();
    }
}

