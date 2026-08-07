package com.aireplyassistant.presentation.keyboard;

import com.aireplyassistant.domain.repository.AccessibilityRepository;
import com.aireplyassistant.domain.usecase.GenerateRepliesUseCase;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;

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
public final class AIKeyboardService_MembersInjector implements MembersInjector<AIKeyboardService> {
  private final Provider<GenerateRepliesUseCase> generateRepliesUseCaseProvider;

  private final Provider<AccessibilityRepository> accessibilityRepositoryProvider;

  private AIKeyboardService_MembersInjector(
      Provider<GenerateRepliesUseCase> generateRepliesUseCaseProvider,
      Provider<AccessibilityRepository> accessibilityRepositoryProvider) {
    this.generateRepliesUseCaseProvider = generateRepliesUseCaseProvider;
    this.accessibilityRepositoryProvider = accessibilityRepositoryProvider;
  }

  @Override
  public void injectMembers(AIKeyboardService instance) {
    injectGenerateRepliesUseCase(instance, generateRepliesUseCaseProvider.get());
    injectAccessibilityRepository(instance, accessibilityRepositoryProvider.get());
  }

  public static MembersInjector<AIKeyboardService> create(
      Provider<GenerateRepliesUseCase> generateRepliesUseCaseProvider,
      Provider<AccessibilityRepository> accessibilityRepositoryProvider) {
    return new AIKeyboardService_MembersInjector(generateRepliesUseCaseProvider, accessibilityRepositoryProvider);
  }

  @InjectedFieldSignature("com.aireplyassistant.presentation.keyboard.AIKeyboardService.generateRepliesUseCase")
  public static void injectGenerateRepliesUseCase(AIKeyboardService instance,
      GenerateRepliesUseCase generateRepliesUseCase) {
    instance.generateRepliesUseCase = generateRepliesUseCase;
  }

  @InjectedFieldSignature("com.aireplyassistant.presentation.keyboard.AIKeyboardService.accessibilityRepository")
  public static void injectAccessibilityRepository(AIKeyboardService instance,
      AccessibilityRepository accessibilityRepository) {
    instance.accessibilityRepository = accessibilityRepository;
  }
}
