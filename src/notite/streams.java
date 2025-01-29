package notite;

import java.util.Arrays;
import java.util.List;

public class streams {
    public static void main(String[] args)
    {
       List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14,15);

       int res1 = numbers.stream().filter(x -> x % 5 == 0 || x%2 == 0)
               .map(x -> {if(x%2==0) return "AAA"; else return "AAAAA"; }).map(x -> x.length()).reduce(0, Integer::sum);
        System.out.println(res1);

        /*List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14,15);

        String result = numbers.stream().filter(x -> x % 5 == 0 || x % 4 == 0).map(x-> "N" + x + "R").reduce("",String::concat);

        System.out.println(result);*/

        //List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,14,15);

       // int result =numbers.stream().filter(x->x%7==0 || x%3==0).map(x->(x-1)*10).reduce(0,Integer::sum );

        //System.out.println(result);
    }
}
