package day19;

import java.util.*;

public class TopUniqueElements<E> extends PriorityQueue<E> {
    private final int capacity;
    private final HashSet<E> elements = new HashSet<>();
    public TopUniqueElements(int capacity, Comparator<? super E> comparator) {
        super(capacity, comparator.reversed());
        this.capacity = capacity;
    }

    @Override
    public boolean add(E e) {
        if(elements.add(e)) super.add(e);
        if(size() > capacity) super.poll();
        return size() <= capacity;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for(E e : c) {
            if(add(e)) {
                modified = true;
            }
        }
        return modified;
    }
}
