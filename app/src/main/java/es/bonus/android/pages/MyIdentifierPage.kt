package es.bonus.android.pages

import androidx.compose.Composable
import androidx.compose.Providers
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.layout.Arrangement
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.padding
import androidx.ui.material.MaterialTheme
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
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