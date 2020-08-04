package es.bonus.android.components

import android.graphics.BitmapFactory
import androidx.compose.Composable
import androidx.ui.core.ContentScale
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.tag
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.graphics.asImageAsset
import androidx.ui.layout.*
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import es.bonus.android.GLOBAL_HOR_PADDING
import es.bonus.android.R
import es.bonus.android.data.bonuses
import es.bonus.android.features.Company
import es.bonus.android.getResourceBytes
import es.bonus.android.ui.BonusTheme
import es.bonus.android.ui.Colors

@Composable
fun CompanyListItemView(
    company: Company,
    mod: Modifier = Modifier,
    headerAddon: @Composable() () -> Unit = {}
) {
    ConstraintLayout(
        ConstraintSet2 {
            val logo = createRefFor("logo")
            val header = createRefFor("header")
            val description = createRefFor("description")
            val continueBtn = createRefFor("continueBtn")
            val divider = createRefFor("divider")

            val verticalPadding = 15.dp

            constrain(logo) {
                top.linkTo(parent.top, verticalPadding)
                bottom.linkTo(parent.bottom, verticalPadding)
                start.linkTo(parent.start)
                end.linkTo(parent.end)

                width = Dimension.wrapContent
                height = Dimension.fillToConstraints
            }

            constrain(header) {
                start.linkTo(parent.start, GLOBAL_HOR_PADDING.dp)
                end.linkTo(parent.end, GLOBAL_HOR_PADDING.dp)
                top.linkTo(parent.top, verticalPadding)

                width = Dimension.fillToConstraints
            }

            constrain(description) {
                start.linkTo(parent.start, GLOBAL_HOR_PADDING.dp)
                end.linkTo(parent.end, GLOBAL_HOR_PADDING.dp)
                top.linkTo(header.bottom, 7.dp)

                width = Dimension.fillToConstraints
            }

            constrain(continueBtn) {
                end.linkTo(parent.end, GLOBAL_HOR_PADDING.dp)
                top.linkTo(description.bottom, 5.dp)
            }

            constrain(divider) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(continueBtn.bottom, 10.dp)
                bottom.linkTo(parent.bottom)
            }
        },
        modifier = Modifier.fillMaxWidth() + mod
    ) {
        val img = BitmapFactory
            .decodeByteArray(company.logoBytes, 0, company.logoBytes.size)
            .asImageAsset()

        Image(
            asset = img,
            alpha = 0.15f,
            modifier = Modifier.tag("logo").aspectRatio(1f),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.tag("header"),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = company.name,
                style = MaterialTheme.typography.h3
            )

            headerAddon()
        }
        Text(
            text = company.description,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.tag("description")
        )
        Text(
            text = "Continue...",
            style = MaterialTheme.typography.caption,
            modifier = Modifier.tag("continueBtn")
        )
        Divider(color = Colors.accent, thickness = 2.dp, modifier = Modifier.tag("divider"))
    }
}

@Composable
@Preview
fun CompanyListItemViewPreview() {
    BonusTheme {
        val context = ContextAmbient.current
        val mcDoodlesLogo = context.getResourceBytes(R.raw.mc_doodles_logo)
        val beautifulCompLogo = context.getResourceBytes(R.raw.beautiful_company_logo)
        val vapeShopLogo = context.getResourceBytes(R.raw.vapeshop_logo)

        val comp1 = Company(
            name = "Beautiful Company",
            logoBytes = beautifulCompLogo,
            description = "multi-level marketing company in beauty, house- hold, and personal care categories"
        )

        val comp2 = Company(
            name = "VapeShop",
            logoBytes = vapeShopLogo,
            description = "around a third of all sales of e-cigarette products take place in vape shops"
        )

        val comp3 = Company(
            name = "McDoodles",
            logoBytes = mcDoodlesLogo,
            description = "the world's largest restaurant chain by revenue, serving over 69 million customers daily in over 100 countries across 37,855 outlets as of 2018"
        )

        Column {
            CompanyListItemView(comp1)
            CompanyListItemView(comp2) {
                OwnedAssetView(
                    ownedAsset = 200.bonuses.toOwnedAsset(comp2),
                    showTooltip = false
                )
            }
            CompanyListItemView(comp3) {
                OwnedAssetView(
                    ownedAsset = 200.bonuses.toOwnedAsset(comp3),
                    showTooltip = false
                )
            }
        }
    }
}