package es.bonus.android.components

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.clip
import androidx.ui.core.tag
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.drawBorder
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.ImageAsset
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.outlined.Edit
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import es.bonus.android.R
import es.bonus.android.ui.BonusTheme
import es.bonus.android.ui.Colors

@Composable
fun Avatar(img: ImageAsset, nickName: String, mod: Modifier = Modifier) {
    val roundAvatarShape = RoundedCornerShape(img.height.dp)

    val clipAndBorderModifier = Modifier.clip(roundAvatarShape) + Modifier.drawBorder(2.dp, Colors.accent, roundAvatarShape)
    val sizeModifier = Modifier.width(100.dp) + Modifier.height(100.dp)
    val imgModifier = sizeModifier + clipAndBorderModifier

    Column(modifier = mod, horizontalGravity = Alignment.CenterHorizontally) {
        Image(asset = img, modifier = imgModifier)
        Row(
            modifier = Modifier.tag("nickName").padding(top = 10.dp),
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