package com.alfredobejarano.viewmodeltesting

import android.app.Application
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Simple test class for [SumViewModel] that uses Robolectric
 * instead of the InstrumentationRegistry, this allows this test class
 * to exist in the test directory.
 */
@RunWith(RobolectricTestRunner::class)
@Config(application = Application::class)
class SumAndroidViewModelUnitTest {
    /**
     * Simple test for the addNumbers() function.
     */
    @Test
    fun addNumbers() {
        // Retrieve the Runtime Application using Robolectric.
        val application = RuntimeEnvironment.application
        // Create a new SumAndroidViewModel and add the elements.
        val result = SumAndroidViewModel(application).addNumbers(220, 446)
        // Assert that the result of the sum is the expected.
        assert(result == 666)
    }
}