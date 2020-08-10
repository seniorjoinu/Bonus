package es.bonus.android.pages

import androidx.compose.Composable
import androidx.compose.Providers
import androidx.ui.core.Modifier
import androidx.ui.core.tag
import androidx.ui.foundation.Text
import androidx.ui.layout.ConstraintLayout
import androidx.ui.layout.ConstraintSet2
import androidx.ui.layout.fillMaxSize
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import es.bonus.android.Ambients
import es.bonus.android.components.CompanyForm
import es.bonus.android.features.createCompanyStore
import es.bonus.android.features.setCurrentCompany
import es.bonus.android.state
import es.bonus.android.ui.BonusTheme

@Composable
fun CompanyPage() {
    ConstraintLayout(
        ConstraintSet2 {
            val settingsHeader = createRefFor("settingsHeader")
            val companyForm = createRefFor("companyForm")

            constrain(settingsHeader) {
                top.linkTo(parent.top, 7.dp)
            }

            constrain(companyForm) {
                top.linkTo(settingsHeader.bottom, 20.dp)
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.h3,
            modifier = Modifier.tag("settingsHeader")
        )

        val companiesState = Ambients.CompanyStore.current

        CompanyForm(
            company = companiesState.state.currentCompany,
            modifier = Modifier.tag("companyForm")
        ) {
            companiesState.setCurrentCompany(company = it)
        }
    }
}

@Composable
@Preview
fun CompanyPagePreview() {
    BonusTheme {
        val companiesStore = createCompanyStore()

        Providers(Ambients.CompanyStore provides companiesStore) {
            CompanyPage()
        }
    }
}