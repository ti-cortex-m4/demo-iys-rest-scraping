package demo.delay;

public final class Delay {

    public static void seconds(int seconds) {
        while (seconds-- > 0) {
            second();
        }
    }

    private static void second() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
