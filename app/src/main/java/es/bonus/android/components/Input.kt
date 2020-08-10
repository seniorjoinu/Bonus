package es.bonus.android.components

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.layout.ConstraintLayout
import androidx.ui.layout.ConstraintSet2
import androidx.ui.material.MaterialTheme

@Composable
fun Input(
    value: String,
    label: String = "",
    onChange: (String) -> Unit
) {
    val expanded = state { false }

    ConstraintLayout(
        ConstraintSet2 {

        },
        modifier = Modifier.clickable {
            if (expanded.value) {

            }
        }
    ) {
        Text(label, style = MaterialTheme.typography.h3)

    }
}