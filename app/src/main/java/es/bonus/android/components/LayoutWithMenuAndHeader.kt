package es.bonus.android.components

import androidx.compose.Composable
import androidx.compose.Providers
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.core.tag
import androidx.ui.foundation.Box
import androidx.ui.foundation.drawBackground
import androidx.ui.foundation.gestures.DragDirection
import androidx.ui.foundation.gestures.ScrollableState
import androidx.ui.foundation.gestures.scrollable
import androidx.ui.layout.*
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import es.bonus.android.Ambients
import es.bonus.android.GLOBAL_HOR_PADDING
import es.bonus.android.features.AppRoute
import es.bonus.android.features.createRoutingStore
import es.bonus.android.state
import es.bonus.android.ui.BonusTheme
import es.bonus.android.ui.Colors

@Composable
fun LayoutWithMenuAndHeader(children: @Composable() () -> Unit) {
    ConstraintLayout(ConstraintSet2 {
        val topHeader = createRefFor("TopHeader")
        val content = createRefFor("Content")
        val bottomMenu = createRefFor("BottomMenu")

        val headerHeight = 50
        val menuHeight = 60

        constrain(topHeader) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)

            width = Dimension.fillToConstraints
            height = Dimension.value(headerHeight.dp)
        }

        constrain(content) {
            top.linkTo(topHeader.bottom)
            bottom.linkTo(bottomMenu.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)

            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }

        constrain(bottomMenu) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)

            width = Dimension.fillToConstraints
            height = Dimension.value(menuHeight.dp)
        }
    }, modifier = Modifier.fillMaxSize() + Modifier.drawBackground(Colors.darkBackground)) {
        val offset = state { 0f }

        val headerModifier = Modifier.tag("TopHeader")
            .padding(horizontal = GLOBAL_HOR_PADDING.dp)

        val menuModifier = Modifier.tag("BottomMenu")

        val contentModifier = Modifier.tag("Content")
            .scrollable(DragDirection.Vertical, ScrollableState { delta ->
                offset.value = offset.value + delta
                delta
            })

        val routerStore = Ambients.RoutingStore.current
        val header = (routerStore.state.currentRoute as AppRoute?)?.headerText ?: ""

        TopHeader(header, modifier = headerModifier)
        Box(modifier = contentModifier) {
            children()
        }
        BottomMenu(routerStore, modifier = menuModifier)
    }
}

@Preview(showBackground = true)
@Composable
fun LayoutWithMenuAndHeaderPreview() {
    BonusTheme {
        val router = createRoutingStore()

        Providers(Ambients.RoutingStore provides router) {
            LayoutWithMenuAndHeader {

            }
        }
    }
}