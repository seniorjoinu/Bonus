package es.bonus.android.features

import es.bonus.android.data.CompanyId
import es.bonus.android.data.ExchangeOfferId
import es.bonus.android.data.UserId
import java.math.BigInteger

enum class ExchangeOfferState {
    PLACED, REMOVED, ACCEPTED
}

data class ExchangeOffer(
    val id: ExchangeOfferId,
    val ownerId: UserId,
    val sellsCompanyId: CompanyId,
    val sellsAmount: BigInteger,
    val buysCompanyId: CompanyId,
    val buysAmount: BigInteger,
    val state: ExchangeOfferState
)