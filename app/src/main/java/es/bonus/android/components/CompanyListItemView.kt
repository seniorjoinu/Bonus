package es.bonus.android.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageAsset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import es.bonus.android.GLOBAL_HOR_PADDING
import es.bonus.android.data.bonuses
import es.bonus.android.data.dummySetup
import es.bonus.android.features.Company
import es.bonus.android.ui.BonusTheme
import es.bonus.android.ui.Colors
import kotlinx.coroutines.runBlocking

@Composable
fun CompanyListItemView(
    company: Company,
    mod: Modifier = Modifier,
    headerAddon: @Composable () -> Unit = {}
) {
    ConstraintLayout(
        ConstraintSet {
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
        modifier = Modifier.fillMaxWidth().then(mod)
    ) {
        val img = BitmapFactory
            .decodeByteArray(company.logoBytes, 0, company.logoBytes.size)
            .asImageAsset()

        Image(
            asset = img,
            alpha = 0.15f,
            modifier = Modifier.layoutId("logo").aspectRatio(1f),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.layoutId("header"),
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
            modifier = Modifier.layoutId("description")
        )
        Text(
            text = "Continue...",
            style = MaterialTheme.typography.caption,
            modifier = Modifier.layoutId("continueBtn")
        )
        Divider(color = Colors.accent, thickness = 2.dp, modifier = Modifier.layoutId("divider"))
    }
}

@Composable
@Preview
fun CompanyListItemViewPreview() {
    val (client1, _) = dummySetup()
    val companies = runBlocking { client1.getCompanies(emptyList()).unwrap() }

    BonusTheme {
        Column {
            CompanyListItemView(company = companies.first())
            CompanyListItemView(company = companies.last()) {
                OwnedAssetView(
                    ownedAsset = 200.bonuses.toOwnedAsset()
                )
            }
        }
    }
}