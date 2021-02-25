/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Verified
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.Puppy
import com.example.androiddevchallenge.data.staticPuppies
import com.example.androiddevchallenge.ui.theme.PuppyTheme
import com.example.androiddevchallenge.ui.theme.green800

@Composable
fun PuppyDetailScreen(
    puppy: Puppy,
    navigateBack: () -> Unit
) {
    Scaffold(
        content = {
            PuppyDetailsContent(
                puppy = puppy,
                navigateBack = navigateBack
            )
        }
    )
}

@Composable
private fun PuppyDetailsContent(
    puppy: Puppy,
    navigateBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        Modifier.verticalScroll(scrollState)
    ) {
        PuppyHeader(
            imageRes = puppy.drawableRes,
            name = puppy.name,
            navigateBack = navigateBack
        )

        Column(
            Modifier.padding(16.dp)
        ) {
            Text(
                text = puppy.name,
                style = MaterialTheme.typography.h2
            )

            GoodDoggoLabel()

            Spacer(Modifier.height(2.dp))

            Spacer(Modifier.height(8.dp))

            PuppyDetailsAttribute(label = "Breed:", value = puppy.breed)
            Spacer(Modifier.height(4.dp))
            PuppyDetailsAttribute(label = "Age:", value = puppy.age)

            Box(
                modifier = Modifier.padding(16.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { /*TODO*/ }
                ) {
                    Text("Adopt Me")
                }
            }
        }
    }
}

@Composable
private fun PuppyHeader(
    @DrawableRes imageRes: Int,
    name: String,
    navigateBack: () -> Unit
) {
    Box(
        Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = "Image of $name",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
        )
        IconButton(onClick = navigateBack) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "back",
                tint = Color.White
            )
        }
    }
}

@Composable
private fun PuppyDetailsAttribute(
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.body2
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
private fun GoodDoggoLabel() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Verified,
            contentDescription = null, // This would just mirror the text
            tint = green800,
            modifier = Modifier.size(18.dp)
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = "Certified Good Doggo",
            style = MaterialTheme.typography.caption,
            color = green800
        )
    }
}

@Preview
@Composable
private fun PuppyDetailPreview() {
    PuppyTheme {
        Surface(
            color = MaterialTheme.colors.surface
        ) {
            PuppyDetailsContent(
                puppy = staticPuppies.first(),
                navigateBack = {}
            )
        }
    }
}
