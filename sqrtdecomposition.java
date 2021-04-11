import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class sqrtdecomposition {
    static final int mod = (int)1e9+7;
    static List<List<Integer>>blocks = new ArrayList<>();
    static void preprocess(String[] s,int n) {
        int size = (int)Math.ceil(Math.sqrt(n));
        List<Integer>list = new ArrayList<>();
        int num = 1;
        int c = 0,ind = -1,i;
        for( i = 0 ; i < n ; i++ ){
            if(c < size) {
                list.add(Integer.parseInt(s[i]));
                num = ((num % mod) * (Integer.parseInt(s[i]) % mod)) % mod;
                ++c;
            }
            else {
                list.add(num % mod);
                blocks.add(list);
                list = new ArrayList<>();
                list.add(Integer.parseInt(s[i]));
                num = Integer.parseInt(s[i]) % mod;
                c = 1;
                ind = i;
            }
        }
        list = new ArrayList<>();
        num = 1;
        for( int j = ind ; j < n ; j++ ) {
            list.add(Integer.parseInt(s[j]));
            num = ((num % mod) * (Integer.parseInt(s[j]) % mod)) % mod;
        }
        list.add(num % mod);
        blocks.add(list);
    }
    static int query(int u, int v, int n) {
        int size = (int)Math.ceil(Math.sqrt(n));
        int ss = u, se = v;
        u = u/size;v = v/size;
        int ans = 1;
        for( int j = u+1 ; j < v ; j++ ) {
            ans = ((ans % mod) * (blocks.get(j).get(blocks.get(j).size()-1) % mod)) % mod;
        }
        for( int j = (ss % size) ; j < size ; j++ ) {
            ans = ((ans % mod) * (blocks.get(u).get(j) % mod)) % mod;
        }
        for( int j = 0 ; j <= (se % size) ; j++ ) {
            ans = ((ans % mod) * (blocks.get(v).get(j) % mod)) % mod;
        }
        return  ans % mod;
    }
    static void update(int i,int val,int n)
    {
        int size = (int)Math.ceil(Math.sqrt(n));
        int ind = i/size, index = i % size;
        List<Integer>list = blocks.get(ind);
        list.set(index,val);
        int ans = 1;
        for( int j = 0 ; j < list.size() - 1 ; j++ ) {
            ans = ((ans % mod) * (list.get(j) % mod)) % mod;
        }
        list.set(list.size()-1,ans);
        blocks.set(ind,list);
    }
    public static void main(String[] args)throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bufferedReader.readLine());
        String[] s = bufferedReader.readLine().split(" ");
        preprocess(s,n);
        int q = Integer.parseInt(bufferedReader.readLine());
        System.out.println(blocks);
        update(3,10,n);
        System.out.println(blocks);
        for( int i = 0 ; i < q ; i++ ) {
            String[] s1 = bufferedReader.readLine().split(" ");
            int u = Integer.parseInt(s1[0]), v = Integer.parseInt(s1[1]);
            int ans = query(u-1,v-1,n);
            System.out.println(ans);
        }
    }
}
