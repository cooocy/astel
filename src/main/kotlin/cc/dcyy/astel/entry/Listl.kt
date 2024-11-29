package cc.dcyy.astel.entry

import java.time.Instant
import java.time.temporal.TemporalUnit

/**
 * A List value, its elements can be duplicated but not null.
 */
class Listl private constructor() : Value() {
    val value: ArrayDeque<Any> = ArrayDeque()

    companion object {

        /**
         * New a forever list.
         */
        fun new(vararg elements: Any): Listl {
            val l = Listl()
            l.value.addAll(elements)
            return l
        }

        /**
         * New a temporary list.
         */
        fun new(expire: Long, unit: TemporalUnit, vararg elements: Any): Listl {
            val l = Listl()
            l.value.addAll(elements)
            l.expires = Instant.now().plus(expire, unit)
            return l
        }
    }

    /**
     * Returns the element at the specified index in the list.
     */
    fun get(index: Int): Any? {
        return value[index]
    }

    /**
     * Removes an element at the specified index from the list.
     */
    fun removeAt(index: Int) {
        value.removeAt(index)
    }

    /**
     * Prepends the specified element to this list.
     */
    fun leftPush(element: Any) {
        value.addFirst(element)
    }

    /**
     * Removes the first element from this list and returns that removed element.
     * If this list is empty, returns null.
     */
    fun leftPop(): Any? {
        return if (value.isEmpty()) null else value.removeFirst()
    }

    /**
     * Appends the specified element to this list.
     */
    fun rightPush(element: Any) {
        value.addLast(element)
    }

    /**
     * Removes the last element from this list and returns that removed element.
     * If this list is empty, returns null.
     */
    fun rightPop(): Any? {
        return if (value.isEmpty()) null else value.removeLast()
    }

    /**
     * Returns a new list of the portion of this list between the specified fromIndex (inclusive) and toIndex (exclusive).
     * [fromIndex, toIndex)
     */
    fun range(fromIndex: Int, toIndex: Int): List<Any> {
        return value.subList(fromIndex, toIndex)
    }

    /**
     * Returns size of this list.
     */
    fun size(): Int {
        return value.size
    }

}