package es.bonus.android.features

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import es.bonus.android.data.CompanyId
import es.bonus.android.data.RewardId
import es.bonus.android.data.UserId
import es.bonus.android.state
import java.math.BigInteger


data class User(
    val id: UserId? = null,
    val avatarBytes: ByteArray,
    val username: String,
    val ownedCompaniesIds: List<CompanyId>,
    val bonuses: Map<CompanyId, BigInteger>,
    val rewards: Map<CompanyId, List<RewardId>>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (!avatarBytes.contentEquals(other.avatarBytes)) return false
        if (username != other.username) return false
        if (ownedCompaniesIds != other.ownedCompaniesIds) return false
        if (bonuses != other.bonuses) return false
        if (rewards != other.rewards) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + avatarBytes.contentHashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + ownedCompaniesIds.hashCode()
        result = 31 * result + bonuses.hashCode()
        result = 31 * result + rewards.hashCode()
        return result
    }

}

data class UserState(
    val currentUser: User? = null
)

typealias UserStore = MutableState<UserState>

fun UserStore.setCurrentUser(user: User) {
    value = state.copy(currentUser = user)
}

@Composable
fun createUserStore() = remember {
    mutableStateOf(
        UserState()
    )
}