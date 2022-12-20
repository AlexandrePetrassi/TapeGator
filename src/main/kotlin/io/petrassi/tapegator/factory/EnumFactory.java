package io.petrassi.tapegator.factory;

import java.util.function.Supplier;

public interface EnumFactory<T, U extends Enum<U> & Supplier<T>> extends Factory<T> {

    Class<U> getEnum();

    /**
     * @param key The enum entry which supplies an instance of T
     * @return an instance of T supplied by an Enum entry which name matches the implementationName
     */
    default T create(U key) {
        return create(key.name());
    }

    /**
     * @param implementationName the implementation name described in the enum (Case Insensitive)
     * @return an instance of T supplied by an Enum entry which name matches the implementationName
     */
    default T create(String implementationName) {
        if(implementationName == null) {
            throw new IllegalArgumentException(String.format(FactoryConfig.NULL_IMPL_ERROR, getType().getSimpleName()));
        }

        if (implementationName.isBlank()) {
            return getEnum().getEnumConstants()[0].get();
        }

        U[] constants = getEnum().getEnumConstants();
        for (U constant : constants) {
            if (constant.name().equalsIgnoreCase(implementationName)) {
                return constant.get();
            }
        }

        String message = String.format(FactoryConfig.MISSING_IMPL_ERROR, implementationName, getType().getSimpleName());
        throw new IllegalArgumentException(message);
    }
}
