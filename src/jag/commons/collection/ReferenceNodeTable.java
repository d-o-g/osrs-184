package jag.commons.collection;

public final class ReferenceNodeTable<T> {

    final int capacity;
    final IterableNodeTable<LinkedReference<T>> table;
    final IterableDoublyLinkedNodeQueue<LinkedReference<T>> queue;
    int remaining;
    ReferenceSweeper sweeper;

    public ReferenceNodeTable(int capacity, int maximumCapacity) {
        this.queue = new IterableDoublyLinkedNodeQueue<>();
        this.capacity = capacity;
        this.remaining = capacity;

        int size = 1;
        while (size + size < capacity && size < maximumCapacity) {
            size += size;
        }

        this.table = new IterableNodeTable<>(size);
    }

    public void relegate(int index) {
        for (LinkedReference<T> ref = queue.first(); ref != null; ref = queue.next()) {
            if (ref.isSoft()) {
                if (ref.getReferent() == null) {
                    ref.unlink();
                    ref.unlinkDoubly();
                    remaining += ref.size;
                }
            } else if (++ref.doublyKey > (long) index) {
                SoftLinkedReference<T> soft = new SoftLinkedReference<T>(ref.getReferent(), ref.size);
                table.put(soft, ref.key);
                IterableDoublyLinkedNodeQueue.insertBefore(soft, ref);
                ref.unlink();
                ref.unlinkDoubly();
            }
        }

    }

    void remove(long key) {
        LinkedReference<T> ref = table.lookup(key);
        remove(ref);
    }

    void remove(LinkedReference<T> ref) {
        if (ref != null) {
            ref.unlink();
            ref.unlinkDoubly();
            remaining += ref.size;
        }

    }

    public T get(long key) {
        LinkedReference<T> ref = table.lookup(key);
        if (ref == null) {
            return null;
        }

        T value = ref.getReferent();
        if (value == null) {
            ref.unlink();
            ref.unlinkDoubly();
            remaining += ref.size;
            return null;
        }

        if (ref.isSoft()) {
            HardLinkedReference<T> hard = new HardLinkedReference<>(value, ref.size);
            table.put(hard, ref.key);
            queue.insert(hard);
            hard.doublyKey = 0L;
            ref.unlink();
            ref.unlinkDoubly();
        } else {
            queue.insert(ref);
            ref.doublyKey = 0L;
        }

        return value;
    }

    public void put(T value, long key, int size) {
        if (size > capacity) {
            throw new IllegalStateException();
        }

        remove(key);
        remaining -= size;

        while (remaining < 0) {
            LinkedReference<T> ref = queue.pop();
            if (ref == null) {
                throw new RuntimeException("");
            }

            remove(ref);
            if (sweeper != null) {
                sweeper.sweep(ref.getReferent());
            }
        }

        HardLinkedReference<T> ref = new HardLinkedReference<>(value, size);
        table.put(ref, key);
        queue.insert(ref);
        ref.doublyKey = 0L;
    }

    public void clear() {
        queue.clear();
        table.clear();
        remaining = capacity;
    }
}
