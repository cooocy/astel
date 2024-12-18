package cc.dcyy.astel.core

import cc.dcyy.astel.MemoryCleanC
import kotlin.test.Test

class MemoryCleanerTest {

    @Test
    fun testClean() {
        MemoryCleaner.clean(MemoryCleanC(20))
    }

}