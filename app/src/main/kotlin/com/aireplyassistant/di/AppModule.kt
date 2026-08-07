package com.aireplyassistant.di

import android.content.Context
import com.aireplyassistant.data.api.*
import com.aireplyassistant.data.repository.AccessibilityRepositoryImpl
import com.aireplyassistant.data.repository.KeyboardRepositoryImpl
import com.aireplyassistant.data.repository.ReplyGenerationRepositoryImpl
import com.aireplyassistant.domain.repository.AccessibilityRepository
import com.aireplyassistant.domain.repository.KeyboardRepository
import com.aireplyassistant.domain.repository.ReplyGenerationRepository
import com.aireplyassistant.domain.router.AIRouter
import com.aireplyassistant.domain.router.CloudAIService
import com.aireplyassistant.domain.router.LocalAIClient
import com.aireplyassistant.domain.usecase.HandleKeyPressUseCase
import com.aireplyassistant.domain.usecase.GenerateRepliesUseCase
import com.aireplyassistant.domain.usecase.InsertReplyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton @Provides fun provideApplicationContext(@ApplicationContext context: Context): Context = context

    @Singleton @Provides fun provideKeyboardRepository(): KeyboardRepository = KeyboardRepositoryImpl()
    @Singleton @Provides fun provideHandleKeyPressUseCase(): HandleKeyPressUseCase = HandleKeyPressUseCase()
    @Singleton @Provides fun provideInsertReplyUseCase(): InsertReplyUseCase = InsertReplyUseCase()

    @Singleton @Provides fun provideAccessibilityRepository(): AccessibilityRepository = AccessibilityRepositoryImpl()

    @Singleton @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Singleton @Provides @Named("OllamaRetrofit")
    fun provideOllamaRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.137.1:11434")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton @Provides @Named("GeminiRetrofit")
    fun provideGeminiRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://generativelanguage.googleapis.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton @Provides @Named("GroqRetrofit")
    fun provideGroqRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.groq.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton @Provides fun provideOllamaApiService(@Named("OllamaRetrofit") r: Retrofit): OllamaApiService = r.create(OllamaApiService::class.java)
    @Singleton @Provides fun provideGeminiApiService(@Named("GeminiRetrofit") r: Retrofit): GeminiApiService = r.create(GeminiApiService::class.java)
    @Singleton @Provides fun provideGroqApiService(@Named("GroqRetrofit") r: Retrofit): GroqApiService = r.create(GroqApiService::class.java)

    @Singleton @Provides fun provideLocalAIClient(ollamaApi: OllamaApiService): LocalAIClient = LocalAIClientImpl(ollamaApi)
    @Singleton @Provides fun provideCloudAIService(gemini: GeminiApiService, groq: GroqApiService): CloudAIService = CloudAIServiceImpl(gemini, groq)
    @Singleton @Provides fun provideAIRouter(local: LocalAIClient, cloud: CloudAIService): AIRouter = AIRouter(local, cloud)
    @Singleton @Provides fun provideGenerateRepliesUseCase(router: AIRouter): GenerateRepliesUseCase = GenerateRepliesUseCase(router)

    @Singleton @Provides fun provideReplyGenerationRepository(gen: GenerateRepliesUseCase): ReplyGenerationRepository = ReplyGenerationRepositoryImpl(gen)
}
