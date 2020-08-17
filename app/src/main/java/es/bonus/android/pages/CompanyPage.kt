package es.bonus.android.pages

import androidx.compose.Composable
import androidx.compose.Providers
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.tag
import androidx.ui.foundation.Text
import androidx.ui.layout.ConstraintLayout
import androidx.ui.layout.ConstraintSet2
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.padding
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
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
        ConstraintSet2 {
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
            modifier = Modifier.tag("settingsHeader").padding(horizontal = GLOBAL_HOR_PADDING.dp)
        )

        CompanyForm(
            company = companiesStore.state.currentCompany,
            modifier = Modifier.tag("companyForm").padding(horizontal = 15.dp)
        ) {
            companiesStore.setCurrentCompany(company = it)
        }

        val data = eventStore.state.events
        EventTable(events = data, ofEntity = EventEntity.COMPANY, mod = Modifier.tag("table"))
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