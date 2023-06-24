package io.petrassi.tapegator.factory;

import io.petrassi.tapegator.factory.config.FactoryConfig;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

        if (supplier != null) {
            return supplier.get();
        }

        List<String> possibleImplementations = getImplementations().keySet().stream()
                .filter(it -> it.equalsIgnoreCase(implementationName))
                .collect(Collectors.toList());

        if(possibleImplementations.isEmpty()) {
            String message = String.format(FactoryConfig.MISSING_IMPL_ERROR, implementationName, getType().getName());
            throw new IllegalArgumentException(message);
        }

        if (possibleImplementations.size() > 1) {
            String template = FactoryConfig.AMBIGUOUS_IMPLEMENTATION_ERROR;
            String message = String.format(template, implementationName, getType().getName(), possibleImplementations);
            throw new IllegalArgumentException(message);
        }

        return getImplementations().get(possibleImplementations.get(0)).get();

    }
}