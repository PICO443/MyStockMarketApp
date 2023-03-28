package com.pico.mystockmarket.domain.repository

import com.pico.mystockmarket.domain.model.CompanyListing
import com.pico.mystockmarket.util.Resource
import kotlinx.coroutines.flow.Flow

interface CompanyListingRepository {

    fun getCompanyListings(
        query: String,
        remoteFetch: Boolean
    ): Flow<Resource<List<CompanyListing>>>
}