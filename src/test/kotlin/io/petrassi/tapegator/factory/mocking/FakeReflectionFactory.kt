package io.petrassi.tapegator.factory.mocking

import io.petrassi.tapegator.factory.ReflectionFactory

object FakeReflectionFactory : ReflectionFactory<FakeData> {
    override fun defaultImplementation(): String = FakeData::class.java.simpleName
    override fun getType(): Class<FakeData> = FakeData::class.java
}
