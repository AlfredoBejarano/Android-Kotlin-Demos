package com.alfredobejarano.viewmodeltesting

import android.app.Application
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test class for [SumAndroidViewModel] using the [InstrumentationRegistry]
 * available in instrumented tests (androidTest directory).
 */
@RunWith(AndroidJUnit4::class)
class SumAndroidViewModelTest {
    /**
     * Test the addNumbers() function using the InstrumentationRegistry
     * application context as an application class.
     */
    @Test
    fun addNumbers() {
        // Parse the InstrumentationRegistry applicationContext to Application.
        val application = InstrumentationRegistry.getTargetContext().applicationContext as Application
        // Create a new SumAndroidViewModel using the parsed Application and sum a given set of elements.
        val result = SumAndroidViewModel(application).addNumbers(400, 20)
        // Assert that the sum is correct.
        assert(result == 420)
    }
}