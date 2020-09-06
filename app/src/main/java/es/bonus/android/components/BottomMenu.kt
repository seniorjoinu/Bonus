package es.bonus.android.components

import androidx.compose.foundation.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import es.bonus.android.GLOBAL_HOR_PADDING
import es.bonus.android.R
import es.bonus.android.features.AppRoute
import es.bonus.android.features.RoutingStore
import es.bonus.android.features.createRoutingStore
import es.bonus.android.features.goTo
import es.bonus.android.ui.BonusTheme

@Composable
fun BottomMenu(
    routingStore: RoutingStore,
    modifier: Modifier = Modifier
) {
    val iconHeightModifier = Modifier.height(40.dp)
    val iconWidthModifier = Modifier.width(40.dp)
    val iconGravityModifier = Modifier.fillMaxSize()
    val iconSizeModifier = iconHeightModifier then iconWidthModifier then iconGravityModifier

    Row(
        modifier = modifier.then(
            Modifier.fillMaxWidth().padding(horizontal = GLOBAL_HOR_PADDING.dp)
        ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalGravity = Alignment.CenterVertically
    ) {
        Icon(
            asset = vectorResource(id = R.drawable.ic_home),
            modifier = iconSizeModifier then Modifier.clickable {
                routingStore.goTo(AppRoute.Profile.Index)
            },
            tint = MaterialTheme.colors.primary
        )
        Icon(
            asset = vectorResource(id = R.drawable.ic_diamond),
            modifier = iconSizeModifier.then(Modifier.clickable {
                routingStore.goTo(AppRoute.MyBonuses.Index)
            }),
            tint = MaterialTheme.colors.primary
        )
        Icon(
            asset = vectorResource(id = R.drawable.ic_present),
            modifier = iconSizeModifier.then(Modifier.clickable {
                routingStore.goTo(AppRoute.MyRewards.Index)
            }),
            tint = MaterialTheme.colors.primary
        )
        Icon(
            asset = vectorResource(id = R.drawable.ic_exchange),
            modifier = iconSizeModifier.then(Modifier.clickable {
                routingStore.goTo(AppRoute.BonusExchange.Index)
            }),
            tint = MaterialTheme.colors.primary
        )
    }
}

//@Preview
@Composable
fun BottomMenuPreview() {
    BonusTheme {
        val router = createRoutingStore()

        BottomMenu(router)
    }
}