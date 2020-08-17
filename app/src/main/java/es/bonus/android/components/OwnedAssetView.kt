package es.bonus.android.components

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.height
import androidx.ui.material.MaterialTheme
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
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