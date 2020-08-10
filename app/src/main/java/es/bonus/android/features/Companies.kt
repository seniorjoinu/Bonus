package es.bonus.android.features

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.state
import androidx.ui.core.ContextAmbient
import es.bonus.android.R
import es.bonus.android.getResourceBytes
import es.bonus.android.state
import java.math.BigInteger

typealias CurrencyPerBonus = BigInteger

data class Company(
    val id: BigInteger = BigInteger.ZERO,
    val name: String = "",
    val logoBytes: ByteArray = ByteArray(0),
    val description: String = "",
    val discount: CurrencyPerBonus = BigInteger.ZERO
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

@Composable // remove composable
fun CompanyStore.fetch() {
    value = state.copy(fetching = true)

    // uncomment
    //try {
    val companies = getCompanies().associateBy { it.id }
    value = state.copy(companies = companies, error = null)
    //} catch (e: Throwable) {
    //    value = state.copy(error = e)
    //} finally {
    value = state.copy(fetching = false)
    //}
}

fun CompanyStore.setCurrentCompany(company: Company) {
    value = state.copy(currentCompany = company)
}

@Composable
fun getCompanies(): List<Company> {
    val context = ContextAmbient.current
    val mcDoodlesLogo = context.getResourceBytes(R.raw.mc_doodles_logo)
    val beautifulCompLogo = context.getResourceBytes(R.raw.beautiful_company_logo)
    val vapeShopLogo = context.getResourceBytes(R.raw.vapeshop_logo)

    return listOf(
        Company(
            id = 1.toBigInteger(),
            name = "Beautiful Company",
            logoBytes = beautifulCompLogo,
            description = "multi-level marketing company in beauty, house- hold, and personal care categories"
        ),
        Company(
            id = 2.toBigInteger(),
            name = "VapeShop",
            logoBytes = vapeShopLogo,
            description = "around a third of all sales of e-cigarette products take place in vape shops"
        ),
        Company(
            id = 3.toBigInteger(),
            name = "McDoodles",
            logoBytes = mcDoodlesLogo,
            description = "the world's largest restaurant chain by revenue, serving over 69 million customers daily in over 100 countries across 37,855 outlets as of 2018"
        )
    )
}

@Composable
fun createCompanyStore() = state { CompanyState() }