package es.bonus.android.components

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import es.bonus.android.R
import es.bonus.android.features.Company
import es.bonus.android.ui.BonusTheme
import es.bonus.android.ui.Colors
import java.math.BigInteger

@Composable
fun CompanyForm(
    company: Company,
    modifier: Modifier = Modifier,
    onCompanyChange: (Company) -> Unit
) {
    ConstraintLayout(
        ConstraintSet {
            val companyNameInput = createRefFor("companyNameInput")
            val companyDescriptionInput = createRefFor("companyDescriptionInput")
            val companyDiscountInput = createRefFor("companyDiscountInput")
            val companyLogoInput = createRefFor("companyLogoInput")

            constrain(companyNameInput) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)

                width = Dimension.fillToConstraints
            }

            constrain(companyDescriptionInput) {
                top.linkTo(companyNameInput.bottom, 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)

                width = Dimension.fillToConstraints
            }

            constrain(companyDiscountInput) {
                top.linkTo(companyDescriptionInput.bottom, 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)

                width = Dimension.fillToConstraints
            }

            constrain(companyLogoInput) {
                top.linkTo(companyDiscountInput.bottom, 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)

                width = Dimension.fillToConstraints
            }
        },
        modifier = Modifier.fillMaxWidth().then(modifier)
    ) {
        val type = MaterialTheme.typography
        val textStyleName = remember {
            mutableStateOf(if (company.name.isBlank()) type.h4 else type.h5)
        }

        TextField(
            modifier = Modifier.layoutId("companyNameInput"),
            value = company.name,
            onValueChange = {
                onCompanyChange(company.copy(name = it))
            },
            label = {
                Text("Company name", style = textStyleName.value)
            },
            inactiveColor = Colors.accent,
            backgroundColor = Colors.darkBackground
        )

        val textStyleDescription = remember {
            mutableStateOf(if (company.description.isBlank()) type.h4 else type.h5)
        }
        TextField(
            modifier = Modifier.layoutId("companyDescriptionInput"),
            value = company.description,
            onValueChange = {
                onCompanyChange(company.copy(description = it))
            },
            label = {
                Text(text = "Company description", style = textStyleDescription.value)
            },
            inactiveColor = Colors.accent,
            backgroundColor = Colors.darkBackground
        )

        val textStyleDiscount = remember {
            mutableStateOf(if (company.discount >= BigInteger.ZERO) type.h5 else type.h4)
        }
        TextField(
            modifier = Modifier.layoutId("companyDiscountInput"),
            value = if (company.discount >= BigInteger.ZERO) company.discount.toString() else "",
            onValueChange = {
                onCompanyChange(
                    company.copy(
                        discount = if (it.isNotBlank()) it.toBigInteger() else BigInteger("-1")
                    )
                )
            },
            label = {
                Row(verticalGravity = Alignment.CenterVertically) {
                    Text(text = "Discount ", style = textStyleDiscount.value)
                    Icon(
                        asset = vectorResource(id = R.drawable.ic_ruble_sign),
                        tint = Colors.accent,
                        modifier = Modifier.height(textStyleDiscount.value.fontSize.value.dp)
                    )
                    Text(text = " per 1 ", style = textStyleDiscount.value)
                    Icon(
                        asset = vectorResource(id = R.drawable.ic_bonuses_sign),
                        tint = Colors.accent,
                        modifier = Modifier.height(textStyleDiscount.value.fontSize.value.dp)
                    )
                }
            },
            inactiveColor = Colors.accent,
            backgroundColor = Colors.darkBackground,
            keyboardType = KeyboardType.Number
        )

        ConstraintLayout(
            ConstraintSet {
                val header = createRefFor("logoHeader")
                val loader = createRefFor("logoLoader")

                constrain(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, 17.dp)
                }

                constrain(loader) {
                    top.linkTo(header.bottom, 5.dp)
                    start.linkTo(parent.start, 17.dp)
                }
            },
            modifier = Modifier.layoutId("companyLogoInput")
        ) {
            Text(
                text = "Logo",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.layoutId("logoHeader")
            )
            ImageLoader(
                imageBytes = company.logoBytes,
                modifier = Modifier.layoutId("logoLoader")
                    .border(2.dp, Colors.accent.copy(alpha = 0.3f))
            ) {
                onCompanyChange(company.copy(logoBytes = it))
            }
        }
    }
}

@Preview
@Composable
fun CompanyFormPreview() {
    BonusTheme {
        val company = remember {
            mutableStateOf(
                Company(
                    name = "Example company",
                    description = "Just for test",
                    discount = 1.toBigInteger(),
                    ownerId = 1.toBigInteger(),
                    rewardImagesIds = emptyList(),
                    bonuses = emptyMap(),
                    logoBytes = ByteArray(0)
                )
            )
        }

        CompanyForm(company.value) { company.value = it }
    }
}