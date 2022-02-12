package com.example.paginationwithcompose.di

import android.content.Context
import androidx.room.Room
import com.example.paginationwithcompose.BuildConfig
import com.example.paginationwithcompose.common.Constants
import com.example.paginationwithcompose.common.Endpoints
import com.example.paginationwithcompose.data.local.db.BreedsDatabase
import com.example.paginationwithcompose.data.remote.ApiService
import com.example.paginationwithcompose.repo.AppRepository
import com.example.paginationwithcompose.repo.AppRepositoryImpl
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesApiService(
        okHttpClient: OkHttpClient
    ): ApiService {
        return Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Endpoints.BASE_URL)
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return if (BuildConfig.DEBUG) {
            //this is for logging profiler
            OkHttpClient
                .Builder()
                .addInterceptor(OkHttpProfilerInterceptor())
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()

            //.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

        } else OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providesAppRepository(
        apiService: ApiService,
        database: BreedsDatabase
    ): AppRepository {
        return AppRepositoryImpl(
            apiService = apiService,
            database = database
        )
    }


    @Provides
    @Singleton
    fun provideBreedDatabase(
        @ApplicationContext context: Context
    ): BreedsDatabase {
        return Room.databaseBuilder(
            context,
            BreedsDatabase::class.java,
            Constants.BREED_DATABASE
        ).fallbackToDestructiveMigration().build()
    }
}