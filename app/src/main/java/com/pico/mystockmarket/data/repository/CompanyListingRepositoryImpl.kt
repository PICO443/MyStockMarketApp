package com.pico.mystockmarket.data.repository

import com.pico.mystockmarket.data.csv.CsvParser
import com.pico.mystockmarket.data.local.CompanyListingDao
import com.pico.mystockmarket.data.mapper.toCompanyListing
import com.pico.mystockmarket.data.mapper.toCompanyListingEntity
import com.pico.mystockmarket.data.remote.StockApi
import com.pico.mystockmarket.domain.model.CompanyListing
import com.pico.mystockmarket.domain.repository.CompanyListingRepository
import com.pico.mystockmarket.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class CompanyListingRepositoryImpl(
    private val dao: CompanyListingDao,
    private val api: StockApi,
    private val companyListingCsvParser: CsvParser<CompanyListing>
) : CompanyListingRepository {
    override fun getCompanyListings(
        query: String,
        remoteFetch: Boolean
    ): Flow<Resource<List<CompanyListing>>> = flow {
        emit(Resource.Loading())
        var cachedCompanyListings = dao.searchCompanyListing(query)
        val shouldJustLoadFromCache = cachedCompanyListings.isNotEmpty().and(query.isNotBlank())
        if(shouldJustLoadFromCache){
            emit(Resource.Success(cachedCompanyListings.map { it.toCompanyListing() }))
            return@flow
        }
        try {
            val response = api.getListings()
            val remoteCompanyListing = companyListingCsvParser.parse(response.byteStream())
            dao.clearCompanyListings()
            dao.insertCompanyListings(remoteCompanyListing.map { it.toCompanyListingEntity() })
            cachedCompanyListings = dao.searchCompanyListing("")
            emit(Resource.Success(data = cachedCompanyListings.map { it.toCompanyListing() }))
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error(message = "Couldn't reach the server ${e.message}"))
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error(message = "HttpException: ${e.message}"))
        }
    }
}