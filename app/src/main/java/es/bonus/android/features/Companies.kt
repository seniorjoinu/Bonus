package es.bonus.android.features

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import es.bonus.android.R
import es.bonus.android.data.CompanyId
import es.bonus.android.data.RewardImageId
import es.bonus.android.data.UserId
import es.bonus.android.getResourceBytes
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

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + logoBytes.contentHashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + discount.hashCode()
        return result
    }
}

data class CompanyState(
    val companies: Map<BigInteger, Company> = emptyMap(),
    val currentCompany: Company = Company(),
    val fetching: Boolean = false,
    val error: Throwable? = null
)

typealias CompanyStore = MutableState<CompanyState>

fun CompanyStore.fetchCompanies() {
    value = state.copy(fetching = true)

    value = try {
        val companies = Companies.all().associateBy { it.id }
        state.copy(companies = companies, error = null, fetching = false)
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

enum class Companies {
    McDoodles,
    VapeShop,
    BeautifulCompany;

    companion object {
        lateinit var mcDoodles: Company
        lateinit var vapeShop: Company
        lateinit var beatifulCompany: Company

        fun init(context: Context) {
            mcDoodles = Company(
                id = BigInteger.ONE,
                name = "McDoodles",
                logoBytes = context.getResourceBytes(R.raw.mc_doodles_logo),
                description = "the world's largest restaurant chain by revenue, serving over 69 million customers daily in over 100 countries across 37,855 outlets as of 2018",
            )

            vapeShop = Company(
                id = BigInteger("2"),
                name = "VapeShop",
                logoBytes = context.getResourceBytes(R.raw.vapeshop_logo),
                description = "around a third of all sales of e-cigarette products take place in vape shops"
            )

            beatifulCompany = Company(
                id = BigInteger("3"),
                name = "Beautiful Company",
                logoBytes = context.getResourceBytes(R.raw.beautiful_company_logo),
                description = "multi-level marketing company in beauty, house-hold, and personal care categories"
            )
        }

        fun random(): Company = when (values().random()) {
            McDoodles -> mcDoodles
            VapeShop -> vapeShop
            BeautifulCompany -> beatifulCompany
        }

        fun all(): List<Company> = listOf(mcDoodles, vapeShop, beatifulCompany)
    }
}
