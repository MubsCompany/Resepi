package org.d3if3011.resepi.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3011.resepi.R
import org.d3if3011.resepi.controller.signUp
import org.d3if3011.resepi.navigation.Screen
import org.d3if3011.resepi.ui.theme.ResepiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrasiScreen(navController: NavHostController, consError: Boolean) {
    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.kembali),
                            tint = Color.Black
                        )
                    }
                },
                title = {  },
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp)
    ){paddingValues ->
        RegistScreen(modifier = Modifier.padding(paddingValues), navController, consError = consError)
    }
}
@Composable
fun RegistScreen(modifier: Modifier, navController: NavHostController, consError: Boolean){
    var namaLengkap by rememberSaveable {
        mutableStateOf("")
    }
    var namaLengkapError by rememberSaveable {
        mutableStateOf(false)
    }
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var emailError by rememberSaveable {
        mutableStateOf(false)
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var passwordError by rememberSaveable {
        mutableStateOf(false)
    }
    var passwordVer by rememberSaveable {
        mutableStateOf("")
    }
    var passwordVerError by rememberSaveable {
        mutableStateOf(false)
    }
    var passwordVerPassword by rememberSaveable {
        mutableStateOf(false)
    }
    var passwordLength by rememberSaveable {
        mutableStateOf(false)
    }
    Text(text = stringResource(id = R.string.regis),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold,
        fontStyle = FontStyle.Normal,
        fontSize = 32.sp,
        modifier = modifier.padding(start = 20.dp,bottom = 24.dp)
    )
Column(
    modifier = modifier
        .padding(start = 20.dp, top = 70.dp, end = 20.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    OutlinedTextField(
        value = namaLengkap,
        onValueChange = { namaLengkap = it },
        label = { Text(text = stringResource(R.string.namaLengkap)) },
        trailingIcon = { if (namaLengkapError) Icon(imageVector = Icons.Filled.Warning, contentDescription = null) },
        isError = namaLengkapError,
        supportingText = { ErrorHintRegist(namaLengkapError, stringResource(id = R.string.namaLengkap)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        modifier = Modifier.fillMaxWidth()
    )
    OutlinedTextField(
        value = consError.toString(),
        onValueChange = { email = it },
        label = { Text(text = stringResource(R.string.email)) },
        trailingIcon = { if (emailError) Icon(imageVector = Icons.Filled.Warning, contentDescription = null) },
        isError = emailError || consError,
        supportingText = {
            if (emailError) ErrorHintRegist(emailError, stringResource(id = R.string.email))
            if (consError) ErrorHintEmailExisted(consError, stringResource(id = R.string.email))
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text(text = stringResource(R.string.password)) },
        trailingIcon = { if (passwordError) Icon(imageVector = Icons.Filled.Warning, contentDescription = null) },
        isError = passwordLength,
        supportingText = {
           if(passwordLength) ErrorHintPasswordLenght(passwordLength, stringResource(id = R.string.password))
           if (passwordError) ErrorHintRegist(passwordError, stringResource(id = R.string.password))
                         },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),
        modifier = Modifier.fillMaxWidth()
    )
    OutlinedTextField(
        value = passwordVer,
        onValueChange = { passwordVer = it },
        label = { Text(text = stringResource(R.string.passwordVer)) },
        trailingIcon = {
            if (passwordVerError) Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
            else if (!passwordVer.equals(password)) Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
                       },
        isError = passwordVerError || passwordVerPassword,
        supportingText = {
            if (passwordVerError) ErrorHintRegist(passwordVerError, stringResource(id = R.string.passwordVer))
            else if(passwordVerPassword) ErrorHintPassword(passwordVerPassword, stringResource(id = R.string.passwordVer))
                         },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),
        modifier = Modifier.fillMaxWidth()
    )
    Button(
        onClick = {
            emailError = email.equals("")
            namaLengkapError = namaLengkap.equals("")
            passwordError = password.equals("")
            passwordVerError = passwordVer.equals("")
            passwordVerPassword = !passwordVer.equals(password)
            passwordLength = password.length < 6
            if (emailError || namaLengkapError || passwordError || passwordVerError)return@Button
            else if (passwordVerPassword)return@Button
            else if (passwordLength) return@Button
            //SignUp here
            else {
                signUp(email, namaLengkap, password, navController)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .size(70.dp)
            .padding(vertical = 8.dp)
            .shadow(2.dp, shape = RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF57C00)
        )
    ) {
        Text(stringResource(R.string.regis),
            fontSize = 18.sp
        )
    }
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(bottom = 10.dp),
    ) {
        Text(text = stringResource(R.string.sudah_punya_akun))

        TextButton(
            onClick =
            {
                navController.navigate(Screen.Login.route)
        }) {
            Text(
                text = stringResource(R.string.login),
                color = Color(0xFFF57C00)
            )
        }
    }
    Divider(
        modifier = Modifier.height(1.5.dp),
        color = Color.LightGray
        )
    OutlinedButton(
        onClick = { /* Handle login google action */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
            .shadow(1.dp, shape = RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        )
    ) {
        Image(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 8.dp)
                .size(22.dp),
            painter = painterResource(id = R.drawable.ic_google),
            contentDescription = null,
        )
        Text(
            text = stringResource(R.string.login_via_google),
            color = Color.Black,
            fontSize = 18.sp
        )
    }
}
}
@Composable
fun ErrorHintRegist(isError: Boolean, unit: String) {
    if (isError) {
        Text(text = stringResource(R.string.isian_tidak_boleh_kosong, unit))
    }
}
@Composable
fun ErrorHintPassword(isErrorPasswordVer: Boolean, unit: String) {
    if (isErrorPasswordVer){
        Text(text = stringResource(R.string.konfirmasi_salah, unit))
    }
}
@Composable
fun ErrorHintPasswordLenght(isErrorPasswordVer: Boolean, unit: String) {
    if (isErrorPasswordVer){
        Text(text = stringResource(R.string.password_panjang, unit))
    }
}
@Composable
fun ErrorHintEmailExisted(isErrorEmailExist: Boolean, unit: String){
    if (isErrorEmailExist == true){
        Text(text = stringResource(R.string.email_sudah_ada, unit))
    }
}
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun Prev() {
    ResepiTheme {
        RegistrasiScreen(rememberNavController(), false)
    }
}