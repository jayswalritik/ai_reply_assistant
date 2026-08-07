package com.aireplyassistant.data.repository;

import com.aireplyassistant.domain.usecase.GenerateRepliesUseCase;
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
public final class ReplyGenerationRepositoryImpl_Factory implements Factory<ReplyGenerationRepositoryImpl> {
  private final Provider<GenerateRepliesUseCase> generateRepliesUseCaseProvider;

  private ReplyGenerationRepositoryImpl_Factory(
      Provider<GenerateRepliesUseCase> generateRepliesUseCaseProvider) {
    this.generateRepliesUseCaseProvider = generateRepliesUseCaseProvider;
  }

  @Override
  public ReplyGenerationRepositoryImpl get() {
    return newInstance(generateRepliesUseCaseProvider.get());
  }

  public static ReplyGenerationRepositoryImpl_Factory create(
      Provider<GenerateRepliesUseCase> generateRepliesUseCaseProvider) {
    return new ReplyGenerationRepositoryImpl_Factory(generateRepliesUseCaseProvider);
  }

  public static ReplyGenerationRepositoryImpl newInstance(
      GenerateRepliesUseCase generateRepliesUseCase) {
    return new ReplyGenerationRepositoryImpl(generateRepliesUseCase);
  }
}
