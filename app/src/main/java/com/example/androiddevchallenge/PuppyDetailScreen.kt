package com.example.androiddevchallenge

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.androiddevchallenge.data.Puppy

@Composable
fun PuppyDetailScreen(
  puppy: Puppy
) {
  Text(puppy.name)
}