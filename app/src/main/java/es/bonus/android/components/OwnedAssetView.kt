package es.bonus.android.components

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import es.bonus.android.R
import es.bonus.android.data.OwnedAsset
import es.bonus.android.data.bonuses
import es.bonus.android.data.rub
import es.bonus.android.ui.BonusTheme
import es.bonus.android.ui.Colors

@Composable
fun OwnedAssetView(
    ownedAsset: OwnedAsset,
    textColor: Color = Colors.white1,
    signColor: Color = Colors.accent,
    mod: Modifier = Modifier
) {
    Row(verticalGravity = Alignment.CenterVertically, modifier = mod) {
        when (ownedAsset) {
            is OwnedAsset.Bonus -> {
                Text(
                    text = "${ownedAsset.valueAsset} ",
                    style = MaterialTheme.typography.subtitle1,
                    color = textColor
                )
                Icon(
                    asset = vectorResource(id = R.drawable.ic_bonuses_sign),
                    tint = signColor
                )
            }
            is OwnedAsset.Discount.Currency -> {
                Text(
                    text = "${ownedAsset.valueAsset}",
                    style = MaterialTheme.typography.subtitle1,
                    color = textColor
                )
                Icon(
                    asset = vectorResource(id = R.drawable.ic_ruble_sign),
                    tint = signColor,
                    modifier = Modifier.height(14.dp)
                )
            }
            is OwnedAsset.Discount.Percent -> {
                Text(
                    text = "${ownedAsset.value}",
                    style = MaterialTheme.typography.subtitle1,
                    color = textColor
                )
                Text(
                    text = "% ",
                    style = MaterialTheme.typography.subtitle1,
                    color = signColor
                )
                Text(
                    text = "discount",
                    style = MaterialTheme.typography.subtitle1,
                    color = textColor
                )
            }
            is OwnedAsset.GiftCoupon -> {
                Text(
                    text = "a ",
                    style = MaterialTheme.typography.subtitle1,
                    color = textColor
                )
                Text(
                    text = "gift ",
                    style = MaterialTheme.typography.subtitle1,
                    color = signColor
                )
                Text(
                    text = "coupon",
                    style = MaterialTheme.typography.subtitle1,
                    color = textColor
                )
            }
        }
    }
}

@Preview
@Composable
fun OwnedAssetViewPreview() {
    BonusTheme {
        Column {
            OwnedAssetView(ownedAsset = 10.rub.toOwnedAsset())
            OwnedAssetView(ownedAsset = 340.rub.toOwnedAsset())
            OwnedAssetView(ownedAsset = 300.bonuses.toOwnedAsset())
            OwnedAssetView(ownedAsset = 1.bonuses.toOwnedAsset())
            OwnedAssetView(ownedAsset = OwnedAsset.Discount.Percent(25))
        }
    }
}