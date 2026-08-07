package com.aireplyassistant.di;

import com.aireplyassistant.domain.repository.KeyboardRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideKeyboardRepositoryFactory implements Factory<KeyboardRepository> {
  @Override
  public KeyboardRepository get() {
    return provideKeyboardRepository();
  }

  public static AppModule_ProvideKeyboardRepositoryFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static KeyboardRepository provideKeyboardRepository() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideKeyboardRepository());
  }

  private static final class InstanceHolder {
    static final AppModule_ProvideKeyboardRepositoryFactory INSTANCE = new AppModule_ProvideKeyboardRepositoryFactory();
  }
}
