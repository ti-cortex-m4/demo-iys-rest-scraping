package demo.executor;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

public interface Executor<T> {

    void execute(Callable<T> producer, Consumer<T> consumer);
}
