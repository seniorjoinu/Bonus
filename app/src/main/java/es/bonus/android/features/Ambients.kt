package es.bonus.android.features

import androidx.compose.Ambient
import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.ambientOf

object Ambients {
    val RoutingStore = ambientOf<RoutingStore> { error("No routing store found") }
    val UserStore = ambientOf<UserStore> { error("No user store found") }
    val EventStore = ambientOf<EventStore> { error("No event store found") }
}

@Composable
val <T : Any> Ambient<MutableState<T>>.state: T
    get() = current.state

val <T : Any> MutableState<T>.state: T
    get() = value