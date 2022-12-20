package io.petrassi.tapegator.factory.mocking

const val FAKE_DATA_DEFAULT_VALUE = "Default"
const val FAKE_DATA_FIRST_IMPL_VALUE = "one"
const val FAKE_DATA_SECOND_IMPL_VALUE = "two"
const val POSSIBLE_FAKE_ONE = "ONE"
const val POSSIBLE_FAKE_TWO = "TWO"

data class FakeData(val value: String = FAKE_DATA_DEFAULT_VALUE)

