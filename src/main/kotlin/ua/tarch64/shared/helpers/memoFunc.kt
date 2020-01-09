package ua.tarch64.shared.helpers

fun <T> createMemoFunc(func: () -> T): () -> T {
    var memo: T? = null

    return fun(): T {
        if ( memo == null ) memo = func()
        return memo!!
    }
}