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

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.entity.FakePuppyRepository
import com.example.androiddevchallenge.entity.PuppyRepository
import com.example.androiddevchallenge.ui.theme.CompawssionTheme

@Composable
fun PuppyDetails(puppyRepository: PuppyRepository, puppyId: Int, onBack: () -> Unit) {
    val puppy = puppyRepository.getPuppy(puppyId)
    val scrollState = rememberScrollState()
    var showFab by remember { mutableStateOf(true) }

    Scaffold(
        topBar = { PuppyDetailsTopBar(onBack) },
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            if (puppy != null) {
                var expand by remember { mutableStateOf(false) }

                val roundedCorner by animateIntAsState(if (expand) 0 else 50)
                val iconSize by animateDpAsState(if (expand) 80.dp else 50.dp)
                val scale by animateFloatAsState(if (showFab) 1f else 0f)

                val modifier = if (expand) {
                    Modifier
                        .absoluteOffset(x = 16.dp, y = 16.dp)
                        .animateContentSize()
                        .fillMaxSize()
                } else {
                    Modifier
                        .animateContentSize()
                        .scale(scale)
                }

                FloatingActionButton(
                    onClick = { expand = !expand },
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = Color.White,
                    modifier = modifier,
                    shape = MaterialTheme.shapes.small.copy(CornerSize(percent = roundedCorner)),
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 32.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_logo),
                                contentDescription = null,
                                modifier = Modifier
                                    .animateContentSize()
                                    .size(iconSize),
                                colorFilter = ColorFilter.tint(color = Color.White)
                            )

                            if (expand) {
                                Text(
                                    text = "Adopted!",
                                    style = MaterialTheme.typography.h4
                                )
                            }
                        }
                        if (!expand) {
                            Text(
                                text = "Adopt me!",
                                style = MaterialTheme.typography.h6
                            )
                        }
                    }
                }
            }
        },
    ) {
        puppy?.let {
            Column(
                Modifier
                    .verticalScroll(state = scrollState)
                    .pointerInput(Unit) {
                        detectVerticalDragGestures { _, dragAmount ->
                            showFab = dragAmount >= 0
                        }
                    }
            ) {
                BoxWithConstraints {
                    var expanded by remember { mutableStateOf(false) }
                    Image(
                        painter = painterResource(puppy.drawable),
                        contentDescription = "Puppy pictures",
                        contentScale = if (expanded) {
                            ContentScale.Fit
                        } else {
                            ContentScale.Crop
                        },
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .animateContentSize()
                            .padding(start = 16.dp)
                            .fillMaxWidth()
                            .heightIn(
                                min = 240.dp,
                                max = if (expanded) {
                                    maxHeight
                                } else {
                                    240.dp
                                }
                            )
                            .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
                            .clickable { expanded = !expanded }
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = puppy.name,
                        style = MaterialTheme.typography.h3,
                    )
                    Text(
                        text = puppy.tagline,
                        style = MaterialTheme.typography.h6.copy(
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Italic
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                    )

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(Color.Black)
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_logo),
                            contentDescription = "Type",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colors.primary,
                        )
                        Text(
                            text = puppy.type,
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_map_pin),
                            contentDescription = "Location",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colors.primary,
                        )
                        Text(
                            text = puppy.location,
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(Color.Black)
                    )

                    Text(text = puppy.description)
                }
            }
        } ?: Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colors.error) {
                Icon(
                    Icons.Default.Error,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
                Text(text = "Puppy not found", style = MaterialTheme.typography.h6)
            }
        }
    }
}

@Composable
fun PuppyDetailsTopBar(onBack: () -> Unit) {
    Box(contentAlignment = Alignment.CenterStart) {
        IconButton(
            onClick = onBack,
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }
        CompawssionTopBar()
    }
}

@Preview
@Composable
fun PuppyDetailsPreview() {
    CompawssionTheme() {
        PuppyDetails(puppyRepository = FakePuppyRepository(), puppyId = 1, onBack = {})
    }
}

@Preview
@Composable
fun PuppyNotFoundPreview() {
    CompawssionTheme() {
        PuppyDetails(puppyRepository = FakePuppyRepository(), puppyId = -1, onBack = {})
    }
}
