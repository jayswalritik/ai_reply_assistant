package com.aireplyassistant.presentation.accessibility;

import com.aireplyassistant.domain.repository.AccessibilityRepository;
import com.aireplyassistant.presentation.accessibility.detector.GenericTextBlockDetector;
import com.aireplyassistant.presentation.accessibility.ocr.VisualTextExtractor;
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
public final class AIAccessibilityService_MembersInjector implements MembersInjector<AIAccessibilityService> {
  private final Provider<AccessibilityRepository> accessibilityRepositoryProvider;

  private final Provider<VisualTextExtractor> visualTextExtractorProvider;

  private final Provider<GenericTextBlockDetector> genericTextBlockDetectorProvider;

  private AIAccessibilityService_MembersInjector(
      Provider<AccessibilityRepository> accessibilityRepositoryProvider,
      Provider<VisualTextExtractor> visualTextExtractorProvider,
      Provider<GenericTextBlockDetector> genericTextBlockDetectorProvider) {
    this.accessibilityRepositoryProvider = accessibilityRepositoryProvider;
    this.visualTextExtractorProvider = visualTextExtractorProvider;
    this.genericTextBlockDetectorProvider = genericTextBlockDetectorProvider;
  }

  @Override
  public void injectMembers(AIAccessibilityService instance) {
    injectAccessibilityRepository(instance, accessibilityRepositoryProvider.get());
    injectVisualTextExtractor(instance, visualTextExtractorProvider.get());
    injectGenericTextBlockDetector(instance, genericTextBlockDetectorProvider.get());
  }

  public static MembersInjector<AIAccessibilityService> create(
      Provider<AccessibilityRepository> accessibilityRepositoryProvider,
      Provider<VisualTextExtractor> visualTextExtractorProvider,
      Provider<GenericTextBlockDetector> genericTextBlockDetectorProvider) {
    return new AIAccessibilityService_MembersInjector(accessibilityRepositoryProvider, visualTextExtractorProvider, genericTextBlockDetectorProvider);
  }

  @InjectedFieldSignature("com.aireplyassistant.presentation.accessibility.AIAccessibilityService.accessibilityRepository")
  public static void injectAccessibilityRepository(AIAccessibilityService instance,
      AccessibilityRepository accessibilityRepository) {
    instance.accessibilityRepository = accessibilityRepository;
  }

  @InjectedFieldSignature("com.aireplyassistant.presentation.accessibility.AIAccessibilityService.visualTextExtractor")
  public static void injectVisualTextExtractor(AIAccessibilityService instance,
      VisualTextExtractor visualTextExtractor) {
    instance.visualTextExtractor = visualTextExtractor;
  }

  @InjectedFieldSignature("com.aireplyassistant.presentation.accessibility.AIAccessibilityService.genericTextBlockDetector")
  public static void injectGenericTextBlockDetector(AIAccessibilityService instance,
      GenericTextBlockDetector genericTextBlockDetector) {
    instance.genericTextBlockDetector = genericTextBlockDetector;
  }
}
