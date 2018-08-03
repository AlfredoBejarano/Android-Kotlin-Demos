package com.alfredobejarano.viewmodeltesting

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Simple test class for [SumViewModel].
 */
@RunWith(JUnit4::class)
class SumViewModelTest {
    /**
     * Simple test for the addNumbers() function.
     */
    @Test
    fun addNumbers() {
        // Create a new SumViewModel and add the elements.
        val result = SumViewModel().addNumbers(100, -31)
        // Assert that the result of the sum is the expected.
        assert(result == 69)
    }
}