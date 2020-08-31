package nordstrom.codingexercise;

public class InMemoryCache {

    public static void main (String[]args){
        // TODO Auto-generated method stub
        LRUCacheEviction cache = new LRUCacheEviction();
        cache.add(1, "cake recipe");
        cache.add(2, "frosting recipe");
        cache.add(3, "cupcake recipe");
        cache.add(4, "brownie recipe");
        cache.add(5, "cookie recipe");

        System.out.println(cache.get(1));
        System.out.println(cache.exists(4));
        System.out.println(cache.get(2));
        System.out.println(cache.get(5));
        System.out.println(cache.get(3));
        System.out.println(cache.get(1));
        cache.print();


        RandomCacheEviction cache2 = new RandomCacheEviction();

        cache2.add(1, "cake recipe");
        cache2.add(2, "frosting recipe");
        cache2.add(3, "cupcake recipe");
        cache2.add(4, "brownie recipe");
        cache2.add(5, "cookie recipe");

        System.out.println(cache2.get(1));
        System.out.println(cache2.exists(4));
        System.out.println(cache2.get(2));
        System.out.println(cache2.get(5));
        System.out.println(cache2.get(3));
        System.out.println(cache2.get(1));


    }

}






