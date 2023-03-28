package com.pico.mystockmarket.di

import android.content.Context
import androidx.room.Room
import com.pico.mystockmarket.data.local.CompanyListingDao
import com.pico.mystockmarket.data.local.StockDatabase
import com.pico.mystockmarket.data.remote.StockApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockApi(): StockApi {
        return Retrofit
            .Builder()
            .baseUrl(StockApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StockApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStockDatabase(@ApplicationContext ctx: Context): StockDatabase {
        return Room
            .databaseBuilder(
                ctx,
                StockDatabase::class.java,
                "stock_db"
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideCompanyListingDao(stockDatabase: StockDatabase): CompanyListingDao {
        return stockDatabase.companyListingDao
    }
}