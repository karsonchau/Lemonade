package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LemonadeApp() {
    var currentStep by remember {
        mutableIntStateOf(1)
    }

    var currentTaps by remember {
        mutableIntStateOf(0)
    }
    var requiredTaps by remember {
        mutableIntStateOf((2..4).random())
    }

    val imageResource = when (currentStep) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }

    val imageContentDescription = when (currentStep) {
        1 -> stringResource(id = R.string.lemon_tree)
        2 -> stringResource(id = R.string.lemon)
        3 -> stringResource(id = R.string.glass_of_lemonade)
        else -> stringResource(id = R.string.empty_glass)
    }

    val text = when (currentStep) {
        1 -> stringResource(id = R.string.tap_lemon_select)
        2 -> stringResource(id = R.string.keep_tapping)
        3 -> stringResource(id = R.string.tap_lemon_drink)
        else -> stringResource(id = R.string.tap_empty_glass)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        if (currentStep == 2) {
            LemonTextAndImage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .wrapContentSize(align = Alignment.Center),
                imageResources = imageResource,
                imageContentDescription = imageContentDescription,
                text = text,
                onClick = {
                    currentTaps += 1
                    if (currentTaps == requiredTaps) {
                        currentStep += 1
                        currentTaps = 0
                        requiredTaps = (2..4).random()
                    }
                })
        } else {
            LemonTextAndImage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .wrapContentSize(align = Alignment.Center),
                imageResources = imageResource,
                imageContentDescription = imageContentDescription,
                text = text,
                onClick = {
                    if (currentStep == 4) {
                        currentStep = 1
                    } else {
                        currentStep += 1
                    }
                })
        }
    }
}

@Composable
fun LemonTextAndImage(modifier: Modifier = Modifier,
             imageResources: Int,
             imageContentDescription: String,
             text: String,
             onClick: () -> Unit = {}) {
    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            Image(
                painter = painterResource(imageResources),
                contentDescription = imageContentDescription,
                modifier = Modifier
                    .width(128.dp)
                    .height(160.dp)
                    .padding(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = text)
    }
}