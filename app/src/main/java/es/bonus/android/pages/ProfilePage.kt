package es.bonus.android.pages

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import es.bonus.android.Ambients
import es.bonus.android.GLOBAL_HOR_PADDING
import es.bonus.android.asImageAsset
import es.bonus.android.components.Avatar
import es.bonus.android.components.EventTable
import es.bonus.android.features.*
import es.bonus.android.state
import es.bonus.android.ui.BonusTheme
import es.bonus.android.ui.Colors


@Composable
fun ProfilePage() {
    val userStore = Ambients.UserStore.current
    val eventStore = Ambients.EventStore.current
    val routingStore = Ambients.RoutingStore.current

    ConstraintLayout(
        ConstraintSet {
            val avatar = createRefFor("avatar")

            constrain(avatar) {
                top.linkTo(parent.top, 15.dp)
                absoluteLeft.linkTo(parent.absoluteLeft)
                absoluteRight.linkTo(parent.absoluteRight)

                centerHorizontallyTo(parent)
            }

            val table = createRefFor("table")

            constrain(table) {
                top.linkTo(avatar.bottom, 20.dp)
                absoluteLeft.linkTo(parent.absoluteLeft)
                absoluteRight.linkTo(parent.absoluteRight)
            }

            val moreBtn = createRefFor("moreBtn")

            constrain(moreBtn) {
                top.linkTo(table.bottom, 12.dp)
                absoluteRight.linkTo(parent.absoluteRight, GLOBAL_HOR_PADDING.dp)
            }

            val showIdBtn = createRefFor("showIdBtn")

            val myCompaniesBtn = createRefFor("myCompaniesBtn")

            constrain(showIdBtn) {
                bottom.linkTo(myCompaniesBtn.top, 12.dp)
            }

            constrain(myCompaniesBtn) {
                bottom.linkTo(parent.bottom, 20.dp)
            }
        },
        Modifier.fillMaxSize()
    ) {
        Avatar(
            img = userStore.state.currentUser.avatarBytes.asImageAsset(),
            nickName = userStore.state.currentUser.nickName,
            mod = Modifier.layoutId("avatar")
        )

        val data = eventStore.state.events
        println("events: $data")
        EventTable(data, EventEntity.USER, mod = Modifier.layoutId("table"))

        Text(
            text = "more...",
            style = MaterialTheme.typography.caption,
            modifier = Modifier.layoutId("moreBtn")
        )

        Button(
            shape = MaterialTheme.shapes.large,
            onClick = { routingStore.goTo(AppRoute.Profile.MyIdentifier) },
            elevation = 0.dp,
            backgroundColor = Colors.accent,
            modifier = Modifier.layoutId("showIdBtn")
                .padding(horizontal = GLOBAL_HOR_PADDING.dp)
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Show ID", style = MaterialTheme.typography.button, textAlign = TextAlign.Center)
        }

        Button(
            shape = MaterialTheme.shapes.large,
            onClick = { routingStore.goTo(AppRoute.Profile.MyCompanies) },
            elevation = 0.dp,
            border = null,
            backgroundColor = Colors.darkBackground,
            modifier = Modifier.layoutId("myCompaniesBtn")
                .padding(horizontal = GLOBAL_HOR_PADDING.dp)
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                "My companies",
                style = MaterialTheme.typography.button,
                textAlign = TextAlign.Center,
                color = Colors.accent
            )
        }
    }
}

@Preview
@Composable
fun ProfilePagePreview() {
    val context = ContextAmbient.current
    Companies.init(context)
    Users.init(context)

    BonusTheme {
        val userStore = createUserStore()
        val eventStore = createEventStore()

        userStore.setCurrentUser(Users.random())
        eventStore.fetchEvents()

        Providers(
            Ambients.UserStore provides userStore,
            Ambients.EventStore provides eventStore
        ) {
            ProfilePage()
        }
    }
}