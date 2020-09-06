package es.bonus.android.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageAsset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
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
        ConstraintSet {
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
        modifier = Modifier.width(imgSize.dp).height(imgSize.dp) then modifier
    ) {
        if (imgBlurredBitmap != null) {
            Image(
                asset = imgBlurredBitmap,
                modifier = Modifier.layoutId("image").width(imgSize.dp).height(imgSize.dp),
                contentScale = ContentScale.Fit
            )
        }

        Icon(
            modifier = Modifier.layoutId("icon"),
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