package nordstrom.codingexercise;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class RandomCacheEviction<K,V> {
    class RandomCacheObject<T, U> {

        int toBeEvicted;
        T key;
        U value;

        public RandomCacheObject(int toBeEvicted, T key, U value) {
            this.key = key;
            this.value = value;
            this.toBeEvicted = toBeEvicted;
        }
    }

    private final int capacity;
    private final HashMap<K, RandomCacheObject<K,V>> cache;


    public RandomCacheEviction() {
        capacity = create();
        cache = new HashMap<>(capacity);
    }

    public int create() {
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
        RandomCacheObject<K, V> fetchedObject = cache.get(key);
        // if key exists, tries to fetch its corresponding cache object, and replace its value
        if (cache.containsKey(key)) {
            fetchedObject.value = value;
        } else {
            // removes random cache object
            if (cache.size() == capacity) {
                System.out.println("The max capacity of the cache has been reached.");
                removeRandomNode(fetchedObject);
            }
            // if key doesn't exist, creates new cache object
            RandomCacheObject<K, V> createdObject = new RandomCacheObject<K, V>(0, key, value);
            cache.put(key, createdObject);
        }
    }

    public V get(K key) {
        RandomCacheObject<K, V> fetchedObject = cache.get(key);
        removeRandomNode(fetchedObject);
        return fetchedObject != null ? fetchedObject.value : null;
    }

    protected Boolean exists(K key) {
        RandomCacheObject<K, V> fetchedObject = cache.get(key);
        removeRandomNode(fetchedObject);
        boolean elementExists;
        if (cache.containsKey(key)) {
            elementExists = true;
        } else {
            elementExists = false;
        }
        return elementExists;
    }

    private int generateRandom() {
        Random rand = new Random();
        int cacheSize = 4;
        int cacheIndexNo = rand.nextInt(cacheSize);
        return cacheIndexNo;
    }

    private void removeRandomNode(RandomCacheObject<K, V> node) {
        node.toBeEvicted = generateRandom();
        if (node.toBeEvicted == capacity) {
            cache.remove(node);
        }
    }
}

