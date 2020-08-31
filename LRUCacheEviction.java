package nordstrom.codingexercise;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.HashMap;

public class LRUCacheEviction<K,V> {
    class CacheObject<T,U> {

        CacheObject<T, U> previous;
        CacheObject<T, U> next;
        T key;
        U value;

        public CacheObject(CacheObject<T, U> previous, T key, U value, CacheObject<T, U> next) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.previous = previous;
        }

        @Override
        public String toString() {
            return " " +
                    (previous == null ? null : previous.value) + " <<-- " +
                    value +
                    " -->> " + (next == null ? null : next.value);
        }
    }

    private final int capacity;
    private CacheObject leastRecentlyUsed;
    private CacheObject mostRecentlyUsed;
    private final HashMap<K, CacheObject> cache;

    public LRUCacheEviction() {
        capacity = createSpecificCacheSize();
        this.cache = new HashMap<>(capacity);
    }

    public int createSpecificCacheSize() {
        System.out.println("Please specify the size (# of entries) for your memory cache: ");
        Scanner scanner = new Scanner(System.in);
        int cacheSize;
        while (true) {
            try {
                cacheSize = scanner.nextInt();
                return cacheSize;
            } catch (InputMismatchException e) {
                System.out.println("That is not a valid size for your memory cache. Try again.");
                scanner.next();
            }
        }
    }

    public void add(K key, V value) {
        CacheObject<K, V> fetchedObject = cache.get(key);
        // if key exists, tries to fetch its corresponding cache object, and replace its value
        if (cache.containsKey(key)) {
            fetchedObject.value = value;
            removeLeastRecentlyUsedNode(fetchedObject);
            shiftMostRecentlyUsedNode(fetchedObject);
        } else {
            // removes most recently accessed cache object
            if (cache.size() == capacity) {
                System.out.println("The max capacity of the cache has been reached.");
                cache.remove(leastRecentlyUsed.key);
                removeLeastRecentlyUsedNode(leastRecentlyUsed);
            }
            // if key doesn't exist, creates new cache object & adds it to head (least recently accessed)
            CacheObject<K, V> createdObject = new CacheObject<K, V>(leastRecentlyUsed, key, value, null);
            shiftMostRecentlyUsedNode(createdObject);
            cache.put(key, createdObject);
        }
    }

    public V get(K key) {
        CacheObject<K, V> fetchedObject = cache.get(key);
        removeLeastRecentlyUsedNode(fetchedObject);
        shiftMostRecentlyUsedNode(fetchedObject);
        return fetchedObject != null ? fetchedObject.value : null;
    }

    protected Boolean exists(K key) {
        CacheObject<K, V> fetchedObject = cache.get(key);
        removeLeastRecentlyUsedNode(fetchedObject);
        shiftMostRecentlyUsedNode(fetchedObject);
        boolean elementExists;
        if (cache.containsKey(key)) {
            elementExists = true;
        } else {
            elementExists = false;
        }
        return elementExists;
    }

    protected void print() {
        CacheObject<K, V> currentHead = leastRecentlyUsed;
        while (currentHead != null) {
            System.out.print(currentHead.value + " -> ");
            currentHead = currentHead.next;
        }
        System.out.println();
    }

    private void removeLeastRecentlyUsedNode (CacheObject<K, V> node) {
        if (node == null)
            return;
        CacheObject prevSpace = node.previous;
        CacheObject nextSpace = node.next;

        if (prevSpace != null) {
            prevSpace.next = nextSpace;
        } else {
            leastRecentlyUsed = nextSpace;
        }
        if (nextSpace != null) {
            nextSpace.previous = prevSpace;
        } else {
            mostRecentlyUsed = prevSpace;
        }
    }

    private void shiftMostRecentlyUsedNode (CacheObject<K, V> node) {
        if (node == null)
            return;
        CacheObject prevSpace = node.previous;
        CacheObject nextSpace = node.next;

        if (leastRecentlyUsed == null) {
            leastRecentlyUsed = mostRecentlyUsed = node;
        } else {
            mostRecentlyUsed.next = node;
            prevSpace = mostRecentlyUsed;
            nextSpace = null;
            mostRecentlyUsed = node;
        }
    }
}
