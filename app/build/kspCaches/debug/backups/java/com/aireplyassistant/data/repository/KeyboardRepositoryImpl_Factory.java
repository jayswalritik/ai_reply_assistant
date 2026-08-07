package com.aireplyassistant.data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class KeyboardRepositoryImpl_Factory implements Factory<KeyboardRepositoryImpl> {
  @Override
  public KeyboardRepositoryImpl get() {
    return newInstance();
  }

  public static KeyboardRepositoryImpl_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static KeyboardRepositoryImpl newInstance() {
    return new KeyboardRepositoryImpl();
  }

  private static final class InstanceHolder {
    static final KeyboardRepositoryImpl_Factory INSTANCE = new KeyboardRepositoryImpl_Factory();
  }
}
