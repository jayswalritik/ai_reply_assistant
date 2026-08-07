package com.aireplyassistant.di;

import com.aireplyassistant.domain.router.AIRouter;
import com.aireplyassistant.domain.usecase.GenerateRepliesUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideGenerateRepliesUseCaseFactory implements Factory<GenerateRepliesUseCase> {
  private final Provider<AIRouter> routerProvider;

  private AppModule_ProvideGenerateRepliesUseCaseFactory(Provider<AIRouter> routerProvider) {
    this.routerProvider = routerProvider;
  }

  @Override
  public GenerateRepliesUseCase get() {
    return provideGenerateRepliesUseCase(routerProvider.get());
  }

  public static AppModule_ProvideGenerateRepliesUseCaseFactory create(
      Provider<AIRouter> routerProvider) {
    return new AppModule_ProvideGenerateRepliesUseCaseFactory(routerProvider);
  }

  public static GenerateRepliesUseCase provideGenerateRepliesUseCase(AIRouter router) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideGenerateRepliesUseCase(router));
  }
}
