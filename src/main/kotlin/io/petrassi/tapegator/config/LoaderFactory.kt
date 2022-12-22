package io.petrassi.tapegator.config

import io.petrassi.tapegator.factory.EnumFactory
import java.util.function.Supplier

enum class LoaderFactory(supplier: Supplier<Loader>) : Supplier<Loader> by supplier {
    JACKSON({ JacksonYamlLoader });
    companion object : EnumFactory<Loader, LoaderFactory> {
        override fun getEnum(): Class<LoaderFactory> = LoaderFactory::class.java
        override fun getType(): Class<Loader> = Loader::class.java
    }
}