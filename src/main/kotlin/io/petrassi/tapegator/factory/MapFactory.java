package io.petrassi.tapegator.factory;

import java.util.Map;
import java.util.function.Supplier;

public interface MapFactory<T> extends Factory<T> {

    Map<String, Supplier<T>> getImplementations();

    @Override
    default T create(String implementationName) {
        if (implementationName == null) {
            String message = String.format(FactoryConfig.NULL_IMPL_ERROR, getType().getName());
            throw new IllegalArgumentException(message);
        }

        Supplier<T> supplier = implementationName.isBlank()
                ? getImplementations().entrySet().stream().findFirst().orElseThrow().getValue()
                : getImplementations().get(implementationName);

        if(supplier == null) {
            String message = String.format(FactoryConfig.MISSING_IMPL_ERROR, implementationName, getType());
            throw new IllegalStateException(message);
        }

        return supplier.get();
    }
}