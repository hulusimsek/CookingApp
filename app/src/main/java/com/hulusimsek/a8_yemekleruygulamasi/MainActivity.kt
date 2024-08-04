package com.hulusimsek.a8_yemekleruygulamasi

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.hulusimsek.a8_yemekleruygulamasi.ui.theme._8_YemeklerUygulamasiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _8_YemeklerUygulamasiTheme {
                SayfaGecisleri()
            }
        }
    }
}

@Composable
fun SayfaGecisleri() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "anasayfa") {
        composable("anasayfa") {
            AnaSayfa(navController = navController)
        }
        composable(
            "detay_sayfa/{yemek}",
            arguments = listOf(navArgument("yemek") { type = NavType.StringType })
        ){
            val json = it.arguments?.getString("yemek")
            val yemek = Gson().fromJson(json,Yemekler::class.java)
            DetaySayfa(yemek = yemek)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "DiscouragedApi")
@Composable
fun AnaSayfa(modifier: Modifier = Modifier, navController: NavController) {
    val yemekListesi = remember { mutableStateListOf<Yemekler>() }

    LaunchedEffect(key1 = true) {
        val y1 = Yemekler(1, "Köfte", "kofte", 15)
        val y2 = Yemekler(2, "Ayran", "ayran", 2)
        val y3 = Yemekler(3, "Fanta", "fanta", 3)
        val y4 = Yemekler(4, "Makarna", "makarna", 14)
        val y5 = Yemekler(5, "Kadayıf", "kadayif", 8)
        val y6 = Yemekler(6, "Baklava", "baklava", 15)

        yemekListesi.addAll(listOf(y1, y2, y3, y4, y5, y6))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Yemekler") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.anaRenk),
                    titleContentColor = Color.White
                )
            )
        },
        content = {
            LazyColumn(
                modifier = modifier.padding(it),
            ) {
                items(
                    count = yemekListesi.count(),
                    itemContent = { index ->
                        val yemek = yemekListesi[index]
                        Card(
                            modifier = Modifier
                                .padding(all = 5.dp)
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .clickable {
                                        val yemekJson = Gson().toJson(yemek)
                                        navController.navigate("detay_sayfa/${yemekJson}")
                                    }
                                    .padding(all = 10.dp) // Kartın içindeki tüm içeriğe padding ekliyoruz
                                    .fillMaxWidth()
                            ) {
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
                                    modifier = Modifier.size(100.dp)
                                )
                                // Dikey olarak hizalamak için bir Column kullanıyoruz
                                Column(
                                    modifier = Modifier
                                        .weight(1f) // Row içinde kalan alanı kaplaması için weight kullanıyoruz
                                        .padding(start = 10.dp)
                                        .align(Alignment.CenterVertically)
                                ) {
                                    Text(text = yemek.yemek_adi, fontSize = 20.sp)
                                    Text(text = "${yemek.yemek_fiyat}₺", color = Color.Blue)
                                }
                                // İkonu sağa almak için bir Box ile sarmalıyoruz ve align yapıyoruz
                                Box(
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                                        contentDescription = ""
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }
    )
}
