import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class sqrtdecomposition {
    static final long mod = (long)1e9+7;
    static List<List<Long>>blocks = new ArrayList<>();
    
    //preprocess is the method where sqrt(n) blocks of List/Array is created
    //preprocess will take O(n) time to craete and will take O(n) space
    static void preprocess(String[] s,long n) {
        long size = (long)Math.ceil(Math.sqrt(n));
        List<Long>list = new ArrayList<>();
        long num = 1;
        long c = 0,ind = -1;
        int i;
        for( i = 0 ; i < n ; i++ ){
            if(c < size) {
                list.add(Long.parseLong(s[i]));
                num = ((num % mod) * (Long.parseLong(s[i]) % mod)) % mod;
                ++c;
            }
            else {
                list.add(num % mod);
                blocks.add(list);
                list = new ArrayList<>();
                list.add(Long.parseLong(s[i]));
                num = Long.parseLong(s[i]) % mod;
                c = 1;
                ind = i;
            }
        }
        list = new ArrayList<>();
        num = 1;
        for( int j = (int)ind ; j < n ; j++ ) {
            list.add(Long.parseLong(s[j]));
            num = ((num % mod) * (Long.parseLong(s[j]) % mod)) % mod;
        }
        list.add(num % mod);
        blocks.add(list);
    }
    //query is the method where the product is calculated between the given range
    //query takes O(sqrt(n)) time with no extra space
    static long query(long u, long v, long n) {
        long size = (long)Math.ceil(Math.sqrt(n));
        long ss = u, se = v;
        u = u/size;v = v/size;
        long ans = 1;
        for( int j = (int)u+1 ; j < v ; j++ ) {
            ans = ((ans % mod) * (blocks.get(j).get(blocks.get(j).size()-1) % mod)) % mod;
        }
        for( int j = (int)(ss % size) ; j < size ; j++ ) {
            ans = ((ans % mod) * (blocks.get((int)u).get(j) % mod)) % mod;
        }
        for( int j = 0 ; j <= (se % size) ; j++ ) {
            ans = ((ans % mod) * (blocks.get((int)v).get((int)j) % mod)) % mod;
        }
        return  ans % mod;
    }
    //update method updates the value at the given position in the array
    //update takes O(sqrt(n)) time with no extra space
    static void update(long i,long val,long n)
    {
        long size = (long)Math.ceil(Math.sqrt(n));
        long ind = i/size, index = i % size;
        List<Long>list = blocks.get((int)ind);
        list.set((int)index,val);
        long ans = 1;
        for( int j = 0 ; j < list.size() - 1 ; j++ ) {
            ans = ((ans % mod) * (list.get(j) % mod)) % mod;
        }
        list.set(list.size()-1,ans);
        blocks.set((int)ind,list);
    }
    public static void main(String[] args)throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        long n = Long.parseLong(bufferedReader.readLine());
        String[] s = bufferedReader.readLine().split(" ");
        preprocess(s,n);
        long q = Long.parseLong(bufferedReader.readLine());
        //printing before updating........
        System.out.println(blocks);
        //calling update method
        update(3,10,n);
        //printing after updating.......
        System.out.println(blocks);
        for( long i = 0 ; i < q ; i++ ) {
            String[] s1 = bufferedReader.readLine().split(" ");
            long u = Long.parseLong(s1[0]), v = Long.parseLong(s1[1]);
            long ans = query(u-1,v-1,n);
            System.out.println(ans);
        }
    }
}
