import org.junit.Test;
import org.water.A;
import org.water.AAA;
import org.water.B;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mati
 * @since 2020/1/9 16:53
 */
public class GenericsTest {

    @Test
    public void generics() {
        B<AAA> b = new B<>();
        List<AAA> aaa= new ArrayList<>();
        List<A> a= new ArrayList<>();
        b.test1(a);
        b.test2(aaa);
    }
}
