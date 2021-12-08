package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the list interface based on linked nodes
 * that store multiple items per node.  Rules for adding and removing
 * elements ensure that each node (except possibly the last one)
 * is at least half full.
 *
 * @author Charles Yang
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E> {
    public static void main(String[] args) {
        StoutList<Integer> list = new StoutList<>();
        for (int i = 10; i >= 0; i--) list.add(i);

        System.out.println(list.toStringInternal());

        list.sort();

        System.out.println(list.toStringInternal());
    }

    /**
     * Default number of elements that may be stored in each node.
     */
    private static final int DEFAULT_NODESIZE = 4;

    /**
     * Number of elements that can be stored in each node.
     */
    private final int nodeSize;

    /**
     * Dummy node for head.  It should be private but set to public here only
     * for grading purpose.  In practice, you should always make the head of a
     * linked list a private instance variable.
     */
    public Node head;

    /**
     * Dummy node for tail.
     */
    private Node tail;

    /**
     * Number of elements in the list.
     */
    private int size;

    /**
     * Constructs an empty list with the default node size.
     */
    public StoutList() {
        this(DEFAULT_NODESIZE);
    }

    /**
     * Constructs an empty list with the given node size.
     *
     * @param nodeSize number of elements that may be stored in each node, must be
     *                 an even number
     */
    public StoutList(int nodeSize) {
        if (nodeSize <= 0 || nodeSize % 2 != 0) throw new IllegalArgumentException();
        this.nodeSize = nodeSize;

        // dummy nodes
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.previous = head;

    }

    /**
     * Constructor for grading only.  Fully implemented.
     *
     * @param head
     * @param tail
     * @param nodeSize
     * @param size
     */
    public StoutList(Node head, Node tail, int nodeSize, int size) {
        this.head = head;
        this.tail = tail;
        this.nodeSize = nodeSize;
        this.size = size;
    }

    @Override
    public int size() {
        return this.size;
    }

    /**
     * Helper for linking a new node
     *
     * @param newGuy node to insert
     * @param oldGuy node proceeding insertion location
     */
    private void link(Node newGuy, Node oldGuy) {
        newGuy.next = oldGuy;
        newGuy.previous = oldGuy.previous;

        newGuy.previous.next = newGuy;

        oldGuy.previous = newGuy;
    }

    @Override
    public boolean add(E item) {
        if (item == null) throw new NullPointerException();

        if (size > 0 && tail.previous.count < nodeSize) {
            tail.previous.addItem(item);
        }
        else {
            Node newNode = new Node();
            newNode.addItem(item);
            link(newNode, tail);
        }

        size++;

        return true;
    }

    // returns the node and offset for the given logical index
    private NodeInfo find(int pos) {
        int count = 0;
        Node currNode = head;

        while (currNode.next != null && count <= pos) {
            currNode = currNode.next;
            count += currNode.count;
        }

        return new NodeInfo(currNode, pos - (count - currNode.count));
    }

    private NodeInfo add(Node n, int offset, E item) {
        if (n.previous != head && offset == 0) {
            if (n.previous.count < nodeSize) {
                n.previous.addItem(item);
            }
            else {
                Node loner = new Node();
                loner.addItem(item);
                link(loner, n);
            }
            return new NodeInfo(n.previous, n.previous.count - 1);
        }

        if (n.count < nodeSize) {
            n.addItem(offset, item);
            return new NodeInfo(n, offset);
        }
        else {
            Node loner = new Node();
            link(loner, n.next);

            // split operation
            for (int i = 0; i < nodeSize / 2; i++) {
                loner.addItem(n.data[nodeSize / 2]);
                n.removeItem(nodeSize / 2);
            }

            if (offset <= nodeSize / 2) {
                n.addItem(offset, item);
                return new NodeInfo(n, offset);
            }
            else {
                loner.addItem(offset - nodeSize / 2, item);
                return new NodeInfo(loner, offset - nodeSize / 2);
            }
        }
    }


    @Override
    public void add(int pos, E item) {
        if (item == null) throw new NullPointerException();
        if (pos > size) throw new IndexOutOfBoundsException();

        NodeInfo n = find(pos);
        add(n.node, n.offset, item);

        size++;
    }

    @Override
    public E remove(int pos) {
        if (pos >= size) throw new IndexOutOfBoundsException();

        // Get node and offset of node containing pos
        NodeInfo n = find(pos);
        E out = n.node.data[n.offset];

        if (n.node == tail.previous && n.node.count == 1) {
            n.node.previous.next = tail;
            tail.previous = n.node.previous;
        }
        else if (n.node == tail.previous || n.node.count > nodeSize / 2) {
            //n.node.removeItem(n.offset);
        }
        else if (n.node.next.count > nodeSize / 2) {
            n.node.addItem(n.node.next.data[0]);
            n.node.next.removeItem(0);
        }
        else {
            while (n.node.next.count > 0) {
                n.node.addItem(n.node.next.data[0]);
                n.node.next.removeItem(0);
            }
            // Deleting merged node
            n.node.next.next.previous = n.node;
            n.node.next = n.node.next.next;
        }

        // Finally actually removing item
        n.node.removeItem(n.offset);
        size--;
        return out;
    }

    /**
     * Sort all elements in the stout list in the INCREASING order. You may do the following.
     * Traverse the list and copy its elements into an array, deleting every visited node along
     * the way.  Then, sort the array by calling the insertionSort() method.  (Note that sorting
     * efficiency is not a concern for this project.)  Finally, copy all elements from the array
     * back to the stout list, creating new nodes for storage. After sorting, all nodes but
     * (possibly) the last one must be full of elements.
     * <p>
     * Comparator<E> must have been implemented for calling insertionSort().
     */
    public void sort() {
        // Copy list to array, sort
        E[] arr = (E[]) new Comparable[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = this.remove(0);
        }
        insertionSort(arr, new GenericComparator());

        // Add sorted array back to list
        for (int i = 0; i < arr.length; i++) {
            add(arr[i]);
        }
    }

    /**
     * Sort all elements in the stout list in the DECREASING order. Call the bubbleSort()
     * method.  After sorting, all but (possibly) the last nodes must be filled with elements.
     * <p>
     * Comparable<? super E> must be implemented for calling bubbleSort().
     */
    public void sortReverse() {
        // Copy list to array, sort
        E[] arr = (E[]) new Comparable[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = this.remove(0);
        }
        bubbleSort(arr);

        // Add sorted array back to list
        for (int i = 0; i < arr.length; i++) {
            add(arr[i]);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new StoutIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new StoutListIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new StoutListIterator(index);
    }

    /**
     * Returns a string representation of this list showing
     * the internal structure of the nodes.
     */
    public String toStringInternal() {
        return toStringInternal(null);
    }

    /**
     * Returns a string representation of this list showing the internal
     * structure of the nodes and the position of the iterator.
     *
     * @param iter an iterator for this list
     */
    public String toStringInternal(ListIterator<E> iter) {
        int count = 0;
        int position = -1;
        if (iter != null) {
            position = iter.nextIndex();
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        Node current = head.next;
        while (current != tail) {
            sb.append('(');
            E data = current.data[0];
            if (data == null) {
                sb.append("-");
            }
            else {
                if (position == count) {
                    sb.append("| ");
                    position = -1;
                }
                sb.append(data.toString());
                ++count;
            }

            for (int i = 1; i < nodeSize; ++i) {
                sb.append(", ");
                data = current.data[i];
                if (data == null) {
                    sb.append("-");
                }
                else {
                    if (position == count) {
                        sb.append("| ");
                        position = -1;
                    }
                    sb.append(data.toString());
                    ++count;

                    // iterator at end
                    if (position == size && count == size) {
                        sb.append(" |");
                        position = -1;
                    }
                }
            }
            sb.append(')');
            current = current.next;
            if (current != tail)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Normal Iterator for StoutList
     */
    private class StoutIterator implements Iterator<E> {
        /**
         * Node the iterator cursor is on
         */
        Node curr;

        /**
         * next offset in each node
         */
        int nextOff;

        /**
         * next logical index
         */
        int nextIndex;

        /**
         * Constructs a basic iterator for StoutList
         */
        public StoutIterator() {
            nextIndex = 0;
            nextOff = 0;
            curr = head.next;
        }


        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();

            if (nextOff >= curr.count) {
                curr = curr.next;
                nextOff = 0;
            }

            nextIndex++;
            return curr.data[nextOff++];
        }
    }

    /**
     * Struct representing a Node and the offset of a given logical index pos
     */
    private class NodeInfo {
        public Node node;
        public int offset;

        /**
         * Constructor
         *
         * @param node   the Node in question
         * @param offset subindex of pos
         */
        public NodeInfo(Node node, int offset) {
            this.node = node;
            this.offset = offset;
        }
    }

    /**
     * Comparator class used in insertionSort()
     */
    private class GenericComparator implements Comparator<E> {

        /**
         * Compares its two arguments for order.  Returns a negative integer,
         * zero, or a positive integer as the first argument is less than, equal
         * to, or greater than the second.<p>
         * <p>
         * In the foregoing description, the notation
         * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
         * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
         * <tt>0</tt>, or <tt>1</tt> according to whether the value of
         * <i>expression</i> is negative, zero or positive.<p>
         * <p>
         * The implementor must ensure that <tt>sgn(compare(x, y)) ==
         * -sgn(compare(y, x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
         * implies that <tt>compare(x, y)</tt> must throw an exception if and only
         * if <tt>compare(y, x)</tt> throws an exception.)<p>
         * <p>
         * The implementor must also ensure that the relation is transitive:
         * <tt>((compare(x, y)&gt;0) &amp;&amp; (compare(y, z)&gt;0))</tt> implies
         * <tt>compare(x, z)&gt;0</tt>.<p>
         * <p>
         * Finally, the implementor must ensure that <tt>compare(x, y)==0</tt>
         * implies that <tt>sgn(compare(x, z))==sgn(compare(y, z))</tt> for all
         * <tt>z</tt>.<p>
         * <p>
         * It is generally the case, but <i>not</i> strictly required that
         * <tt>(compare(x, y)==0) == (x.equals(y))</tt>.  Generally speaking,
         * any comparator that violates this condition should clearly indicate
         * this fact.  The recommended language is "Note: this comparator
         * imposes orderings that are inconsistent with equals."
         *
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         * @return a negative integer, zero, or a positive integer as the
         * first argument is less than, equal to, or greater than the
         * second.
         * @throws NullPointerException if an argument is null and this
         *                              comparator does not permit null arguments
         * @throws ClassCastException   if the arguments' types prevent them from
         *                              being compared by this comparator.
         */
        @Override
        public int compare(E o1, E o2) {
            return o1.compareTo(o2);
        }
    }

    /**
     * Node type for this list.  Each node holds a maximum
     * of nodeSize elements in an array.  Empty slots
     * are null.
     */
    private class Node {
        /**
         * Array of actual data elements.
         */
        // Unchecked warning unavoidable.
        public E[] data = (E[]) new Comparable[nodeSize];

        /**
         * Link to next node.
         */
        public Node next;

        /**
         * Link to previous node;
         */
        public Node previous;

        /**
         * Index of the next available offset in this node, also
         * equal to the number of elements in this node.
         */
        public int count;

        /**
         * Adds an item to this node at the first available offset.
         * Precondition: count < nodeSize
         *
         * @param item element to be added
         */
        void addItem(E item) {
            if (count >= nodeSize) {
                return;
            }
            data[count++] = item;
            //useful for debugging
            //      System.out.println("Added " + item.toString() + " at index " + count + " to node "  + Arrays.toString(data));
        }

        /**
         * Adds an item to this node at the indicated offset, shifting
         * elements to the right as necessary.
         * <p>
         * Precondition: count < nodeSize
         *
         * @param offset array index at which to put the new element
         * @param item   element to be added
         */
        void addItem(int offset, E item) {
            if (count >= nodeSize) {
                return;
            }
            for (int i = count - 1; i >= offset; --i) {
                data[i + 1] = data[i];
            }
            ++count;
            data[offset] = item;
            //useful for debugging
//      System.out.println("Added " + item.toString() + " at index " + offset + " to node: "  + Arrays.toString(data));
        }

        /**
         * Deletes an element from this node at the indicated offset,
         * shifting elements left as necessary.
         * Precondition: 0 <= offset < count
         *
         * @param offset
         */
        void removeItem(int offset) {
            E item = data[offset];
            for (int i = offset + 1; i < nodeSize; ++i) {
                data[i - 1] = data[i];
            }
            data[count - 1] = null;
            --count;
        }
    }


    private class StoutListIterator implements ListIterator<E> {
        // instance variables ...
        /**
         * Node the iterator cursor is on
         */
        Node curr;

        /**
         * next offset in each node
         */
        int nextOff;

        /**
         * Last operation performed. -1: previous, 0: add/remove/none, 1: next
         */
        int lastOp;

        /**
         * next logical index
         */
        int nextIndex;

        /**
         * Default constructor
         */
        public StoutListIterator() {
            nextIndex = 0;
            nextOff = 0;
            curr = head.next;
            lastOp = 0;
        }

        /**
         * Constructor finds node at a given position.
         *
         * @param pos
         */
        public StoutListIterator(int pos) {
            nextIndex = pos;
            NodeInfo temp = find(pos);
            curr = temp.node;
            nextOff = temp.offset;
            lastOp = 0;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();

            if (nextOff >= curr.count) {
                curr = curr.next;
                nextOff = 0;
            }

            nextIndex++;
            lastOp = 1;
            return curr.data[nextOff++];
        }

        /**
         * Returns {@code true} if this list iterator has more elements when
         * traversing the list in the reverse direction.  (In other words,
         * returns {@code true} if {@link #previous} would return an element
         * rather than throwing an exception.)
         *
         * @return {@code true} if the list iterator has more elements when
         * traversing the list in the reverse direction
         */
        @Override
        public boolean hasPrevious() {
            return nextIndex - 1 >= 0;
        }

        /**
         * Returns the previous element in the list and moves the cursor
         * position backwards.  This method may be called repeatedly to
         * iterate through the list backwards, or intermixed with calls to
         * {@link #next} to go back and forth.  (Note that alternating calls
         * to {@code next} and {@code previous} will return the same
         * element repeatedly.)
         *
         * @return the previous element in the list
         * @throws NoSuchElementException if the iteration has no previous
         *                                element
         */
        @Override
        public E previous() {
            if (!hasPrevious()) throw new NoSuchElementException();

            if (nextOff < 1) {
                curr = curr.previous;
                nextOff = curr.count;
            }

            nextIndex--;
            lastOp = -1;
            return curr.data[--nextOff];
        }

        /**
         * Returns the index of the element that would be returned by a
         * subsequent call to {@link #next}. (Returns list size if the list
         * iterator is at the end of the list.)
         *
         * @return the index of the element that would be returned by a
         * subsequent call to {@code next}, or list size if the list
         * iterator is at the end of the list
         */
        @Override
        public int nextIndex() {
            return nextIndex;
        }

        /**
         * Returns the index of the element that would be returned by a
         * subsequent call to {@link #previous}. (Returns -1 if the list
         * iterator is at the beginning of the list.)
         *
         * @return the index of the element that would be returned by a
         * subsequent call to {@code previous}, or -1 if the list
         * iterator is at the beginning of the list
         */
        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }

        /**
         * Removes from the list the last element that was returned by {@link
         * #next} or {@link #previous} (optional operation).  This call can
         * only be made once per call to {@code next} or {@code previous}.
         * It can be made only if {@link #add} has not been
         * called after the last call to {@code next} or {@code previous}.
         *
         * @throws UnsupportedOperationException if the {@code remove}
         *                                       operation is not supported by this list iterator
         * @throws IllegalStateException         if neither {@code next} nor
         *                                       {@code previous} have been called, or {@code remove} or
         *                                       {@code add} have been called after the last call to
         *                                       {@code next} or {@code previous}
         */
        @Override
        public void remove() {
            switch (lastOp) {
                case 0:
                    throw new IllegalStateException();
                case -1:
                    StoutList.this.remove(nextIndex);
                    break;
                case 1:

                    StoutList.this.remove(--nextIndex);
                    nextOff = StoutList.this.find(nextIndex).offset;
                    break;
            }

            lastOp = 0;
        }

        /**
         * Replaces the last element returned by {@link #next} or
         * {@link #previous} with the specified element (optional operation).
         * This call can be made only if neither {@link #remove} nor {@link
         * #add} have been called after the last call to {@code next} or
         * {@code previous}.
         *
         * @param e the element with which to replace the last element returned by
         *          {@code next} or {@code previous}
         * @throws UnsupportedOperationException if the {@code set} operation
         *                                       is not supported by this list iterator
         * @throws ClassCastException            if the class of the specified element
         *                                       prevents it from being added to this list
         * @throws IllegalArgumentException      if some aspect of the specified
         *                                       element prevents it from being added to this list
         * @throws IllegalStateException         if neither {@code next} nor
         *                                       {@code previous} have been called, or {@code remove} or
         *                                       {@code add} have been called after the last call to
         *                                       {@code next} or {@code previous}
         */
        @Override
        public void set(E e) {
            switch (lastOp) {
                case 0:
                    throw new IllegalStateException();
                case -1:
                    curr.data[nextOff] = e;
                    break;
                case 1:
                    curr.data[nextOff - 1] = e;
                    break;
            }
        }

        /**
         * Inserts the specified element into the list (optional operation).
         * The element is inserted immediately before the element that
         * would be returned by {@link #next}, if any, and after the element
         * that would be returned by {@link #previous}, if any.  (If the
         * list contains no elements, the new element becomes the sole element
         * on the list.)  The new element is inserted before the implicit
         * cursor: a subsequent call to {@code next} would be unaffected, and a
         * subsequent call to {@code previous} would return the new element.
         * (This call increases by one the value that would be returned by a
         * call to {@code nextIndex} or {@code previousIndex}.)
         *
         * @param e the element to insert
         * @throws UnsupportedOperationException if the {@code add} method is
         *                                       not supported by this list iterator
         * @throws ClassCastException            if the class of the specified element
         *                                       prevents it from being added to this list
         * @throws IllegalArgumentException      if some aspect of this element
         *                                       prevents it from being added to this list
         */
        @Override
        public void add(E e) {
            NodeInfo n = find(nextIndex);
            StoutList.this.add(n.node, n.offset, e);
            next();
            size++;
            lastOp = 0;
        }

        // Other methods you may want to add or override that could possibly facilitate
        // other operations, for instance, addition, access to the previous element, etc.
        //
        // ...
        //
    }


    /**
     * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING order.
     *
     * @param arr  array storing elements from the list
     * @param comp comparator used in sorting
     */
    private void insertionSort(E[] arr, Comparator<? super E> comp) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (comp.compare(arr[j], arr[j - 1]) < 0) swap(arr, j, j - 1);
                else {
                    break;
                }
            }
        }
    }

    /**
     * Swaps elements at index a and b in arr
     * @param arr Array in which elements are being swapped
     * @param a index 1
     * @param b index 2
     */
    private void swap(E[] arr, int a, int b) {
        E temp = arr[b];
        arr[b] = arr[a];
        arr[a] = temp;
    }

    /**
     * Sort arr[] using the bubble sort algorithm in the DECREASING order. For a
     * description of bubble sort please refer to Section 6.1 in the project description.
     * You must use the compareTo() method from an implementation of the Comparable
     * interface by the class E or ? super E.
     *
     * @param arr array holding elements from the list
     */
    private void bubbleSort(E[] arr) {
        for (int i = 0; i < arr.length - 1; i++)
            for (int j = 0; j < arr.length - i - 1; j++)
                if (arr[j].compareTo(arr[j + 1]) < 0) {
                    swap(arr, j+1, j);
                }
    }
}