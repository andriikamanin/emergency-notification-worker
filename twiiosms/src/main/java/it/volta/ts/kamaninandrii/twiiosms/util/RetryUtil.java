package it.volta.ts.kamaninandrii.twiiosms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class RetryUtil {

    private static final Logger logger = LoggerFactory.getLogger(RetryUtil.class);

    public static <T> T withRetry(Supplier<T> supplier, int maxAttempts) {
        int attempt = 0;
        while (attempt < maxAttempts) {
            try {
                return supplier.get();
            } catch (Exception e) {
                attempt++;
                logger.warn("Attempt {} failed, retrying...", attempt);
                if (attempt == maxAttempts) {
                    throw e;
                }
                try {
                    Thread.sleep(1000); // Пауза между попытками
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return null;
    }
}
