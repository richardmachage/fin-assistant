package com.transsion.financialassistant.presentation.components.bottom_nav_bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.transsion.financialassistant.presentation.theme.FAColors

@Composable
fun BottomNavBar(
    modifier: Modifier,
    navController: NavController,
    listOfBottomBarItems: List<BottomBarItem>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomAppBar(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            listOfBottomBarItems.forEach { bottomBarItem ->
                val selected = currentRoute?.contains(bottomBarItem.route.toString())
                    ?: false //== bottomBarItem.route

                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        if (currentRoute != bottomBarItem.route) {
                            navController.navigate(bottomBarItem.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(bottomBarItem.icon),
                            contentDescription = stringResource(bottomBarItem.title)
                        )
                    },
                    label = {
                        Text(text = stringResource(bottomBarItem.title))
                    },
                    colors = NavigationBarItemDefaults.colors().copy(
                        selectedIconColor = FAColors.green,
                        unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                    )
                )
            }
        }
    }
}


