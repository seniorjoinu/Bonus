package es.bonus.android.components

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.layout.Arrangement
import androidx.ui.layout.Row
import androidx.ui.material.MaterialTheme
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