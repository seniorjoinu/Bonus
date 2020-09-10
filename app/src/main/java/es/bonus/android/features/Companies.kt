package es.bonus.android.features

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import es.bonus.android.data.CompanyId
import es.bonus.android.data.Result
import es.bonus.android.data.RewardImageId
import es.bonus.android.data.UserId
import es.bonus.android.state
import java.math.BigInteger


data class Company(
    val id: CompanyId? = null,
    val name: String,
    val logoBytes: ByteArray,
    val description: String,
    // TODO: discount should be decimal
    val discount: BigInteger,
    val ownerId: UserId,
    val rewardImagesIds: List<RewardImageId>,
    val bonuses: Map<UserId, BigInteger>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Company

        if (id != other.id) return false
        if (name != other.name) return false
        if (!logoBytes.contentEquals(other.logoBytes)) return false
        if (description != other.description) return false
        if (discount != other.discount) return false
        if (ownerId != other.ownerId) return false
        if (rewardImagesIds != other.rewardImagesIds) return false
        if (bonuses != other.bonuses) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + logoBytes.contentHashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + discount.hashCode()
        result = 31 * result + ownerId.hashCode()
        result = 31 * result + rewardImagesIds.hashCode()
        result = 31 * result + bonuses.hashCode()
        return result
    }
}

data class CompanyState(
    val companies: Map<CompanyId, Company> = emptyMap(),
    val currentCompany: Company? = null,
    val fetching: Boolean = false,
    val error: Throwable? = null
)

typealias CompanyStore = MutableState<CompanyState>

suspend fun CompanyStore.fetchCompanies(getCompanies: suspend () -> Result<List<Company>, String>) {
    value = state.copy(fetching = true)

    value = try {
        val companies = getCompanies().unwrap()
        state.copy(companies = companies.associateBy { it.id!! }, error = null, fetching = false)
    } catch (e: Throwable) {
        state.copy(error = e, fetching = false)
    }
}

fun CompanyStore.setCurrentCompany(company: Company) {
    value = state.copy(currentCompany = company)
}

@Composable
fun createCompanyStore() = remember {
    mutableStateOf(
        CompanyState()
    )
}