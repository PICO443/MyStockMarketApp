package com.pico.mystockmarket.di

import com.pico.mystockmarket.data.csv.CompanyListingCsvParser
import com.pico.mystockmarket.data.csv.CsvParser
import com.pico.mystockmarket.data.repository.CompanyListingRepositoryImpl
import com.pico.mystockmarket.domain.model.CompanyListing
import com.pico.mystockmarket.domain.repository.CompanyListingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingCsvParser(companyListingCsvParser: CompanyListingCsvParser): CsvParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindCompanyListingRepository(companyListingRepository: CompanyListingRepositoryImpl): CompanyListingRepository
}