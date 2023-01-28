package io.petrassi.tapegator.factory.mocking

import io.petrassi.tapegator.factory.EnumFactory
import java.util.function.Supplier

enum class FakeEnumFactory(supplier: Supplier<FakeData>) : Supplier<FakeData> by supplier {
    ONE({ FakeData(FAKE_DATA_FIRST_IMPL_VALUE) }),
    TWO({ FakeData(FAKE_DATA_SECOND_IMPL_VALUE) });
    companion object : EnumFactory<FakeData, FakeEnumFactory> {
        override fun getType(): Class<FakeData> = FakeData::class.java
        override fun getEnum(): Class<FakeEnumFactory> = FakeEnumFactory::class.java
    }
}
