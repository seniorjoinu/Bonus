package es.bonus.android.components

import android.graphics.Bitmap
import androidx.compose.Composable
import androidx.ui.foundation.Image
import androidx.ui.graphics.asImageAsset
import androidx.ui.tooling.preview.Preview
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import es.bonus.android.ui.ColorsHex
import java.util.*


@Composable
fun QRCode(data: String, size: Int = 500) {
    val hintsMap: MutableMap<EncodeHintType, Any?> = EnumMap(EncodeHintType::class.java)
    hintsMap[EncodeHintType.CHARACTER_SET] = "utf-8"
    hintsMap[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
    hintsMap[EncodeHintType.MARGIN] = 0

    val bitmap = try {
        val bitMatrix = QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, size, size, hintsMap)
        val pixels = IntArray(size * size)
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (bitMatrix[j, i]) {
                    pixels[i * size + j] = -0x1
                } else {
                    pixels[i * size + j] = ColorsHex.white1
                }
            }
        }

        Bitmap.createBitmap(pixels, size, size, Bitmap.Config.ARGB_8888)
    } catch (e: WriterException) {
        e.printStackTrace()
        null
    }

    check(bitmap != null) { "Invalid QR render" }

    Image(bitmap.asImageAsset())
}

@Preview
@Composable
fun QRCodePreview() {
    QRCode(data = "Keks a;lksldk;las;ldk;laksl;kdl;ka;lks;ldkla;sk;ldka;lksldk;laks;lkd;lka;lskd;lak;lskd;lak;lskd;lak;lsdk;lak;ldksalk")
}