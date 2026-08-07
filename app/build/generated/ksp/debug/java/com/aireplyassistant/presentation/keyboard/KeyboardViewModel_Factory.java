package com.aireplyassistant.presentation.keyboard;

import android.content.Context;
import com.aireplyassistant.domain.repository.AccessibilityRepository;
import com.aireplyassistant.domain.usecase.GenerateRepliesUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class KeyboardViewModel_Factory implements Factory<KeyboardViewModel> {
  private final Provider<GenerateRepliesUseCase> generateRepliesUseCaseProvider;

  private final Provider<AccessibilityRepository> accessibilityRepositoryProvider;

  private final Provider<Context> contextProvider;

  private KeyboardViewModel_Factory(Provider<GenerateRepliesUseCase> generateRepliesUseCaseProvider,
      Provider<AccessibilityRepository> accessibilityRepositoryProvider,
      Provider<Context> contextProvider) {
    this.generateRepliesUseCaseProvider = generateRepliesUseCaseProvider;
    this.accessibilityRepositoryProvider = accessibilityRepositoryProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public KeyboardViewModel get() {
    return newInstance(generateRepliesUseCaseProvider.get(), accessibilityRepositoryProvider.get(), contextProvider.get());
  }

  public static KeyboardViewModel_Factory create(
      Provider<GenerateRepliesUseCase> generateRepliesUseCaseProvider,
      Provider<AccessibilityRepository> accessibilityRepositoryProvider,
      Provider<Context> contextProvider) {
    return new KeyboardViewModel_Factory(generateRepliesUseCaseProvider, accessibilityRepositoryProvider, contextProvider);
  }

  public static KeyboardViewModel newInstance(GenerateRepliesUseCase generateRepliesUseCase,
      AccessibilityRepository accessibilityRepository, Context context) {
    return new KeyboardViewModel(generateRepliesUseCase, accessibilityRepository, context);
  }
}
