package io.petrassi.tapegator.config

import io.petrassi.tapegator.factory.Factory

interface Loader {

    fun <T> load(clazz: Class<T>, filename: String): T
    companion object : Factory<Loader> by LoaderFactory
}

inline fun <reified T> Loader.load(filename: String): T =
    load(T::class.java, filename)
