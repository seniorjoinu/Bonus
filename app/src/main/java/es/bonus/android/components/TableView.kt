package es.bonus.android.components

import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import es.bonus.android.GLOBAL_HOR_PADDING
import es.bonus.android.features.EventType
import es.bonus.android.features.getRandomEvents
import es.bonus.android.prettyTimestamp
import es.bonus.android.ui.BonusTheme
import es.bonus.android.ui.Colors
import kotlin.random.Random

typealias RowRenderer<T> = @Composable RowScope.(T, Color) -> Unit

sealed class TableData<T : Any> {
    abstract val data: T

    data class Simple<T : Any>(override val data: T) : TableData<T>()
    data class WithTag<T : Any>(override val data: T, val drawTag: Boolean) : TableData<T>()
}

@Composable
fun <T : Any> TableView(
    header: String,
    entries: Collection<TableData<T>>,
    rowHeight: Int = 30,
    tag: String = "",
    mod: Modifier = Modifier,
    rowRenderer: RowRenderer<T>
) {
    ConstraintLayout(
        ConstraintSet {
            val header = createRefFor("header")

            constrain(header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }

            val table = createRefFor("table")

            constrain(table) {
                top.linkTo(header.bottom, 10.dp)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        },
        Modifier.fillMaxWidth().then(mod)
    ) {
        Text(
            text = header,
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(horizontal = GLOBAL_HOR_PADDING.dp).layoutId("header")
        )

        Box(modifier = Modifier.layoutId("table")) {
            entries.forEachIndexed { index, entry ->
                val bgColor = if (index % 2 == 0) Colors.white1 else Colors.darkBackground
                val textColor = if (index % 2 == 0) Colors.darkBackground else Colors.white1

                Row(modifier = Modifier.fillMaxWidth().height(rowHeight.dp)) {
                    val tagWidth = 11
                    val shouldDrawTag = if (entry is TableData.WithTag) entry.drawTag else false

                    if (shouldDrawTag) {
                        Column(
                            modifier = Modifier.fillMaxHeight()
                                .background(color = Colors.accent)
                                .padding(bottom = 5.dp)
                                .width(tagWidth.dp),
                            horizontalGravity = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            for (char in tag) {
                                Text(
                                    modifier = Modifier.height(9.dp),
                                    text = char.toString(),
                                    color = Colors.white1,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.Light,
                                    fontSize = 9.sp
                                )
                            }
                        }
                    }

                    val padding = if (shouldDrawTag)
                        GLOBAL_HOR_PADDING - tagWidth
                    else
                        GLOBAL_HOR_PADDING

                    Row(
                        modifier = Modifier.background(color = bgColor)
                            .fillMaxWidth()
                            .height(rowHeight.dp)
                            .padding(start = padding.dp, end = GLOBAL_HOR_PADDING.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalGravity = Alignment.CenterVertically
                    ) {
                        rowRenderer(entry.data, textColor)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    BonusTheme {
        val data = getRandomEvents(4)
        val entries = data.map { TableData.WithTag(it, Random.nextBoolean()) }

        TableView(
            header = "Recent history",
            entries = entries
        ) { data, contrastColor ->
            val sign = when (data.type) {
                EventType.COMPANY_BONUSES_ISSUED, EventType.CUSTOMER_REWARD_PURCHASED -> "+ "
                EventType.COMPANY_BONUSES_WITHDRAWN, EventType.CUSTOMER_REWARD_USED -> "- "
            }

            Row(verticalGravity = Alignment.CenterVertically) {
                Text(text = sign, style = MaterialTheme.typography.body1, color = contrastColor)
                OwnedAssetView(
                    ownedAsset = data.ownedAsset,
                    textColor = contrastColor
                )
            }
            Text(
                text = " " + prettyTimestamp(data.timestamp.toLong()),
                style = MaterialTheme.typography.body1,
                fontSize = 14.sp,
                color = Colors.gray1
            )
        }
    }
}