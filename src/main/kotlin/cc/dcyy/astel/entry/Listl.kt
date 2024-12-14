package cc.dcyy.astel.entry

import java.time.Instant
import java.time.temporal.TemporalUnit

/**
 * A List value, its elements can be duplicated but not null.
 */
class Listl private constructor() : Value() {
    val v: ArrayDeque<Any> = ArrayDeque()

    companion object {

        /**
         * New a forever list.
         */
        fun new(vararg elements: Any): Listl {
            val l = Listl()
            l.v.addAll(elements)
            return l
        }

        /**
         * New a temporary list.
         */
        fun new(expire: Long, unit: TemporalUnit, vararg elements: Any): Listl {
            val l = Listl()
            l.v.addAll(elements)
            l.expires = Instant.now().plus(expire, unit)
            return l
        }
    }

    /**
     * Returns the element at the specified index in the list.
     */
    fun get(index: Int): Any? {
        return v[index]
    }

    /**
     * Removes an element at the specified index from the list.
     */
    fun removeAt(index: Int) {
        v.removeAt(index)
    }

    /**
     * Prepends the specified element to this list.
     */
    fun leftPush(element: Any) {
        v.addFirst(element)
    }

    /**
     * Removes the first element from this list and returns that removed element.
     * If this list is empty, returns null.
     */
    fun leftPop(): Any? {
        return if (v.isEmpty()) null else v.removeFirst()
    }

    /**
     * Appends the specified element to this list.
     */
    fun rightPush(element: Any) {
        v.addLast(element)
    }

    /**
     * Removes the last element from this list and returns that removed element.
     * If this list is empty, returns null.
     */
    fun rightPop(): Any? {
        return if (v.isEmpty()) null else v.removeLast()
    }

    /**
     * Returns a new list of the portion of this list between the specified fromIndex (inclusive) and toIndex (exclusive).
     * [fromIndex, toIndex)
     */
    fun range(fromIndex: Int, toIndex: Int): List<Any> {
        return v.subList(fromIndex, toIndex)
    }

    /**
     * Returns size of this list.
     */
    fun size(): Int {
        return v.size
    }

}