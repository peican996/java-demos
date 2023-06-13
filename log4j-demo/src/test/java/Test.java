import com.wpc.LogTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author wangpeican
 * @date 2023/6/13 22:41
 */
@RunWith(PowerMockRunner.class)
public class Test {
    @org.junit.Test
    public void test() {
        Logger logger = Mockito.mock(Logger.class);
        PowerMockito.when(LogManager.getLogger("RollingFileInfo")).thenReturn(logger);
        String[] args = new String[]{};
        LogTest.main(args);
    }
}
