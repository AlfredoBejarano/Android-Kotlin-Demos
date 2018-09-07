package com.alfredobejarano.viewmodeltesting

import android.arch.lifecycle.ViewModel

/**
 * Simple [ViewModel] class.
 * @author Alfredo Bejarano
 * @since August 2nd, 2018 - 06:56 PM
 * @version 1.0
 */
class SumViewModel : ViewModel() {
    /**
     * Receives a set of numbers and adds them to a result.
     * @return The addition of all the elements.
     */
    fun addNumbers(vararg elements: Int): Int {
        var result = 0
        elements.forEach {
            result += it
        }
        return result
    }
}