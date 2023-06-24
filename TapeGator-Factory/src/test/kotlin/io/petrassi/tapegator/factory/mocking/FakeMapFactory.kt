package io.petrassi.tapegator.factory.mocking

import io.petrassi.tapegator.factory.MapFactory
import java.util.function.Supplier

object FakeMapFactory : MapFactory<FakeData> {
    override fun getType(): Class<FakeData> = FakeData::class.java
    override fun getImplementations(): Map<String, Supplier<FakeData>> = mapOf(
        POSSIBLE_FAKE_ONE to Supplier { FakeData(FAKE_DATA_FIRST_IMPL_VALUE) },
        POSSIBLE_FAKE_TWO to Supplier { FakeData(FAKE_DATA_SECOND_IMPL_VALUE) },
        POSSIBLE_FAKE_ONE1 to Supplier { FakeData(FAKE_DATA_SECOND_IMPL_VALUE) }
    )
}
