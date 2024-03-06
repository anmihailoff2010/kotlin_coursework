class Stack<T> {
    var items = mutableListOf<T>()

    fun isEmpty(): Boolean = items.isEmpty()

    fun push(item: T) = items.add(item)

    fun pop(): T? {
        return if (isEmpty()) null
        else items.removeAt(items.count() - 1)
    }

}