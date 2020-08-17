package es.bonus.android.data

import java.math.BigInteger

sealed class ValueAsset {
    abstract val value: BigInteger

    data class Bonus(override val value: BigInteger) : ValueAsset() {
        override fun toString(): String {
            return value.toString()
        }

        fun toOwnedAsset() = OwnedAsset.Bonus(this)
    }

    sealed class Currency : ValueAsset() {
        abstract val pointCount: Int

        override fun toString(): String {
            val pointExp = BigInteger.TEN.pow(pointCount)
            val beforePoint = value / pointExp
            val afterPoint = value % pointExp

            return "${beforePoint}.${afterPoint.toString().padStart(pointCount, '0')}"
        }

        data class Ruble(override val value: BigInteger) : Currency() {
            override val pointCount: Int = 2

            override fun toString(): String {
                return super.toString()
            }

            fun toOwnedAsset() = OwnedAsset.Discount.Currency(this)
        }
    }
}

val Int.bonuses: ValueAsset.Bonus
    get() = this.toBigInteger().bonuses

val BigInteger.bonuses: ValueAsset.Bonus
    get() = ValueAsset.Bonus(this)

val Int.rub: ValueAsset.Currency.Ruble
    get() = this.toBigInteger().rub

val BigInteger.rub: ValueAsset.Currency.Ruble
    get() = ValueAsset.Currency.Ruble(this * BigInteger.TEN)


sealed class OwnedAsset {
    data class Bonus(val valueAsset: ValueAsset.Bonus) : OwnedAsset()

    sealed class Discount : OwnedAsset() {
        data class Currency(val valueAsset: ValueAsset.Currency) : Discount()

        data class Percent(val value: Byte) : Discount()
    }

    data class GiftCoupon(val id: BigInteger) : OwnedAsset()
}
