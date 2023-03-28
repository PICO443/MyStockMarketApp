package com.pico.mystockmarket.presentation.company_listing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pico.mystockmarket.domain.repository.CompanyListingRepository
import com.pico.mystockmarket.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingViewModel @Inject constructor(
    private val companyListingRepository: CompanyListingRepository
) : ViewModel() {
    var uiState by mutableStateOf(CompanyListingUiState())

    var searchQueryJob: Job? = null

    fun onEvent(event: CompanyListingEvents) {
        when (event) {
            is CompanyListingEvents.Refresh -> {
                getCompanyListings(remoteFetch = true)
            }
            is CompanyListingEvents.OnSearchQueryChange -> {
                uiState = uiState.copy(searchQuery = event.query)
                searchQueryJob?.cancel()
                searchQueryJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListings()
                }
            }
        }
    }

    private fun getCompanyListings(query: String = uiState.searchQuery.lowercase(), remoteFetch: Boolean = false) {
        viewModelScope.launch {
            companyListingRepository
                .getCompanyListings(query, remoteFetch)
                .collect { result ->
                    when(result) {
                        is Resource.Loading -> {
                            uiState = uiState.copy(isLoading = true)
                        }
                        is Resource.Success -> {
                            uiState = uiState.copy(companies = result.data, isLoading = false)
                        }
                        is Resource.Error -> {

                        }
                    }
                }
        }
    }
}