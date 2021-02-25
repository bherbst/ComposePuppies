package com.example.androiddevchallenge.data

import androidx.annotation.DrawableRes

data class Puppy(
  val name: String,
  val age: String,
  val breed: String,
  @DrawableRes val drawableRes: Int,

  // They're all good doggos, please do not set to false
  val isGood: Boolean = true
) {
  init {
    require(isGood) { "All puppies are good, please correct your data" }
  }
}