package es.bonus.android.components

import androidx.compose.*
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.clickable
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.res.vectorResource
import androidx.ui.unit.dp
import es.bonus.android.*
import es.bonus.android.R
import es.bonus.android.features.*
import es.bonus.android.ui.BonusTheme

@Composable
fun BottomMenu(
    routingStore: RoutingStore,
    modifier: Modifier = Modifier
) {
    val iconHeightModifier = Modifier.height(40.dp)
    val iconWidthModifier = Modifier.width(40.dp)
    val iconGravityModifier = Modifier.fillMaxSize()
    val iconSizeModifier = iconHeightModifier + iconWidthModifier + iconGravityModifier

    Row(
        modifier = modifier + Modifier.fillMaxWidth().padding(horizontal = GLOBAL_HOR_PADDING.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalGravity = Alignment.CenterVertically
    ) {
        Icon(
            asset = vectorResource(id = R.drawable.ic_home),
            modifier = iconSizeModifier + Modifier.clickable {
                routingStore.goTo(AppRoute.Profile.Index)
            },
            tint = MaterialTheme.colors.primary
        )
        Icon(
            asset = vectorResource(id = R.drawable.ic_diamond),
            modifier = iconSizeModifier + Modifier.clickable {
                routingStore.goTo(AppRoute.MyBonuses.Index)
            },
            tint = MaterialTheme.colors.primary
        )
        Icon(
            asset = vectorResource(id = R.drawable.ic_present),
            modifier = iconSizeModifier + Modifier.clickable {
                routingStore.goTo(AppRoute.MyRewards.Index)
            },
            tint = MaterialTheme.colors.primary
        )
        Icon(
            asset = vectorResource(id = R.drawable.ic_exchange),
            modifier = iconSizeModifier + Modifier.clickable {
                routingStore.goTo(AppRoute.BonusExchange.Index)
            },
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