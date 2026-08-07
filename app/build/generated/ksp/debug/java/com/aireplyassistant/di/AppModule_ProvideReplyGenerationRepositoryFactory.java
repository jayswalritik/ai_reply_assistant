package com.aireplyassistant.di;

import com.aireplyassistant.domain.repository.ReplyGenerationRepository;
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
public final class AppModule_ProvideReplyGenerationRepositoryFactory implements Factory<ReplyGenerationRepository> {
  private final Provider<GenerateRepliesUseCase> genProvider;

  private AppModule_ProvideReplyGenerationRepositoryFactory(
      Provider<GenerateRepliesUseCase> genProvider) {
    this.genProvider = genProvider;
  }

  @Override
  public ReplyGenerationRepository get() {
    return provideReplyGenerationRepository(genProvider.get());
  }

  public static AppModule_ProvideReplyGenerationRepositoryFactory create(
      Provider<GenerateRepliesUseCase> genProvider) {
    return new AppModule_ProvideReplyGenerationRepositoryFactory(genProvider);
  }

  public static ReplyGenerationRepository provideReplyGenerationRepository(
      GenerateRepliesUseCase gen) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideReplyGenerationRepository(gen));
  }
}
