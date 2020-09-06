package es.bonus.android.components

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.ui.tooling.preview.Preview
import es.bonus.android.ui.BonusTheme

@Composable
fun TopHeader(header: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalGravity = Alignment.CenterVertically
    ) {
        Text(
            text = header,
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopHeaderPreview() {
    BonusTheme {
        TopHeader("Example header")
    }
}