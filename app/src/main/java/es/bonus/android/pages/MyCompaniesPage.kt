package es.bonus.android.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Modifier
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

    val userCompanies = userStore.state.currentUser.ownedCompaniesIds
        .mapNotNull { companyStore.state.companies[it] }

    Column(modifier = Modifier.fillMaxSize()) {
        for (company in userCompanies) {
            CompanyListItemView(
                company = company,
                mod = Modifier.clickable {
                    routingStore.goTo(AppRoute.Profile.Company(company.name))
                    companyStore.setCurrentCompany(company)
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