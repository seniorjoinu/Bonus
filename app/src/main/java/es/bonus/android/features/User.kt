package es.bonus.android.features

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import es.bonus.android.R
import es.bonus.android.data.CompanyId
import es.bonus.android.data.RewardId
import es.bonus.android.data.UserId
import es.bonus.android.getResourceBytes
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

        if (!avatarBytes.contentEquals(other.avatarBytes)) return false
        if (username != other.username) return false
        if (id != other.id) return false
        if (ownedCompaniesIds != other.ownedCompaniesIds) return false

        return true
    }

    override fun hashCode(): Int {
        var result = avatarBytes.contentHashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + ownedCompaniesIds.hashCode()
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

enum class Users {
    AlexanderVtyurin,
    NikitaSamsonov;

    companion object {
        lateinit var alexanderVtyurin: User
        lateinit var nikitaSamsonov: User

        fun init(context: Context) {
            alexanderVtyurin = User(
                id = BigInteger.ONE,
                username = "Alexander Vtyurin",
                avatarBytes = context.getResourceBytes(R.raw.avatar),
                ownedCompaniesIds = listOf(BigInteger.ONE)
            )

            nikitaSamsonov = User(
                id = BigInteger("2"),
                username = "Nikita Samsonov",
                avatarBytes = context.getResourceBytes(R.raw.avatar),
                ownedCompaniesIds = listOf(BigInteger("2"), BigInteger("3"))
            )
        }

        fun random() = when (values().random()) {
            AlexanderVtyurin -> alexanderVtyurin
            NikitaSamsonov -> nikitaSamsonov
        }
    }
}