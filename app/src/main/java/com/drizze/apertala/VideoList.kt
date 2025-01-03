package com.drizze.apertala

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drizze.apertala.ui.theme.ApertaLaTheme
import com.drizze.apertala.ui.theme.BackgroundMain
import com.drizze.apertala.ui.theme.OrangeMain
import com.drizze.apertala.ui.theme.belanosimaFontFamily

class VideoList : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApertaLaTheme {
                ScaF()
            }
        }
    }
}

@Composable
fun MainSection(modifier: Modifier = Modifier) {
    Column(modifier) {
        Column {
            Text(text = "Ontem", fontFamily = belanosimaFontFamily, fontSize = 36.sp)
            Row {

            }
        }

        Spacer(Modifier.fillMaxWidth().padding(vertical = 22.dp))

        Column {
            Text(text = "Quarta, 01 jan. 2025", fontFamily = belanosimaFontFamily, fontSize = 36.sp)
            Row {

            }
        }
    }
}

@Composable
fun MainSectionPreview() {
    ApertaLaTheme {
        MainSection()
    }
}

@Preview(showBackground = true)
@Composable
fun ScaF(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                containerColor = BackgroundMain,
                contentColor = OrangeMain,
                shape = CircleShape,
                onClick = {  }
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
