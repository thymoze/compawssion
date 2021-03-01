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
package com.example.androiddevchallenge.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.components.PuppyCard
import com.example.androiddevchallenge.entity.PuppyRepository

@ExperimentalFoundationApi
@Composable
fun PuppyList(puppyRepository: PuppyRepository, onClickPuppy: (Int) -> Unit) {
    val gridState = rememberLazyListState()
    val puppies = puppyRepository.getPuppies()

    Scaffold(
        topBar = { CompawssionTopBar() }
    ) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            state = gridState,
        ) {
            items(puppies.size) { i ->
                Box(Modifier.padding(8.dp)) { PuppyCard(puppies[i], onClickPuppy) }
            }
        }
    }
}
