package es.bonus.android.pages

import androidx.compose.Composable
import androidx.compose.Providers
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.tag
import androidx.ui.foundation.Text
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import es.bonus.android.Ambients
import es.bonus.android.GLOBAL_HOR_PADDING
import es.bonus.android.components.Avatar
import es.bonus.android.components.OwnedAssetView
import es.bonus.android.components.TableData
import es.bonus.android.components.TableView
import es.bonus.android.data.UserEventType
import es.bonus.android.features.*
import es.bonus.android.prettyTimestamp
import es.bonus.android.state
import es.bonus.android.ui.BonusTheme
import es.bonus.android.ui.Colors


@Composable
fun ProfilePage() {
    val userStore = Ambients.UserStore.current
    val eventStore = Ambients.EventStore.current
    val routingStore = Ambients.RoutingStore.current

    ConstraintLayout(
        ConstraintSet2 {
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
            img = userStore.state.avatarImg,
            nickName = userStore.state.nickName,
            mod = Modifier.tag("avatar")
        )

        val data = eventStore.state.events
        val entries = data.map { TableData.TwoColumn(it, it.timestamp, false) }

        TableView(
            header = "Recent history",
            entries = entries,
            mod = Modifier.tag("table")
        ) { left, right, contrastColor ->
            val sign = when (left.type) {
                UserEventType.RECEIVE -> "+ "
                UserEventType.USE -> "- "
            }

            Row(verticalGravity = Alignment.CenterVertically) {
                Text(text = sign, style = MaterialTheme.typography.body1, color = contrastColor)
                OwnedAssetView(
                    ownedAsset = left.ownedAsset,
                    textColor = contrastColor
                )
            }
            Text(
                text = " " + prettyTimestamp(right.toLong()),
                style = MaterialTheme.typography.body1,
                fontSize = 14.sp,
                color = Colors.gray1
            )
        }

        Text(
            text = "more...",
            style = MaterialTheme.typography.caption,
            modifier = Modifier.tag("moreBtn")
        )

        Button(
            shape = MaterialTheme.shapes.large,
            onClick = { routingStore.goTo(AppRoute.Profile.MyIdentifier) },
            elevation = 0.dp,
            backgroundColor = Colors.accent,
            modifier = Modifier.tag("showIdBtn")
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
            modifier = Modifier.tag("myCompaniesBtn")
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
    BonusTheme {

        val userStore = createUserStore()
        val eventStore = createEventStore()

        eventStore.fetch()

        Providers(
            Ambients.UserStore provides userStore,
            Ambients.EventStore provides eventStore
        ) {
            ProfilePage()
        }
    }
}