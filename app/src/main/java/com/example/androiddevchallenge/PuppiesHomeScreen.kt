package com.example.androiddevchallenge

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.Puppy
import com.example.androiddevchallenge.data.staticPuppies
import com.example.androiddevchallenge.ui.theme.PuppyTheme
import com.example.androiddevchallenge.ui.theme.blue100
import com.example.androiddevchallenge.ui.theme.blue300
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PuppiesHomeScreen(
  puppies: List<Puppy>,
  navigateToPuppyDetails: (Puppy) -> Unit
) {
  val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
  val scope = rememberCoroutineScope()

  // TODO a lot of this could and should be moved out of here
  val breedFilters = remember { mutableStateOf(listOf<String>()) }
  val breeds = remember {
    puppies.map { it.breed }.distinct()
  }

  val displayPuppies = if (breedFilters.value.isNotEmpty()) {
    puppies.filter { it.breed in breedFilters.value }
  } else {
    puppies
  }

  BackdropScaffold(
    scaffoldState = scaffoldState,
    appBar = {
      PuppiesAppBar(
        filtersOpen = scaffoldState.isRevealed,
        toggleFiltersOpen = {
          if (scaffoldState.isRevealed) {
            scope.launch { scaffoldState.conceal() }
          } else {
            scope.launch { scaffoldState.reveal() }
          }
        }
      )
    },
    backLayerContent = {
      PuppyFilters(
        breeds = breeds,
        selectedBreeds = breedFilters.value,
        onBreedSelectionChanged = { breedFilters.value = it } // TODO getValue/setValue
      )
    },
    frontLayerContent = {
      PuppyList(displayPuppies, navigateToPuppyDetails)
    },
  )
}

@Composable
private fun PuppiesAppBar(
  filtersOpen: Boolean,
  toggleFiltersOpen: () -> Unit
) {
  val icon = when (filtersOpen) {
    true -> Icons.Filled.Close
    false -> Icons.Filled.Tune
  }
  val description = when (filtersOpen) {
    true -> "close filters"
    false -> "open filters"
  }
  val title = when (filtersOpen) {
    true -> "Filters"
    false -> "Adopt a Puppy"
  }

  TopAppBar(
    title = { Text(text = title) },
    elevation = 0.dp,
    actions = {
      IconButton(onClick = { toggleFiltersOpen() }) {
        Icon(
          imageVector = icon,
          contentDescription = description
        )
      }
    }
  )
}

@Composable
private fun PuppyFilters(
  breeds: List<String>,
  selectedBreeds: List<String>,
  onBreedSelectionChanged: (List<String>) -> Unit
) {
  Column {
    Box(Modifier.padding(horizontal = 16.dp)) {
      Text("Breed: ")
    }
    Spacer(Modifier.height(4.dp))
    BreedChipGroup(
      breeds = breeds,
      selectedBreeds = selectedBreeds,
      onBreedSelectionChanged = onBreedSelectionChanged,
    )
    Spacer(Modifier.height(12.dp))
  }
}

@Composable
private fun BreedChipGroup(
  breeds: List<String>,
  selectedBreeds: List<String>,
  onBreedSelectionChanged: (List<String>) -> Unit
) {
  LazyRow(
    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp)
  ) {
    itemsIndexed(breeds) { index, breed ->
      Chip(
        text = breed,
        selected = selectedBreeds.contains(breed),
        onSelectedChanged = { selected ->
          if (selected) {
            onBreedSelectionChanged(selectedBreeds + breed)
          } else {
            onBreedSelectionChanged(selectedBreeds - breed)
          }
        }
      )

      if (index < breeds.size - 1) {
        Spacer(Modifier.width(8.dp))
      }
    }
  }
}

// TODO extract
// TODO animate color changes
@Composable
private fun Chip(
  text: String,
  selected: Boolean,
  onSelectedChanged: (Boolean) -> Unit
) {
  Surface(
    color = if (selected) blue300 else blue100, //TBD
    shape = ChipShape,
    border = if (selected) null else BorderStroke(1.dp, blue300),
    modifier = Modifier
      .clip(ChipShape)
      .selectable(
        selected = selected,
        role = Role.Checkbox,
        onClick = { onSelectedChanged(!selected) }
      )
  ) {
    Text(
      text = text,
      style = MaterialTheme.typography.body2,
      modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
  }
}

val ChipShape = RoundedCornerShape(percent = 50)

@Preview
@Composable
private fun PuppiesHomePreview() {
  PuppyTheme {
    PuppiesHomeScreen(staticPuppies, navigateToPuppyDetails = {})
  }
}