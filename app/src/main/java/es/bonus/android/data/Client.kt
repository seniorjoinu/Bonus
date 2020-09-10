package es.bonus.android.data

import es.bonus.android.features.*
import java.math.BigInteger

typealias Id = BigInteger
typealias UserId = Id
typealias CompanyId = Id
typealias RewardImageId = Id
typealias RewardId = Id
typealias ExchangeOfferId = Id

sealed class Result<out S, out F> {
    data class Success<out S>(val value: S) : Result<S, Nothing>()
    data class Failure<out F>(val reason: F) : Result<Nothing, F>()

    fun unwrap(): S {
        return when (this) {
            is Success -> value
            is Failure -> error("Error during unwrapping of the result: $reason")
        }
    }
}

interface IClient {
    var currentUserId: UserId

    suspend fun login(username: String, password: String): Result<User, String>

    suspend fun registerUser(
        username: String,
        password: String,
        avatarBytes: ByteArray
    ): Result<User, String>

    suspend fun registerCompany(
        name: String,
        logoBytes: ByteArray,
        description: String,
        discount: BigInteger
    ): Result<CompanyId, String>

    suspend fun editCompany(
        id: CompanyId,
        name: String? = null,
        logoBytes: ByteArray? = null,
        description: String? = null,
        discount: BigInteger? = null
    ): Result<Unit, String>

    suspend fun createRewardImage(rewardImage: RewardImage): Result<RewardImageId, String>

    suspend fun removeRewardImage(rewardImageId: RewardImageId): Result<Unit, String>

    suspend fun issueBonuses(
        fromCompanyId: CompanyId,
        toUserId: UserId,
        amount: BigInteger
    ): Result<Unit, String>

    suspend fun acceptReward(rewardId: RewardId): Result<Unit, String>

    suspend fun transferBonuses(
        ofCompanyId: CompanyId,
        toUserId: UserId,
        amount: BigInteger
    ): Result<Unit, String>

    suspend fun purchaseCustomReward(rewardImageId: RewardImageId): Result<RewardId, String>

    suspend fun purchaseCommonReward(
        companyId: CompanyId,
        amount: BigInteger
    ): Result<RewardId, String>

    suspend fun useReward(rewardId: RewardId): Result<Unit, String>

    suspend fun placeExchangeOffer(
        sellsCompanyId: CompanyId,
        sellsAmount: BigInteger,
        buysCompanyId: CompanyId,
        buysAmount: BigInteger
    ): Result<ExchangeOfferId, String>

    suspend fun acceptExchangeOffer(offerId: ExchangeOfferId): Result<Unit, String>

    suspend fun removeExchangeOffer(offerId: ExchangeOfferId): Result<Unit, String>

    suspend fun getUsers(ids: List<UserId>): Result<List<User>, String>

    suspend fun getCompanies(ids: List<CompanyId>): Result<List<Company>, String>

    suspend fun getRewardImages(ids: List<RewardImageId>): Result<List<RewardImage>, String>

    suspend fun getRewards(
        ids: List<RewardId>,
        state: RewardState? = null
    ): Result<List<Reward>, String>

    suspend fun getExchangeOffers(
        ids: List<ExchangeOfferId>,
        state: ExchangeOfferState? = null
    ): Result<List<ExchangeOffer>, String>

    suspend fun getEvents(
        userId: UserId? = null,
        companyId: CompanyId? = null
    ): Result<List<Event>, String>
}