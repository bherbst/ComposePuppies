package com.example.androiddevchallenge

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Verified
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.Puppy
import com.example.androiddevchallenge.data.staticPuppies
import com.example.androiddevchallenge.ui.theme.PuppyTheme
import com.example.androiddevchallenge.ui.theme.green800

@Composable
fun PuppyList(
  puppies: List<Puppy>,
  navigateToPuppyDetails: (Puppy) -> Unit
) {
  LazyColumn {
    itemsIndexed(puppies) { index, puppy ->
      PuppyRow(
        puppy = puppy,
        modifier = Modifier
          .clickable { navigateToPuppyDetails(puppy) }
          .fillMaxWidth()
      )

      if (index < puppies.size - 1) {
        Divider()
      }
    }
  }
}

@Composable
fun PuppyRow(
  puppy: Puppy,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier.padding(16.dp)
  ) {
    // TODO can we take a chomp out of the corner?
    Image(
      painter = painterResource(puppy.drawableRes),
      contentDescription = "Image of ${puppy.name}",
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .clip(RoundedCornerShape(8.dp))
        .size(120.dp)
    )

    Spacer(Modifier.width(16.dp))

    Column(
      verticalArrangement = Arrangement.Center
    ) {
      Text(
        text = puppy.name,
        style = MaterialTheme.typography.subtitle1
      )

      if (puppy.isGood) {
        Spacer(Modifier.height(4.dp))
        PuppyAttribute(
          icon = Icons.Filled.Verified,
          text = "Certified Good Doggo",
          color = green800
        )
      }

      Spacer(Modifier.height(12.dp))

      Row {
        PuppyAttribute(
          icon = Icons.Filled.CalendarToday,
          text = puppy.age
        )
        Spacer(Modifier.width(12.dp))
        PuppyAttribute(
          icon = Icons.Filled.Pets,
          text = puppy.breed
        )
      }
    }
  }
}

@Composable
private fun PuppyAttribute(
  icon: ImageVector,
  text: String,
  color: Color = Color.Unspecified
) {
  Row(
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      imageVector = icon,
      contentDescription = null, // This would just mirror the text
      tint = color.takeOrElse { LocalContentColor.current.copy(alpha = LocalContentAlpha.current) },
      modifier = Modifier.size(18.dp)
    )
    Spacer(Modifier.width(4.dp))
    Text(
      text = text,
      style = MaterialTheme.typography.caption,
      color = color
    )
  }
}

@Preview
@Composable
private fun PuppyRowPreview() {
  PuppyTheme {
    Surface(
      color = MaterialTheme.colors.surface
    ) {
      PuppyRow(puppy = staticPuppies.first())
    }
  }
}