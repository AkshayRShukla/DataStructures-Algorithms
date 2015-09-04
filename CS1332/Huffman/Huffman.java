import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Iterator;

/**
 * Date: November 9th 2014
 * @author Akshay Shukla
 */

public class Huffman {

    // Do NOT add any variables (instance or static)

    /**
     * Builds a frequency map of characters for the given string.
     *
     * This should just be the count of each character.
     * No character not in the input string should be in the frequency map.
     *
     * @param s the string to map
     * @return the character -> Integer frequency map
     */
    public static Map<Character, Integer> buildFrequencyMap(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        HashMap<Character, Integer> toReturn = new HashMap<>();
        for (char key : s.toCharArray()) {
            Integer previous = toReturn.remove(key);
            Integer next;
            if (previous != null) {
                next = previous + 1;
            } else {
                next = 1;
            }
            toReturn.put(key, next);
        }
        return toReturn;

    }

    /**
     * Build the Huffman tree using the frequencies given.
     *
     * Add the nodes to the tree based on their natural ordering (the order
     * given by the compareTo method).
     * The frequency map will not necessarily come from the
     * {@code buildFrequencyMap()} method. Every entry in the map should be in
     * the tree.
     *
     * @param freq the frequency map to represent
     * @return the root of the Huffman Tree
     */
    public static HuffmanNode buildHuffmanTree(Map<Character, Integer> freq) {
        if (freq == null) {
            throw new IllegalArgumentException();
        }
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<HuffmanNode>();

        for (Map.Entry<Character, Integer> pair : freq.entrySet()) {
            queue.add(new HuffmanNode(pair.getKey(), pair.getValue()));
        }

        while (queue.size() > 1) {
            HuffmanNode a = queue.poll();
            HuffmanNode b = queue.poll();
            queue.add(new HuffmanNode(a, b));
        }

        return queue.poll();
    }

    /**
     * Traverse the tree and extract the encoding for each character in the
     * tree.
     *
     * The tree provided will be a valid huffman tree but may not come from the
     * {@code buildHuffmanTree()} method.
     *
     * @param tree the root of the Huffman Tree
     * @return the map of each character to the encoding string it represents
     */
    public static Map<Character, EncodedString> buildEncodingMap(
            HuffmanNode tree) {
        if (tree == null) {
            throw new IllegalArgumentException();
        }
        HashMap<Character, EncodedString> toReturn = new HashMap<>();
        if (tree.getLeft() == null && tree.getRight() == null) {
            EncodedString temp = new EncodedString();
            temp.zero();
            toReturn.put((Character) tree.getCharacter(), temp);
            return toReturn;
        }

        encodeHelper(tree, new EncodedString(), toReturn);

        return toReturn;
    }

    /**
     * Encode Helper for buildEncodingmap
     *
     * @param node the node being encoded.
     * @param current encoded String
     * @param the map of each character to the encoding string
     */

    private static void encodeHelper(HuffmanNode node, EncodedString current,
            Map<Character, EncodedString> map) {
        if (node.getLeft() == null && node.getRight() == null) {
            map.put(node.getCharacter(), current);
        }

        if (node.getLeft() != null) {
            EncodedString encodedString = new EncodedString();
            encodedString.concat(current);
            encodedString.zero();
            encodeHelper(node.getLeft(), encodedString, map);
        }

        if (node.getRight() != null) {
            EncodedString encodedString = new EncodedString();
            encodedString.concat(current);
            encodedString.one();
            encodeHelper(node.getRight(), encodedString, map);
        }
    }

    /**
     * Encode each character in the string using the map provided.
     *
     * If a character in the string doesn't exist in the map ignore it.
     *
     * The encoding map may not necessarily come from the
     * {@code buildEncodingMap()} method, but will be correct for the tree given
     * to {@code decode()} when decoding this method's output.
     *
     * @param encodingMap the map of each character to the encoding string it
     * represents
     * @param s the string to encode
     * @return the encoded string
     */
    public static EncodedString encode(Map<Character, EncodedString>
    encodingMap, String s) {
        if (encodingMap == null || s == null) {
            throw new IllegalArgumentException();
        }
        EncodedString toReturn = new EncodedString();
        for (char key : s.toCharArray()) {
            EncodedString encodedString = encodingMap.get(key);
            if (encodedString != null) {
                toReturn.concat(encodedString);
                //easier to concat then join.
            }
        }
        return toReturn;
    }

    /**
     * Decode the encoded string using the tree provided.
     *
     * The encoded string may not necessarily come from {@code encode()}, but
     * will be a valid string for the given tree.
     *
     * (tip: use StringBuilder to make this method faster -- concatenating
     * strings is SLOW.)
     *
     * @param tree the tree to use to decode the string
     * @param es the encoded string
     * @return the decoded string
     */
    public static String decode(HuffmanNode tree, EncodedString es) {
        if (tree == null || es == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder toReturn = new StringBuilder();

        HuffmanNode current = tree;
        Iterator<Byte> iterator = es.iterator();
        while (iterator.hasNext()) {

            Byte bit = iterator.next();
            //decode time!
            if (current != null) {
                if (bit == 0) {
                    if (current.getLeft() != null
                            && current.getRight() != null) {
                        current = current.getLeft();
                    }
                } else if (bit == 1) {
                    current = current.getRight();
                }
            }

            if (current.getLeft() == null && current.getRight() == null) {
                toReturn.append(current.getCharacter());
                current = tree;
            }
        }

        return toReturn.toString();
    }
}

