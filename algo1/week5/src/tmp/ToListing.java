package tmp;

import static java.util.stream.Collectors.toCollection;

import java.util.Vector;
import java.util.stream.IntStream;

public class ToListing {
    public static void main(String[] args) {
        int n = 1000000;
        
        System.out.println(IntStream.range(0, n).parallel()
                                    .mapToDouble(Math::sin)
                                    .boxed()
                                    .collect(toCollection(Vector<Double>::new))
                                    .size());;
    }
}
