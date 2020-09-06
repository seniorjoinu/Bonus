package es.bonus.android.pages

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import es.bonus.android.Ambients
import es.bonus.android.components.QRCode
import es.bonus.android.features.createUserStore
import es.bonus.android.state
import es.bonus.android.ui.BonusTheme

@Composable
fun MyIdentifierPage() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalGravity = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(horizontalGravity = Alignment.CenterHorizontally) {
            val userStore = Ambients.UserStore.current

            QRCode(data = "{userId: \"${userStore.state.currentUser.id}\"}")
            Text(
                modifier = Modifier.padding(top = 35.dp),
                text = userStore.state.currentUser.id.toString(16).padStart(6, '0'),
                style = MaterialTheme.typography.subtitle1,
                fontSize = 32.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun MyIdentifierPagePreview() {
    val userStore = createUserStore()

    BonusTheme {
        Providers(Ambients.UserStore provides userStore) {
            MyIdentifierPage()
        }
    }
}