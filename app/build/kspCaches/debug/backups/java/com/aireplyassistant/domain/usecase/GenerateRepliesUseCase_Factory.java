package com.aireplyassistant.domain.usecase;

import com.aireplyassistant.domain.router.AIRouter;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
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
public final class GenerateRepliesUseCase_Factory implements Factory<GenerateRepliesUseCase> {
  private final Provider<AIRouter> aiRouterProvider;

  private GenerateRepliesUseCase_Factory(Provider<AIRouter> aiRouterProvider) {
    this.aiRouterProvider = aiRouterProvider;
  }

  @Override
  public GenerateRepliesUseCase get() {
    return newInstance(aiRouterProvider.get());
  }

  public static GenerateRepliesUseCase_Factory create(Provider<AIRouter> aiRouterProvider) {
    return new GenerateRepliesUseCase_Factory(aiRouterProvider);
  }

  public static GenerateRepliesUseCase newInstance(AIRouter aiRouter) {
    return new GenerateRepliesUseCase(aiRouter);
  }
}
