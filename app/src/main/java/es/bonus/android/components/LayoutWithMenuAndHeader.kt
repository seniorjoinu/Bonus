package es.bonus.android.components

import androidx.compose.foundation.Box
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import es.bonus.android.Ambients
import es.bonus.android.GLOBAL_HOR_PADDING
import es.bonus.android.features.AppRoute
import es.bonus.android.features.createRoutingStore
import es.bonus.android.state
import es.bonus.android.ui.BonusTheme
import es.bonus.android.ui.Colors

@Composable
fun LayoutWithMenuAndHeader(children: @Composable () -> Unit) {
    ConstraintLayout(
        ConstraintSet {
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
        }, modifier = Modifier.fillMaxSize().background(color = Colors.darkBackground)
    ) {
        val offset = remember { mutableStateOf(0f) }

        val headerModifier = Modifier.layoutId("TopHeader")
            .padding(horizontal = GLOBAL_HOR_PADDING.dp)

        val menuModifier = Modifier.layoutId("BottomMenu")
        val contentModifier = Modifier.layoutId("Content")

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