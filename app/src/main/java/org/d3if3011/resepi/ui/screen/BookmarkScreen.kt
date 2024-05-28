package org.d3if3011.resepi.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.d3if3011.resepi.R
import org.d3if3011.resepi.ui.theme.ResepiTheme

@Composable
fun BookmarkScreen(modifier: Modifier) {
    ResepiTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            BookmarkListItem()
        }
    }
}

@Composable
fun BookmarkListItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "Ayam Goreng Crispy",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = "Ayam goreng dicampur dengan taburan crispy",
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Image(painter = painterResource(id = R.drawable.clock), contentDescription = null)
                Text(
                    text = "60 menit",
                    maxLines = 1,
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.example_chicken),
            contentDescription = stringResource(id = R.string.food),
            modifier = Modifier
                .size(124.dp)
                .clip(shape = RoundedCornerShape(12.dp))
        )
    }
}

@Preview
@Composable
fun BookmarkListItemPreview() {
    ResepiTheme {
        BookmarkListItem()
    }
}

