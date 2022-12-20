package io.petrassi.tapegator.factory.mocking

import io.petrassi.tapegator.factory.EnumFactory
import java.util.function.Supplier

object FakeEnumFactory : EnumFactory<FakeData, PossibleFakes> {
    override fun getType(): Class<FakeData> = FakeData::class.java
    override fun getEnum(): Class<PossibleFakes> = PossibleFakes::class.java
}

enum class PossibleFakes(supplier: Supplier<FakeData>) : Supplier<FakeData> by supplier {
    ONE({ FakeData(FAKE_DATA_FIRST_IMPL_VALUE) }),
    TWO({ FakeData(FAKE_DATA_SECOND_IMPL_VALUE) });
}
