package es.bonus.android.features

import es.bonus.android.data.*
import java.math.BigInteger


sealed class RewardImage {
    // warning: do not pop up similar properties!
    data class Discount(
        val id: RewardImageId? = null,
        val description: String,
        val price: BigInteger,
        val amount: OwnedAsset.Discount,
        val ofCompanyId: CompanyId
    ) : RewardImage()

    data class Gift(
        val id: RewardImageId? = null,
        val description: String,
        val price: BigInteger,
        val ofCompanyId: CompanyId
    ) : RewardImage()
}

sealed class Reward {
    // warning: do not pop up similar properties!
    data class Custom(
        val id: RewardId,
        val imageId: RewardImageId,
        val ownerId: UserId,
        val used: Boolean = false
    ) : Reward()

    data class Common(
        val id: RewardId,
        val ownerId: UserId,
        val ofCompanyId: CompanyId,
        val amount: OwnedAsset.Discount.Currency,
        val used: Boolean = false
    ) : Reward()
}
