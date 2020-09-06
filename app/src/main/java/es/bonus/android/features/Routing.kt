package es.bonus.android.features

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ContextAmbient
import es.bonus.android.Ambients
import es.bonus.android.MainActivity
import es.bonus.android.pages.*
import es.bonus.android.state

data class RoutingState(private val backStack: List<Route> = emptyList()) {
    constructor(vararg routes: Route) : this(routes.toMutableList())

    val currentRoute: Route?
        get() {
            if (backStack.isEmpty()) return null

            return backStack.last()
        }

    fun push(route: Route): RoutingState {
        return this.copy(backStack = this.backStack + route)
    }

    fun pop(): RoutingState {
        if (backStack.isNotEmpty())
            return this.copy(backStack = backStack.dropLast(1))

        return this
    }
}

interface Route

typealias RoutingStore = MutableState<RoutingState>

fun RoutingStore.goTo(route: Route) {
    println("Going to route $route")
    value = state.push(route)
}

fun RoutingStore.goBack() {
    value = state.pop()
}

@Composable
fun createRoutingStore(defaultRoute: Route = AppRoute.Profile.Index) = remember {
    mutableStateOf(
        RoutingState(defaultRoute)
    )
}

sealed class AppRoute {
    abstract val headerText: String

    sealed class Profile : AppRoute() {
        object Index : Profile(), Route {
            override val headerText: String = "Profile"
        }

        object MyIdentifier : Profile(), Route {
            override val headerText: String = "My identifier"
        }

        object MyCompanies : Profile(), Route {
            override val headerText: String = "My companies"
        }

        class Company(name: String) : Profile(), Route {
            override val headerText: String = name
        }
    }

    sealed class MyBonuses : AppRoute() {
        object Index : MyBonuses(), Route {
            override val headerText: String = "My bonuses"
        }
    }

    sealed class MyRewards : AppRoute() {
        object Index : MyRewards(), Route {
            override val headerText: String = "My rewards"
        }
    }

    sealed class BonusExchange : AppRoute() {
        object Index : BonusExchange(), Route {
            override val headerText: String = "Bonus exchange"
        }
    }
}

@Composable
fun RenderRoutes() {
    when (Ambients.RoutingStore.state.currentRoute) {
        is AppRoute.Profile.Index -> ProfilePage()
        is AppRoute.Profile.MyIdentifier -> MyIdentifierPage()
        is AppRoute.Profile.MyCompanies -> MyCompaniesPage()
        is AppRoute.Profile.Company -> CompanyPage()

        is AppRoute.MyBonuses.Index -> MyBonusesPage()
        is AppRoute.MyRewards.Index -> MyRewardsPage()
        is AppRoute.BonusExchange.Index -> BonusExchangePage()

        null -> (ContextAmbient.current as MainActivity).onBackPressed()
    }
}