package cc.dcyy.astel.core

import cc.dcyy.astel.MemoryCleanC
import kotlin.test.Test

class MemoryCleanerTest {

    @Test
    fun testClean() {
        val c = MemoryCleanC()
        c.threshold = 20
        c.period = 10L
        MemoryCleaner.clean(c)
    }

}