package io.petrassi.tapegator.config

import io.petrassi.tapegator.factory.EnumFactory

interface Loader {

    fun <T> load(clazz: Class<T>, filename: String): T
    companion object : EnumFactory<Loader, LoaderFactory> by LoaderFactory
}

inline fun <reified T> Loader.load(filename: String): T =
    load(T::class.java, filename)
