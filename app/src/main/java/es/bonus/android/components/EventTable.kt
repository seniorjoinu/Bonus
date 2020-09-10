package es.bonus.android.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.ui.tooling.preview.Preview
import es.bonus.android.data.*
import es.bonus.android.features.*
import es.bonus.android.ui.BonusTheme

@Composable
fun EventTable(
    events: List<Event>,
    users: Map<UserId, User>,
    companies: Map<CompanyId, Company>,
    rewards: Map<RewardId, Reward>,
    rewardImages: Map<RewardImageId, RewardImage>,
    exchangeOffers: Map<ExchangeOfferId, ExchangeOffer>,
    ofEntity: EventEntity,
    mod: Modifier = Modifier
) {
    val entries = events.map { TableData.Simple(it) }

    TableView(
        header = "Recent history",
        entries = entries,
        mod = mod
    ) { event, contrastColor ->
        val prefixText: String
        val suffixText: String
        val bonuses: OwnedAsset.Bonus

        when (ofEntity) {
            EventEntity.COMPANY -> when (event) {
                is Event.BonusesIssued -> {
                    prefixText = "issued "
                    suffixText = " to ${users[event.toUserId]!!.username}"
                    bonuses = event.amount.bonuses.toOwnedAsset()
                }
                is Event.RewardPurchased -> {
                    val reward = rewards[event.rewardId]!!
                    bonuses = event.price.bonuses.toOwnedAsset()

                    when (reward) {
                        is Reward.Custom -> {
                            prefixText = "${users[reward.ownerId]} spent "
                            suffixText = when (rewardImages[reward.imageId]!!) {
                                is RewardImage.Discount -> " for a discount"
                                is RewardImage.Gift -> " for a gift"
                            }
                        }
                        is Reward.Common -> {
                            prefixText = "${users[reward.ownerId]} spent "
                            suffixText = " for a discount"
                        }
                    }
                }
                is Event.RewardAccepted -> {
                    val reward = rewards[event.rewardId]!!

                    when (reward) {

                    }
                    bonuses = reward.bonuses.toOwnedAsset()

                    prefixText = ""
                    suffixText = when (event.ownedAsset) {
                        is OwnedAsset.Discount,
                        is OwnedAsset.GiftCoupon -> " purchased by ${event.user.username}"
                        else -> ""
                    }
                }
                EventType.CUSTOMER_REWARD_USED -> {
                    prefixText = ""
                    suffixText = when (event.ownedAsset) {
                        is OwnedAsset.Discount,
                        is OwnedAsset.GiftCoupon -> " used by ${event.user.username}"
                        else -> ""
                    }
                }
            }
            EventEntity.USER -> when (event.type) {
                EventType.COMPANY_BONUSES_ISSUED -> {
                    prefixText = "+ "
                    suffixText = " of ${event.company.name}"
                }
                EventType.COMPANY_BONUSES_WITHDRAWN -> {
                    prefixText = "- "
                    suffixText = " of ${event.company.name}"
                }
                EventType.CUSTOMER_REWARD_PURCHASED -> {
                    prefixText = "+ "
                    suffixText = " in ${event.company.name}"
                }
                EventType.CUSTOMER_REWARD_USED -> {
                    prefixText = "- "
                    suffixText = " in ${event.company.name}"
                }
            }
        }

        Row(verticalGravity = Alignment.CenterVertically) {
            Text(text = prefixText, style = MaterialTheme.typography.body1, color = contrastColor)
            OwnedAssetView(ownedAsset = event.ownedAsset, textColor = contrastColor)
            Text(text = suffixText, style = MaterialTheme.typography.body1, color = contrastColor)
        }
        Text(
            text = " " + prettyTimestamp(event.timestamp.toLong()),
            style = MaterialTheme.typography.body1,
            fontSize = 14.sp,
            color = Colors.gray1
        )
    }
}

@Composable
@Preview
fun EventTablePreview() {
    val context = ContextAmbient.current
    Companies.init(context)
    Users.init(context)

    BonusTheme {
        Column {
            EventTable(getRandomEvents(5), EventEntity.COMPANY)
            EventTable(getRandomEvents(5), EventEntity.USER)
        }
    }
}