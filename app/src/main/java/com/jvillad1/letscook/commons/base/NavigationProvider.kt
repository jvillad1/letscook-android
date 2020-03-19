package com.jvillad1.letscook.commons.base

/**
 * Interface for navigation between views.
 *
 * @author juan.villada
 */
interface NavigationProvider<in A> {

    fun navigateTo(destinationView: A)
}
