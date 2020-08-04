package es.bonus.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.Providers
import androidx.ui.core.setContent
import androidx.ui.tooling.preview.Preview
import es.bonus.android.components.LayoutWithMenuAndHeader
import es.bonus.android.features.*
import es.bonus.android.ui.BonusTheme

class MainActivity : AppCompatActivity() {
    lateinit var router: RoutingStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BonusTheme {
                router = createRoutingStore()
                val userStore = createUserStore()
                val eventStore = createEventStore()
                val companyStore = createCompanyStore()

                eventStore.fetch()
                companyStore.fetch()

                Providers(
                    Ambients.RoutingStore provides router,
                    Ambients.UserStore provides userStore,
                    Ambients.EventStore provides eventStore,
                    Ambients.CompanyStore provides companyStore
                ) {
                    LayoutWithMenuAndHeader {
                        RenderRoutes()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        router.goBack()

        if (router.state.currentRoute == null) {
            super.onBackPressed()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BonusTheme {
        val router = createRoutingStore()

        Providers(Ambients.RoutingStore provides router) {
            LayoutWithMenuAndHeader {
                RenderRoutes()
            }
        }
    }
}

