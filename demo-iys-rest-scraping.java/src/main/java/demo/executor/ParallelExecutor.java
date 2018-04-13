package demo.executor;

import com.google.common.util.concurrent.*;
import demo.delay.Delay;
import demo.reader.AbstractReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class ParallelExecutor<T> implements Executor<T> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractReader.class);

    final private AtomicInteger freeExecutorsNumber;
    final private ListeningExecutorService executorService;

    public ParallelExecutor(int totalExecutorsNumber) {
        this.freeExecutorsNumber = new AtomicInteger(totalExecutorsNumber);
        this.executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(totalExecutorsNumber));
    }

    @Override
    public void execute(Callable<T> producer, Consumer<T> consumer) {
        while (freeExecutorsNumber.get() == 0) {
            logger.info("waiting...");
            Delay.seconds(1);
        }
        freeExecutorsNumber.decrementAndGet();

        final ListenableFuture<T> future = executorService.submit(producer);
        Futures.addCallback(future, new FutureCallback<T>() {
            @Override
            public void onSuccess(T result) {
                freeExecutorsNumber.incrementAndGet();
                consumer.accept(result);
            }

            @Override
            public void onFailure(Throwable t) {
                freeExecutorsNumber.incrementAndGet();
                logger.error("Exception during execution", t);

                logger.info("Executors shutdown started");
                boolean success = MoreExecutors.shutdownAndAwaitTermination(executorService, 60, TimeUnit.SECONDS);
                logger.info("Executors shutdown finished: " + (success ? " success" : "failure"));

                System.exit(1);
            }
        });
    }
}

// https://github.com/google/guava/wiki/ListenableFutureExplained
// http://www.nurkiewicz.com/2014/11/executorservice-10-tips-and-tricks.html