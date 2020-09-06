package es.bonus.android.components

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import es.bonus.android.R
import es.bonus.android.ui.BonusTheme
import es.bonus.android.ui.Colors

@Composable
fun Avatar(img: ImageAsset, nickName: String, mod: Modifier = Modifier) {
    val roundAvatarShape = RoundedCornerShape(50)

    val clipAndBorderModifier = Modifier
        .clip(roundAvatarShape)
        .border(2.dp, color = Colors.accent, roundAvatarShape)
    val sizeModifier = Modifier.width(100.dp).height(100.dp)
    val imgModifier = sizeModifier then clipAndBorderModifier

    Column(modifier = mod, horizontalGravity = Alignment.CenterHorizontally) {
        Image(asset = img, modifier = imgModifier)
        Row(
            modifier = Modifier.padding(top = 10.dp).layoutId("nickName"),
            verticalGravity = Alignment.CenterVertically
        ) {
            Text("$nickName ", style = MaterialTheme.typography.body1)
            Icon(
                asset = Icons.Outlined.Edit,
                modifier = Modifier.size(16.dp),
                tint = Colors.accent
            )
        }
    }
}

@Preview
@Composable
fun AvatarPreview() {
    BonusTheme {
        Avatar(img = imageResource(id = R.drawable.avatar), nickName = "Alexander Vtyurin")
    }
}