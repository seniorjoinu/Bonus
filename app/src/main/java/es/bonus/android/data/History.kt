package es.bonus.android.data

import es.bonus.android.features.Company
import java.math.BigInteger


enum class UserEventType {
    RECEIVE, USE;

    companion object {
        fun random() = values().random()
    }
}

sealed class OwnedAsset {
    abstract val ofCompany: Company

    data class Bonus(
        val valueAsset: ValueAsset.Bonus,
        override val ofCompany: Company
    ) : OwnedAsset()

    sealed class Discount: OwnedAsset() {
        data class Currency(
            val valueAsset: ValueAsset.Currency,
            override val ofCompany: Company
        ): Discount()

        data class Percent(
            val value: Byte,
            override val ofCompany: Company
        ): Discount()
    }
}

data class UserEvent(
    val type: UserEventType,
    val ownedAsset: OwnedAsset,
    val timestamp: BigInteger
)
