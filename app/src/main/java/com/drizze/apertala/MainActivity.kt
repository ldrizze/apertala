package com.drizze.apertala

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drizze.apertala.ui.theme.ApertaLaTheme
import com.drizze.apertala.ui.theme.belanosimaFontFamily

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApertaLaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier.background(Color(0xff222222))
    ) {
        Spacer(modifier = Modifier.padding(vertical = 60.dp))

        Image(
            painter = painterResource(R.drawable.typo),
            contentDescription = "",
            modifier = Modifier.padding(horizontal = 40.dp, vertical = 60.dp)
        )

        Image(
            painter = painterResource(R.drawable.login_wave),
            contentDescription = "",
            modifier = Modifier.background(Color(0xffffffff)).fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .background(Color(0xffffffff))
                .fillMaxSize()
        ) {
            LoginForm()
        }
    }
}

@Composable
fun LoginForm() {
    var login by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(all = 40.dp),
    ) {
        Text(
            text = "Entre com a sua conta",
            fontFamily = belanosimaFontFamily,
            modifier = Modifier.padding(bottom = 20.dp),
        )

        OutlinedTextField(
            value = login,
            label = {
                Text("E-mail")
            },
            onValueChange = { newText ->
                login = newText
            }
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp))

        OutlinedTextField(
            value = password,
            label = {
                Text("Senha")
            },
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { newValue ->
                password = newValue
            }
        )

        Button(
            modifier = Modifier.padding(top = 20.dp),
            shape = RoundedCornerShape(4.dp),
            onClick = {
                val videoListIntent = Intent(context, VideoList::class.java)
                context.startActivity(videoListIntent)
            }
        ) {
            Text("Entrar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    ApertaLaTheme {
        LoginScreen()
    }
}
