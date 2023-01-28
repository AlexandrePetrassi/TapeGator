package io.petrassi.tapegator.factory;

import io.petrassi.tapegator.factory.config.FactoryConfig;

import java.util.function.Supplier;

/**
 * <p>Enum based factory. Use this factory to associate Enums which supplies
 * implementations of a given type.</p>
 * <p>EnumFactories make possible the use of compile-time constants as keys in
 * implementation mappings.</p>
 * @param <T> The type of object this factory creates
 * @param <U> The enum type which each entry is also a Supplier of T
 */
public interface EnumFactory<T, U extends Enum<U> & Supplier<T>> extends Factory<T> {

    /**
     * <p>This must return a U class object.</p>
     * <p>Due to Type Erasure, the generic U type must be reified by this
     * property, so the factory can access a list of constants and enum entries
     * from the enum.</p>
     * @return a reified U Class object. Essentially U::class
     */
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
            throw new IllegalArgumentException(String.format(FactoryConfig.NULL_IMPL_ERROR, getType().getName()));
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

        String message = String.format(FactoryConfig.MISSING_IMPL_ERROR, implementationName, getType().getName());
        throw new IllegalArgumentException(message);
    }
}
