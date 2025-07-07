package android.util

/**
    This class replaces android.util.Log during unit tests to prevent real Log calls,
    which are unavailable outside the Android runtime.
    Always use an injected logger (e.g., ClassLogger) in production code and inject a mock in tests.
    Do not call Log.d directly in tested code; use only the injected logger to enable mocking and
    avoid environment errors.
**/
object Log {
    @JvmStatic
    fun d(tag: String?, msg: String?): Int = 0
    @JvmStatic
    fun i(tag: String?, msg: String?): Int = 0
    @JvmStatic
    fun w(tag: String?, msg: String?): Int = 0
    @JvmStatic
    fun e(tag: String?, msg: String?): Int = 0
}