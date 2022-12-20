package io.petrassi.tapegator.config

import io.petrassi.tapegator.factory.EnumFactory
import java.util.function.Supplier

interface Loader {
    fun <T> load(clazz: Class<T>, filename: String): T

    companion object : EnumFactory<Loader, LoaderFactory> {
        override fun getEnum(): Class<LoaderFactory> = LoaderFactory::class.java
        override fun getType(): Class<Loader> = Loader::class.java
    }
}

inline fun <reified T> Loader.load(filename: String): T =
    load(T::class.java, filename)

enum class LoaderFactory(supplier: Supplier<Loader>) : Supplier<Loader> by supplier {
    JACKSON({ JacksonYamlLoader });
}