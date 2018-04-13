package demo.delay

object Delay {

    fun seconds(seconds: Int) {
        var seconds = seconds
        while (seconds-- > 0) {
            second()
        }
    }

    private fun second() {
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }

    }
}
