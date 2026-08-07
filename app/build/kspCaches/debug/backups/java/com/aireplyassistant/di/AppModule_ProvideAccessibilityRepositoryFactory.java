package com.aireplyassistant.di;

import com.aireplyassistant.domain.repository.AccessibilityRepository;
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
public final class AppModule_ProvideAccessibilityRepositoryFactory implements Factory<AccessibilityRepository> {
  @Override
  public AccessibilityRepository get() {
    return provideAccessibilityRepository();
  }

  public static AppModule_ProvideAccessibilityRepositoryFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static AccessibilityRepository provideAccessibilityRepository() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideAccessibilityRepository());
  }

  private static final class InstanceHolder {
    static final AppModule_ProvideAccessibilityRepositoryFactory INSTANCE = new AppModule_ProvideAccessibilityRepositoryFactory();
  }
}
