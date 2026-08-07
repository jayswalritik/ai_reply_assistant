package com.aireplyassistant.data.repository;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class ChatGptConversationRepositoryImpl_Factory implements Factory<ChatGptConversationRepositoryImpl> {
  private final Provider<Context> contextProvider;

  private ChatGptConversationRepositoryImpl_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ChatGptConversationRepositoryImpl get() {
    return newInstance(contextProvider.get());
  }

  public static ChatGptConversationRepositoryImpl_Factory create(
      Provider<Context> contextProvider) {
    return new ChatGptConversationRepositoryImpl_Factory(contextProvider);
  }

  public static ChatGptConversationRepositoryImpl newInstance(Context context) {
    return new ChatGptConversationRepositoryImpl(context);
  }
}
