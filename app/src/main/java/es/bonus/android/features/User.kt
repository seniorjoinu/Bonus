package es.bonus.android.features

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import es.bonus.android.R
import es.bonus.android.getResourceBytes
import es.bonus.android.state
import java.math.BigInteger


data class User(
    val avatarBytes: ByteArray = ByteArray(0),
    val nickName: String = "Anonymous",
    val id: BigInteger = BigInteger.ONE,
    val companyIds: List<BigInteger> = listOf(1.toBigInteger(), 2.toBigInteger(), 3.toBigInteger())
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (!avatarBytes.contentEquals(other.avatarBytes)) return false
        if (nickName != other.nickName) return false
        if (id != other.id) return false
        if (companyIds != other.companyIds) return false

        return true
    }

    override fun hashCode(): Int {
        var result = avatarBytes.contentHashCode()
        result = 31 * result + nickName.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + companyIds.hashCode()
        return result
    }
}

data class UserState(
    val currentUser: User = User()
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
                nickName = "Alexander Vtyurin",
                avatarBytes = context.getResourceBytes(R.raw.avatar),
                companyIds = listOf(BigInteger.ONE)
            )

            nikitaSamsonov = User(
                id = BigInteger("2"),
                nickName = "Nikita Samsonov",
                avatarBytes = context.getResourceBytes(R.raw.avatar),
                companyIds = listOf(BigInteger("2"), BigInteger("3"))
            )
        }

        fun random() = when (values().random()) {
            AlexanderVtyurin -> alexanderVtyurin
            NikitaSamsonov -> nikitaSamsonov
        }
    }
}