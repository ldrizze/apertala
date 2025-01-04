package com.drizze.apertala

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drizze.apertala.ui.theme.ApertaLaTheme
import com.drizze.apertala.ui.theme.BackgroundMain
import com.drizze.apertala.ui.theme.OrangeMain
import com.drizze.apertala.ui.theme.belanosimaFontFamily
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import java.io.File

class VideoList : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApertaLaTheme {
                ScaF(modifier = Modifier.fillMaxSize())
            }
        }

        copyFilesToExternalStorage()
    }

    private fun copyFilesToExternalStorage() {
        val video1 = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "video1")
        if (!video1.exists()) {
            copyFile(R.raw.video_1, "video1")
        }

        val video2 = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "video2")
        if (!video2.exists()) {
            copyFile(R.raw.video_2, "video2")
        }

        val video3 = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "video3")
        if (!video3.exists()) {
            copyFile(R.raw.video_3, "video3")
        }
    }

    private fun copyFile(rawId: Int, name: String) {
        val ins = resources.openRawResource(rawId)
        val video = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), name)

        if (video.createNewFile()) {
            ins.use { input ->
                video.outputStream().use { output ->
                    ins.copyTo(output)
                }
            }
        }

        Log.d("CPY", "$name copied!")
    }

}

@Composable
fun MainSection(modifier: Modifier = Modifier) {
    Column(modifier.padding(horizontal = 12.dp)) {
        Spacer(Modifier.fillMaxWidth().padding(vertical = 22.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Ontem", fontFamily = belanosimaFontFamily, fontSize = 36.sp)
            Row (modifier = Modifier.padding(14.dp).fillMaxWidth()) {
                VideoThumb("Gol de placa - SP", painterResource(R.drawable.pic_video_1), "video1")
            }
        }

        Spacer(Modifier.fillMaxWidth().padding(vertical = 22.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Quarta, 01 jan. 2025", fontFamily = belanosimaFontFamily, fontSize = 36.sp)
            Row(modifier = Modifier.padding(14.dp).fillMaxWidth()) {
                VideoThumb(
                    "Campo posto menino - SP",
                    painterResource(R.drawable.pic_video_2),
                    "video2",
                    Modifier.padding(end = 12.dp)
                )

                VideoThumb(
                    "Campo posto menino - SP",
                    painterResource(R.drawable.pic_video_3),
                    "video3"
                )
            }
        }
    }
}

@Composable
fun VideoThumb(picName: String, thumb: Painter, videoRef: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(modifier.clickable(onClick = {
        val inte = Intent(Intent.ACTION_SEND)
        inte.setType("video/mp4")
        inte.putExtra(
            Intent.EXTRA_STREAM,
            FileProvider.getUriForFile(
                context,
                context.packageName + ".fileprovider",
                File(
                    context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                    videoRef
                )
            )
        )
        inte.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(Intent.createChooser(inte, "Share"))
    })) {
        Image(
            thumb,
            picName,
            modifier = Modifier.size(140.dp).clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
        Text(text = picName, fontSize = 8.sp, modifier = Modifier.padding(top = 4.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ScaF(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Scaffold(
        modifier,
        floatingActionButton = {
            FloatingActionButton(
                containerColor = BackgroundMain,
                contentColor = OrangeMain,
                shape = CircleShape,
                onClick = {
                    val scanner = GmsBarcodeScanning.getClient(context)
                    scanner.startScan()
                        .addOnSuccessListener { barcode ->
                            Toast.makeText(
                                context,
                                "Quadra reconhecida. Pra começar a gravar aperta lá!",
                                Toast.LENGTH_SHORT
                            ).show()

                            Log.d("QR", barcode.rawValue.toString())
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                context,
                                "Quadra não reconhecida.",
                                Toast.LENGTH_SHORT
                            ).show()

                            Log.e("QR", e.message.toString());
                        }
                }
            ) {
                Icon(
                    painterResource(R.drawable.baseline_qr_code_scanner_24_primary),
                    "Scanear QR Code"
                )
            }
        }
    ) { innerPadding ->
        MainSection(
            modifier = Modifier.padding(innerPadding).fillMaxWidth()
        )
    }
}
