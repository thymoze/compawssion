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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.entity.PuppyRepository
import com.example.androiddevchallenge.ui.MainDestinations.PUPPY_ID
import com.example.androiddevchallenge.ui.MainDestinations.PUPPY_LIST_ROUTE
import com.example.androiddevchallenge.ui.MainDestinations.PUPPY_ROUTE

object MainDestinations {
    const val PUPPY_LIST_ROUTE = "puppies"
    const val PUPPY_ROUTE = "puppy"
    const val PUPPY_ID = "puppyId"
}

@ExperimentalFoundationApi
@Composable
fun Navigation(puppyRepository: PuppyRepository, startDestination: String = PUPPY_LIST_ROUTE) {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(PUPPY_LIST_ROUTE) { PuppyList(puppyRepository, actions.onClickPuppy) }
        composable(
            "$PUPPY_ROUTE/{$PUPPY_ID}",
            arguments = listOf(navArgument(PUPPY_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            PuppyDetails(puppyRepository, arguments.getInt(PUPPY_ID, 0), actions.onBack)
        }
    }
}

class MainActions(navController: NavHostController) {
    val onClickPuppy: (Int) -> Unit = {
        navController.navigate("$PUPPY_ROUTE/$it")
    }
    val onBack: () -> Unit = {
        navController.navigateUp()
    }
}
