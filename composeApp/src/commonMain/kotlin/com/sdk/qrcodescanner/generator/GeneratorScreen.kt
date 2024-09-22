package com.sdk.qrcodescanner.generator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.sdk.qrcodescanner.core.ImeAdaptiveColumn
import com.sdk.qrcodescanner.core.QrCodeGenerator
import com.sdk.qrcodescanner.core.byteArrayToImageBitmap
import com.sdk.qrcodescanner.core.getGenerator
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft

internal object GeneratorScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        var byteArray by remember { mutableStateOf<ByteArray?>(null) }
        var url by remember { mutableStateOf("") }
        val nav = LocalNavigator.current
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                nav?.pop()
                            }
                        ) {
                            Icon(
                                imageVector = FeatherIcons.ArrowLeft,
                                contentDescription = null
                            )
                        }
                    },
                    title = {}
                )
            }
        ) {
            ImeAdaptiveColumn(
                modifier = Modifier.padding(it)
            ) {
                val image = byteArrayToImageBitmap(byteArray)
                if (image != null) {
                    Image(
                        modifier = Modifier
                            .size(300.dp)
                            .border(BorderStroke(1.dp,MaterialTheme.colorScheme.primary),
                                RoundedCornerShape(12.dp)
                            ),
                        bitmap = image,
                        contentDescription = null
                    )
                }
                Spacer(Modifier.height(40.dp))
                OutlinedTextField(
                    value = url,
                    onValueChange = {
                        url = it
                    },
                    placeholder = {
                        Text(
                            text = "Enter or paste link/url"
                        )
                    }
                )
                Spacer(Modifier.height(16.dp))
                Button(
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    onClick = {
                        if (url.isNotBlank()) {
                            val generator: QrCodeGenerator = getGenerator()
                            byteArray = generator.generateQrCode(url)
                        }
                    }
                ) {
                    Text(
                        text = "Generate"
                    )
                }
            }
        }
    }
}