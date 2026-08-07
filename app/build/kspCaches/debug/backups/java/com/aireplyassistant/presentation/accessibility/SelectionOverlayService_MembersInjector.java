package com.aireplyassistant.presentation.accessibility;

import com.aireplyassistant.domain.repository.AccessibilityRepository;
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
public final class SelectionOverlayService_MembersInjector implements MembersInjector<SelectionOverlayService> {
  private final Provider<AccessibilityRepository> accessibilityRepositoryProvider;

  private SelectionOverlayService_MembersInjector(
      Provider<AccessibilityRepository> accessibilityRepositoryProvider) {
    this.accessibilityRepositoryProvider = accessibilityRepositoryProvider;
  }

  @Override
  public void injectMembers(SelectionOverlayService instance) {
    injectAccessibilityRepository(instance, accessibilityRepositoryProvider.get());
  }

  public static MembersInjector<SelectionOverlayService> create(
      Provider<AccessibilityRepository> accessibilityRepositoryProvider) {
    return new SelectionOverlayService_MembersInjector(accessibilityRepositoryProvider);
  }

  @InjectedFieldSignature("com.aireplyassistant.presentation.accessibility.SelectionOverlayService.accessibilityRepository")
  public static void injectAccessibilityRepository(SelectionOverlayService instance,
      AccessibilityRepository accessibilityRepository) {
    instance.accessibilityRepository = accessibilityRepository;
  }
}
