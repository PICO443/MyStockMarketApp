package com.pico.mystockmarket.presentation.company_listing

import com.pico.mystockmarket.domain.model.CompanyListing

data class CompanyListingUiState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = true,
    val searchQuery: String = ""
)