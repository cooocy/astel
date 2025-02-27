package cc.dcyy.astel.core.entry

import java.time.Instant
import java.time.temporal.TemporalUnit

/**
 * A Set value, its elements can not be duplicated and not null.
 */
class Sets private constructor() : Value() {
    val v: HashSet<Any> = HashSet()

    companion object {
        /**
         * New a forever set.
         */
        fun new(vararg elements: Any): Sets {
            var s = Sets()
            elements.forEach { e -> s.v.add(e) }
            return s
        }

        /**
         * New a temporary set.
         */
        fun new(expire: Long, unit: TemporalUnit, vararg elements: Any): Sets {
            val s = Sets()
            elements.forEach { e -> s.v.add(e) }
            s.expires = Instant.now().plus(expire, unit)
            return s
        }
    }

    /**
     * Returns size of this set.
     */
    fun size() = v.size

    /**
     * Returns true if this set contains the specified element.
     */
    fun contains(element: Any): Boolean {
        return v.contains(element)
    }

    /**
     * Removes the specified element from this set if it is present.
     */
    fun remove(element: Any) {
        v.remove(element)
    }

    /**
     * Returns a set containing all elements that are contained by both this set and the specified set.
     */
    fun intersect(other: Sets): Set<Any> {
        return HashSet(v.intersect(other.v))
    }

    /**
     * Returns a set containing all distinct elements from both sets.
     */
    fun union(other: Sets): Set<Any> {
        return HashSet(v.union(other.v))
    }

}