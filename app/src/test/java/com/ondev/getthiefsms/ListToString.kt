package com.ondev.getthiefsms

import com.ondev.getthiefsms.utils.phoneList2String
import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ListToString {

    @Test
    fun listProcess() {
        val b = listOf("+5354074127", "+5354087598").phoneList2String()
        Assert.assertEquals("+5354074127\n+5354087598", b)
    }
}