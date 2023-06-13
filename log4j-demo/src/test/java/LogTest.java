import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LogManager.class)
@PowerMockIgnore({"javax.management.*"})
public class LogTest {

    @Test
    public void testLogMessage() {
        Logger loggerMock = PowerMockito.mock(Logger.class);
        PowerMockito.mockStatic(LogManager.class);
        PowerMockito.when(LogManager.getLogger("RollingFileInfo")).thenReturn(loggerMock);
        String[] args = new String[]{};
        com.wpc.LogTest.main(args);

    }
}
