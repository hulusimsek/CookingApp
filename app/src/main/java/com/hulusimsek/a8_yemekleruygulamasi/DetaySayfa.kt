package com.hulusimsek.a8_yemekleruygulamasi

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetaySayfa(yemek : Yemekler) {
    Scaffold(modifier = Modifier,
        topBar = {
            TopAppBar(title = { Text(text = "Yemekler") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(
                    id = R.color.anaRenk), titleContentColor = Color.White)
            )
        },
        content = {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally) {
                val activity = (LocalContext.current as Activity)
                Image(
                    bitmap = ImageBitmap.imageResource(
                        id = activity.resources.getIdentifier(
                            yemek.yemek_resim_adi,
                            "drawable",
                            activity.packageName
                        )
                    ),
                    contentDescription = "",
                    modifier = Modifier.size(250.dp)
                )

                Text(text = "${yemek.yemek_fiyat}₺", fontSize = 50.sp, color = Color.Blue)
                
                Button(onClick = {Log.e("yemek","${yemek.yemek_adi}")}, modifier = Modifier.size(250.dp,50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.anaRenk),
                        contentColor = Color.White
                    )) {
                    Text(text = "Sipariş Ver")
                }

            }
        }
    )
}
