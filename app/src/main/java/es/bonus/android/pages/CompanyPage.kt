package es.bonus.android.pages

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.ConstraintSet
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import es.bonus.android.Ambients
import es.bonus.android.GLOBAL_HOR_PADDING
import es.bonus.android.components.CompanyForm
import es.bonus.android.components.EventTable
import es.bonus.android.features.*
import es.bonus.android.state
import es.bonus.android.ui.BonusTheme

@Composable
fun CompanyPage() {
    ConstraintLayout(
        ConstraintSet {
            val settingsHeader = createRefFor("settingsHeader")
            val companyForm = createRefFor("companyForm")
            val table = createRefFor("table")

            constrain(settingsHeader) {
                top.linkTo(parent.top, 7.dp)
            }

            constrain(companyForm) {
                top.linkTo(settingsHeader.bottom, 20.dp)
            }

            constrain(table) {
                top.linkTo(companyForm.bottom, 20.dp)
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        val companiesStore = Ambients.CompanyStore.current
        val eventStore = Ambients.EventStore.current

        Text(
            text = "Settings",
            style = MaterialTheme.typography.h3,
            modifier = Modifier.layoutId("settingsHeader")
                .padding(horizontal = GLOBAL_HOR_PADDING.dp)
        )

        CompanyForm(
            company = companiesStore.state.currentCompany,
            modifier = Modifier.layoutId("companyForm").padding(horizontal = 15.dp)
        ) {
            companiesStore.setCurrentCompany(company = it)
        }

        val data = eventStore.state.events
        EventTable(events = data, ofEntity = EventEntity.COMPANY, mod = Modifier.layoutId("table"))
    }
}

@Composable
@Preview
fun CompanyPagePreview() {
    val context = ContextAmbient.current
    Companies.init(context)
    Users.init(context)

    BonusTheme {
        val companiesStore = createCompanyStore()
        val eventsStore = createEventStore()

        companiesStore.setCurrentCompany(Companies.random())
        eventsStore.fetchEvents()

        Providers(
            Ambients.CompanyStore provides companiesStore,
            Ambients.EventStore provides eventsStore
        ) {
            CompanyPage()
        }
    }
}