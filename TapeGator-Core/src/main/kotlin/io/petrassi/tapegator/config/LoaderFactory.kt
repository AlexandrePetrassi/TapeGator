package io.petrassi.tapegator.config

import io.petrassi.tapegator.factory.ReflectionFactory

object LoaderFactory : ReflectionFactory<Loader> {
    override fun getType(): Class<Loader> {
        return Loader::class.java
    }
    override fun defaultImplementation(): String {
        return ""
    }
}