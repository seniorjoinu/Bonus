package es.bonus.android.features

import android.content.Context
import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.state
import es.bonus.android.R
import es.bonus.android.getResourceBytes
import es.bonus.android.state
import java.math.BigInteger

data class Company(
    val id: BigInteger = BigInteger.ZERO,
    val name: String = "",
    val logoBytes: ByteArray = ByteArray(0),
    val description: String = "",
    val discount: BigInteger = BigInteger.ZERO
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
fun createCompanyStore() = state { CompanyState() }

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
                description = "the world's largest restaurant chain by revenue, serving over 69 million customers daily in over 100 countries across 37,855 outlets as of 2018"
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
