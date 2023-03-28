package com.pico.mystockmarket.presentation.company_listing

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pico.mystockmarket.presentation.company_listing.components.CompanyListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyListingScreen(viewModel: CompanyListingViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState

    Scaffold() { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = {
                    viewModel.onEvent(CompanyListingEvents.OnSearchQueryChange(it))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(){
                items(uiState.companies){ company ->
                    CompanyListItem(company = company)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            if(uiState.isLoading){
                Box {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}