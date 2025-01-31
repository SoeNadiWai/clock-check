package com.personalprojects.clockcheck.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.personalprojects.clockcheck.R

@Composable
fun ResultDialog(
    onDismissRequest: () -> Unit,
    isCheckSuccess: Boolean
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AnimatedPreloader(isCheckSuccess = isCheckSuccess)
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(
                        if (isCheckSuccess) {
                            R.string.dialog_text_success
                        } else {
                            R.string.dialog_text_failure
                        }
                    ),
                    modifier = Modifier.padding(8.dp),
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(4.dp),
                    ) {
                        Text(stringResource(R.string.dialog_button_close))
                    }
                }
            }
        }
    }
}

@Composable
fun AnimatedPreloader(modifier: Modifier = Modifier, isCheckSuccess: Boolean) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            if (isCheckSuccess) {
                R.raw.animation_checkmark
            } else {
                R.raw.animation_crossmark
            }
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = 1,
        isPlaying = true
    )

    LottieAnimation(
        composition = preloaderLottieComposition,
        progress = preloaderProgress,
        modifier = modifier.size(160.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun ResultDialogPreview() {
    ResultDialog(
        onDismissRequest = {},
        isCheckSuccess = true
    )
}
