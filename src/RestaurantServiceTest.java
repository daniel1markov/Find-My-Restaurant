import com.hit.algorithm.IAlgoPatternSearching;
import com.hit.algorithm.RabinKarpAlgorithm;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RestaurantServiceTest {

        static final String txt = "Daniel and Inbar Algorithms";

        @Test
        public void RabinKarpTest_PatternExists_ShouldPass() {
            String pat = "Algo";
            IAlgoPatternSearching robinKarp = new RabinKarpAlgorithm(txt, pat);
            boolean result = robinKarp.Search();

        }
}
