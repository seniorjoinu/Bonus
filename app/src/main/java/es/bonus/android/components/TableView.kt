package es.bonus.android.components

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.tag
import androidx.ui.foundation.*
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.text.font.FontFamily
import androidx.ui.text.font.FontWeight
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import es.bonus.android.GLOBAL_HOR_PADDING
import es.bonus.android.data.UserEventType
import es.bonus.android.features.getRandomEvents
import es.bonus.android.prettyTimestamp
import es.bonus.android.ui.BonusTheme
import es.bonus.android.ui.Colors

typealias RowRenderer<T1, T2> = @Composable() RowScope.(T1, T2, Color) -> Unit

sealed class TableData {
    abstract var shouldDrawTag: Boolean

    data class TwoColumn<T1 : Any, T2 : Any>(
        val left: T1,
        val right: T2,
        override var shouldDrawTag: Boolean
    ) : TableData()
}

@Composable
fun <T1 : Any, T2 : Any> TableView(
    header: String,
    entries: Collection<TableData.TwoColumn<T1, T2>>,
    rowHorizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    rowVerticalGravity: Alignment.Vertical = Alignment.CenterVertically,
    rowHeight: Int = 30,
    tag: String = "",
    mod: Modifier = Modifier,
    rowRenderer: RowRenderer<T1, T2>
) {
    ConstraintLayout(
        ConstraintSet2 {
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
        Modifier.fillMaxWidth() + mod
    ) {
        Text(
            text = header,
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(horizontal = GLOBAL_HOR_PADDING.dp) + Modifier.tag("header")
        )

        Box(modifier = Modifier.tag("table")) {
            entries.forEachIndexed { index, entry ->
                val bgColor = if (index % 2 == 0) Colors.white1 else Colors.darkBackground
                val textColor = if (index % 2 == 0) Colors.darkBackground else Colors.white1

                Row(modifier = Modifier.fillMaxWidth().height(rowHeight.dp)) {
                    val tagWidth = 11
                    val shouldDrawTag = entry.shouldDrawTag

                    if (shouldDrawTag) {
                        Column(
                            modifier = Modifier.fillMaxHeight()
                                .drawBackground(Colors.accent)
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
                        modifier = Modifier.drawBackground(bgColor)
                            .fillMaxWidth()
                            .height(rowHeight.dp)
                            .padding(start = padding.dp, end = GLOBAL_HOR_PADDING.dp),
                        horizontalArrangement = rowHorizontalArrangement,
                        verticalGravity = rowVerticalGravity
                    ) {
                        rowRenderer(entry.left, entry.right, textColor)
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
        val entries = data.map { TableData.TwoColumn(it, it.timestamp, false) }

        TableView(
            header = "Recent history",
            entries = entries
        ) { left, right, contrastColor ->
            val sign = when (left.type) {
                UserEventType.RECEIVE -> "+ "
                UserEventType.USE -> "- "
            }

            Row(verticalGravity = Alignment.CenterVertically) {
                Text(text = sign, style = MaterialTheme.typography.body1, color = contrastColor)
                OwnedAssetView(
                    ownedAsset = left.ownedAsset,
                    textColor = contrastColor
                )
            }
            Text(
                text = " " + prettyTimestamp(right.toLong()),
                style = MaterialTheme.typography.body1,
                fontSize = 14.sp,
                color = Colors.gray1
            )
        }
    }
}