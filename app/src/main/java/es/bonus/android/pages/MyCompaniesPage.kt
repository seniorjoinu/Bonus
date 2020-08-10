package es.bonus.android.pages

import androidx.compose.Composable
import androidx.compose.Providers
import androidx.ui.core.Modifier
import androidx.ui.foundation.clickable
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxSize
import androidx.ui.tooling.preview.Preview
import es.bonus.android.Ambients
import es.bonus.android.components.CompanyListItemView
import es.bonus.android.features.*
import es.bonus.android.state
import es.bonus.android.ui.BonusTheme

@Composable
fun MyCompaniesPage() {
    val userStore = Ambients.UserStore.current
    val companyStore = Ambients.CompanyStore.current
    val routingStore = Ambients.RoutingStore.current

    val userCompanies = userStore.state.companyIds
        .mapNotNull { companyStore.state.companies[it] }

    Column(modifier = Modifier.fillMaxSize()) {
        for (company in userCompanies) {
            CompanyListItemView(
                company = company,
                mod = Modifier.clickable {
                    companyStore.setCurrentCompany(company)
                    routingStore.goTo(AppRoute.Profile.Company(company.name))
                }
            )
        }
    }
}

@Preview
@Composable
fun MyCompaniesPagePreview() {
    BonusTheme {
        val userStore = createUserStore()
        val companyStore = createCompanyStore()

        Providers(
            Ambients.UserStore provides userStore,
            Ambients.CompanyStore provides companyStore
        ) {
            MyCompaniesPage()
        }
    }
}