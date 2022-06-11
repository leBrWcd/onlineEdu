/**
 * @author lebrwcd
 * @date 2022/5/30
 * @note
 */

import com.lebrwcd.eduorder.utils.OrderNoUtil;
import org.junit.Test;

/**
 * ClassName UtilTest
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/30
 */
public class UtilTest {

    @Test
    public void test1() {
        String s = OrderNoUtil.RandomOrder();
        System.out.println(s);
        //20220530151026815
    }
}
