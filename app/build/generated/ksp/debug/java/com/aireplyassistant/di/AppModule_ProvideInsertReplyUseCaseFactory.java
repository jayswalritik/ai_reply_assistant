package com.aireplyassistant.di;

import com.aireplyassistant.domain.usecase.InsertReplyUseCase;
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
public final class AppModule_ProvideInsertReplyUseCaseFactory implements Factory<InsertReplyUseCase> {
  @Override
  public InsertReplyUseCase get() {
    return provideInsertReplyUseCase();
  }

  public static AppModule_ProvideInsertReplyUseCaseFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static InsertReplyUseCase provideInsertReplyUseCase() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideInsertReplyUseCase());
  }

  private static final class InstanceHolder {
    static final AppModule_ProvideInsertReplyUseCaseFactory INSTANCE = new AppModule_ProvideInsertReplyUseCaseFactory();
  }
}
