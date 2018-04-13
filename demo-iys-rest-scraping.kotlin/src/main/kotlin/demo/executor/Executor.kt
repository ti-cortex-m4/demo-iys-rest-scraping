package demo.executor

import java.util.concurrent.Callable
import java.util.function.Consumer

interface Executor<T> {

    fun execute(producer: Callable<T>, consumer: Consumer<T>)
}
