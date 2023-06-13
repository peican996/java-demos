package com.wpc;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author wangpeican
 * @date 2022/3/24 22:32
 */
public class LogTest {
//    private static Logger logger = LogManager.getLogger(LogTest.class.getName());
    private static final Logger logger = LogManager.getLogger("RollingFileInfo");

    public static void main(String[] args) {
        logger.info("1541511");
    }

//    @Test
//    public void testLog4j2() {
//        logger.info("info");
//    }
}
