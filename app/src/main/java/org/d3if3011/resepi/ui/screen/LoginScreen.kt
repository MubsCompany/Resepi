package org.d3if3011.resepi.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if3011.resepi.R
import org.d3if3011.resepi.ui.theme.ResepiTheme

@Composable
fun LoginScreen() {
    Scaffold {paddingValues ->
        ScreenContent(modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun ScreenContent(modifier: Modifier) {
    var email by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var emailError by rememberSaveable {
        mutableStateOf(false)
    }

    var passwordError by rememberSaveable {
        mutableStateOf(false)
    }

    var rememberMe by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.ic_logo_resepi), contentDescription = "logo resepi")



        Column (
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            Text(
                text = "Login",
                style = TextStyle(
                    fontSize = 32.sp, // Ukuran font (dalam sp)
                    fontWeight = FontWeight.Normal // Tebal font
                )
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = stringResource(R.string.email)) },
                isError = emailError,
                trailingIcon = { if (emailError) Icon(imageVector = Icons.Filled.Warning, contentDescription = null) },
                supportingText = { ErrorHint(emailError) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = stringResource(R.string.password)) },
            isError = passwordError,
            trailingIcon = { if (passwordError) Icon(imageVector = Icons.Filled.Warning, contentDescription = null) },
            supportingText = { ErrorHint(passwordError) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row (verticalAlignment = Alignment.CenterVertically,) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it }, // Tambahkan padding kanan
                )
                Text(
                    text = "Ingat saya",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            Text(text = stringResource(R.string.lupa_password))
        }

        Button(
            onClick = { /* Handle login action */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF57C00)
            )
        ) {
            Text(stringResource(R.string.login))
        }

        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.belum_punya_akun))

            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = stringResource(R.string.buat_akun),
                    color = Color(0xFFF57C00)
                )
            }
        }

        Divider()

        OutlinedButton(
            onClick = { /* Handle login action */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            )
        ) {
            Text(
                text = stringResource(R.string.login_via_google),
                color = Color.Black
            )
        }

    }
}


@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.isian_tidak_boleh_kosong))
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ScreenPreview() {
    ResepiTheme {
        LoginScreen()
    }
}
