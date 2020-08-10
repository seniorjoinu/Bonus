package es.bonus.android.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.Composable
import androidx.ui.core.ContentScale
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.tag
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Image
import androidx.ui.graphics.asImageAsset
import androidx.ui.layout.*
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import es.bonus.android.R
import es.bonus.android.getResourceBytes
import es.bonus.android.ui.BonusTheme
import es.bonus.android.ui.Colors


@Composable
fun ImageLoader(
    imageBytes: ByteArray,
    modifier: Modifier = Modifier,
    onImageChange: (ByteArray) -> Unit = {}
) {
    val imgBitmap = if (imageBytes.isNotEmpty())
        BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    else
        null

    val imgSize = 75

    val imgBlurredBitmap = if (imgBitmap != null)
        Bitmap.createScaledBitmap(imgBitmap, imgSize, imgSize, true).asImageAsset()
    else
        null

    ConstraintLayout(
        ConstraintSet2 {
            val image = createRefFor("image")
            val icon = createRefFor("icon")

            constrain(image) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)

                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }

            constrain(icon) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        },
        modifier = Modifier.width(imgSize.dp).height(imgSize.dp) + modifier
    ) {
        if (imgBlurredBitmap != null) {
            Image(
                asset = imgBlurredBitmap,
                modifier = Modifier.tag("image").width(imgSize.dp).height(imgSize.dp),
                contentScale = ContentScale.Fit
            )
        }

        Icon(
            modifier = Modifier.tag("icon"),
            asset = vectorResource(R.drawable.ic_baseline_save_alt_24),
            tint = Colors.white1
        )
    }
}

@Composable
@Preview
fun ImageLoaderPreview() {
    BonusTheme {
        val context = ContextAmbient.current
        val imgBytes = context.getResourceBytes(R.raw.vapeshop_logo)

        ImageLoader(imgBytes)
    }
}