package es.bonus.android.components

import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.tag
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.drawBorder
import androidx.ui.input.KeyboardType
import androidx.ui.layout.*
import androidx.ui.material.FilledTextField
import androidx.ui.material.MaterialTheme
import androidx.ui.res.vectorResource
import androidx.ui.savedinstancestate.savedInstanceState
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import es.bonus.android.R
import es.bonus.android.data.Company
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
        ConstraintSet2 {
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
        modifier = Modifier.fillMaxWidth() + modifier
    ) {
        val type = MaterialTheme.typography

        var companyName by savedInstanceState { company.name }
        var textStyleName by savedInstanceState { if (companyName.isBlank()) type.h4 else type.h5 }
        FilledTextField(
            modifier = Modifier.tag("companyNameInput"),
            value = companyName,
            onValueChange = {
                companyName = it
            },
            label = {
                Text(text = "Company name", style = textStyleName)
            },
            onFocusChange = {
                textStyleName = if (it) type.h5 else {
                    onCompanyChange(company.copy(name = companyName))

                    if (companyName.isBlank()) type.h4 else type.h5
                }
            },
            inactiveColor = Colors.accent,
            backgroundColor = Colors.darkBackground
        )

        var companyDescription by savedInstanceState { company.description }
        var textStyleDescription by savedInstanceState {
            if (companyDescription.isBlank()) type.h4 else type.h5
        }
        FilledTextField(
            modifier = Modifier.tag("companyDescriptionInput"),
            value = companyDescription,
            onValueChange = {
                companyDescription = it
            },
            label = {
                Text(text = "Company description", style = textStyleDescription)
            },
            onFocusChange = {
                textStyleDescription = if (it) type.h5 else {
                    onCompanyChange(company.copy(description = companyDescription))

                    if (companyDescription.isBlank()) type.h4 else type.h5
                }
            },
            inactiveColor = Colors.accent,
            backgroundColor = Colors.darkBackground
        )

        var companyDiscount by savedInstanceState { company.discount }
        var textStyleDiscount by savedInstanceState {
            if (companyDiscount >= BigInteger.ZERO) type.h5 else type.h4
        }
        FilledTextField(
            modifier = Modifier.tag("companyDiscountInput"),
            value = if (companyDiscount >= BigInteger.ZERO) companyDiscount.toString() else "",
            onValueChange = {
                companyDiscount = if (it.isNotBlank()) it.toBigInteger() else BigInteger("-1")
            },
            label = {
                Row(verticalGravity = Alignment.CenterVertically) {
                    Text(text = "Discount ", style = textStyleDiscount)
                    Icon(
                        asset = vectorResource(id = R.drawable.ic_ruble_sign),
                        tint = Colors.accent,
                        modifier = Modifier.height(textStyleDiscount.fontSize.value.dp)
                    )
                    Text(text = " per 1 ", style = textStyleDiscount)
                    Icon(
                        asset = vectorResource(id = R.drawable.ic_bonuses_sign),
                        tint = Colors.accent,
                        modifier = Modifier.height(textStyleDiscount.fontSize.value.dp)
                    )
                }
            },
            onFocusChange = {
                textStyleDiscount = if (it) type.h5 else {
                    if (companyDiscount >= BigInteger.ZERO) type.h5 else type.h4
                }
                onCompanyChange(company.copy(discount = companyDiscount))
            },
            inactiveColor = Colors.accent,
            backgroundColor = Colors.darkBackground,
            keyboardType = KeyboardType.Number
        )

        ConstraintLayout(
            ConstraintSet2 {
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
            modifier = Modifier.tag("companyLogoInput")
        ) {
            Text(
                text = "Logo",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.tag("logoHeader")
            )
            ImageLoader(
                imageBytes = company.logoBytes,
                modifier = Modifier.tag("logoLoader")
                    .drawBorder(2.dp, Colors.accent.copy(alpha = 0.3f))
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
        var company by savedInstanceState { Company() }

        CompanyForm(company) { company = it }
    }
}