package io.petrassi.tapegator.factory;

public interface ReflectionFactory<T> extends Factory<T> {

    default String getRootPackage() {
        return this.getClass().getPackageName();
    }

    String defaultImplementation();

    @Override
    default T create(String implementationName) {
        if(implementationName == null) {
            throw new IllegalArgumentException(String.format(FactoryConfig.NULL_IMPL_ERROR, getType().getSimpleName()));
        }

        if (implementationName.isBlank()) {
            return FactoryConfig.INSTANCE.getByReflection(getRootPackage(), defaultImplementation());
        }

        return FactoryConfig.INSTANCE.getByReflection(getRootPackage(), implementationName);
    }
}