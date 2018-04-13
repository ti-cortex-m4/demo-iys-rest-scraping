package demo.executor

import com.google.common.util.concurrent.FutureCallback
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListeningExecutorService
import com.google.common.util.concurrent.MoreExecutors
import demo.delay.Delay
import demo.reader.AbstractReader
import org.slf4j.LoggerFactory
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer

class ParallelExecutor<T>(totalExecutorsNumber: Int) : Executor<T> {

    private val freeExecutorsNumber: AtomicInteger
    private val executorService: ListeningExecutorService

    init {
        this.freeExecutorsNumber = AtomicInteger(totalExecutorsNumber)
        this.executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(totalExecutorsNumber))
    }

    override fun execute(producer: Callable<T>, consumer: Consumer<T>) {
        while (freeExecutorsNumber.get() == 0) {
            logger.info("waiting...")
            Delay.seconds(1)
        }
        freeExecutorsNumber.decrementAndGet()

        val future = executorService.submit(producer)
        Futures.addCallback(future, object : FutureCallback<T> {
            override fun onSuccess(result: T?) {
                freeExecutorsNumber.incrementAndGet()
                consumer.accept(result)
            }

            override fun onFailure(t: Throwable) {
                freeExecutorsNumber.incrementAndGet()
                logger.error("Exception during execution", t)

                logger.info("Executors shutdown started")
                val success = MoreExecutors.shutdownAndAwaitTermination(executorService, 60, TimeUnit.SECONDS)
                logger.info("Executors shutdown finished: " + if (success) " success" else "failure")

                System.exit(1)
            }
        })
    }

    companion object {

        private val logger = LoggerFactory.getLogger(AbstractReader::class.java)
    }
}

// https://github.com/google/guava/wiki/ListenableFutureExplained
// http://www.nurkiewicz.com/2014/11/executorservice-10-tips-and-tricks.html
