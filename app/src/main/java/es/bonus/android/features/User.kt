package es.bonus.android.features

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.state
import androidx.ui.graphics.ImageAsset
import androidx.ui.res.imageResource
import es.bonus.android.R

data class UserState(
    val avatarImg: ImageAsset,
    val nickName: String = "Anonymous"
)

typealias UserStore = MutableState<UserState>

@Composable
fun createUserStore() = state { UserState(imageResource(id = R.drawable.anonymous_avatar)) }