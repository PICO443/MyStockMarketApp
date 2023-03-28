package com.pico.mystockmarket.presentation.company_listing.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pico.mystockmarket.domain.model.CompanyListing
import com.pico.mystockmarket.ui.theme.MyStockMarketTheme

@Composable
fun CompanyListItem(company: CompanyListing, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row{
            Text(
                text = company.name,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = company.exchange)
        }
        Text(text = company.symbol, fontStyle = FontStyle.Italic)
    }
}

@Preview(showSystemUi = true)
@Composable
fun CompanyListItemPreview(){
    MyStockMarketTheme() {
        CompanyListItem(company = CompanyListing(name = "Tesla", exchange = "TSL", symbol = "T"))
    }
}