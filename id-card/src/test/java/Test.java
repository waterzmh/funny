import static org.water.IdCardValidator.isValidatedAllIdCard;

/**
 * @author water
 * @since 2019/1/2 18:46
 */
public class Test {

    @org.junit.Test
    public void test() {
        // google找的一个身份证
        String card = "341221198804231073";
        System.out.println(isValidatedAllIdCard(card));
    }
}
