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
package com.example.androiddevchallenge.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

val shapes = Shapes(
  small = RoundedCornerShape(4.dp),
  medium = RoundedCornerShape(4.dp),
  large = RoundedCornerShape(0.dp)
)

val squareChompShape: Shape = ChompShape(false)
val roundedChompShape: Shape = ChompShape(true)

/**
 * Shape that (kinda) makes it look like a doggo took a bite out of the corner of a rectangle
 */
private class ChompShape(
  val roundCorners: Boolean
) : Shape {

  override fun createOutline(
    size: Size,
    layoutDirection: LayoutDirection,
    density: Density
  ) = Outline.Generic(
    createPath(size)
  )

  private fun createPath(size: Size) =  Path().apply {
    val width = size.width
    val height = size.height

    val chompRadius = width * .2f
    val nibbleRadius = chompRadius * .6f

    val rect = Path().apply {
      if (roundCorners) {
        addRoundRect(
          RoundRect(
            0f, 0f, width, height,
            cornerRadius = CornerRadius(20f)
          )
        )
      } else {
        addRect(Rect(0f, 0f, width, height))
      }
    }

    // There is _definitely_ a more efficient way to do this.
    val chompStart = width - chompRadius
    val nibbles = Path().apply {
      val nibbleOneLeft = width - chompRadius
      val nibbleOneRect = Rect(
        Offset(nibbleOneLeft, 0f),
        Offset(nibbleOneLeft + nibbleRadius, nibbleRadius)
      )
      addOval(nibbleOneRect)

      val nibbleTwoRect = nibbleOneRect.translate(nibbleRadius / 3f, nibbleRadius / 1.5f)
      addOval(nibbleTwoRect)

      val nibbleThreeRect = nibbleTwoRect.translate(nibbleRadius / 1.5f, nibbleRadius / 3f)
      addOval(nibbleThreeRect)

      close()
    }

    val restOfTheChomp = Path().apply {
      moveTo(width, 0f)
      lineTo(chompStart, 0f)
      lineTo(width, chompRadius)
      close()
    }

    val chomp = Path.combine(
      PathOperation.union,
      nibbles,
      restOfTheChomp
    )

    op(
      rect,
      chomp,
      PathOperation.difference
    )
  }
}