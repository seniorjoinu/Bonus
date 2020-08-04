package es.bonus.android.features

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.state
import androidx.ui.core.ContextAmbient
import es.bonus.android.MainActivity
import es.bonus.android.pages.*

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
fun createRoutingStore(defaultRoute: Route = AppRoute.Profile.Index) = state {
    RoutingState(defaultRoute)
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

        is AppRoute.MyBonuses.Index -> MyBonusesPage()
        is AppRoute.MyRewards.Index -> MyRewardsPage()
        is AppRoute.BonusExchange.Index -> BonusExchangePage()

        null -> (ContextAmbient.current as MainActivity).onBackPressed()
    }
}